package com.example.quapp

import ProgressBarAnimation
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var progressBar: ProgressBar
    private lateinit var questionText: TextView
    private lateinit var trueBtn: MaterialButton
    private lateinit var falseBtn: MaterialButton
    private lateinit var skipBtn: MaterialButton

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    private var currentPos = 1
    private var questionList: ArrayList<Question>? = null
    private var givenAnswer: Boolean? = null
    private var didAnswer = false

    // variables for Match model
    private var correctAnswers = mutableListOf<Int>()
    private var allQuestions = mutableListOf<Int>()

    private var dialogResults = MatchResultsDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)
        initUI()
        progressBar.progress = 0

        auth = Firebase.auth

//      store all questions
        questionList = Constants.getQuestions()
        for (n in questionList!!) {
            allQuestions.add(n.id)
        }
        setQuestion()

        trueBtn.setOnClickListener(this)
        falseBtn.setOnClickListener(this)
        skipBtn.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v) {
            trueBtn -> {
                givenAnswer = true
                animateProgressBar()
            }
            falseBtn -> {
                givenAnswer = false
                animateProgressBar()
            }
            skipBtn -> {
                givenAnswer = null
            }
        }
        checkIfCorrect(v)
    }

    private fun checkIfCorrect(v: View?) {
        val question = questionList?.get(currentPos - 1)
        when (givenAnswer) {
            null -> {
                // check if he answered so as to continue, or if he skipped
                if (didAnswer) {
                    checkForNextQuestion()
                    revertClickability()
                } else {
                    question?.skipped = true
                    animateProgressBar()
                    checkForNextQuestion()
                }
            }
            else -> {
                revertClickability()
                if (givenAnswer == question!!.correctAnswer) {
                    correctAnswers.add(question.id) // add correct answer
                    viewCorrectAnswer(v, R.color.correct)
                } else {
                    viewCorrectAnswer(v, R.color.wrong)
                    if (v == trueBtn) {
                        viewCorrectAnswer(falseBtn, R.color.correct)
                    } else {
                        viewCorrectAnswer(trueBtn, R.color.correct)
                    }
                }
            }
        }
    }

    private fun revertClickability() {
        trueBtn.isClickable = !trueBtn.isClickable
        falseBtn.isClickable = !falseBtn.isClickable
        skipBtn.text = when (skipBtn.text) {
            "Skip" -> "Continue"
            "Continue" -> "Skip"
            else -> "Finish"
        }
        didAnswer = !didAnswer
    }

    private fun viewCorrectAnswer(v: View?, color: Int) {
        v?.backgroundTintList = ContextCompat.getColorStateList(this, color)
    }

    private fun checkForNextQuestion() {
        currentPos++
        when {
            currentPos <= questionList!!.size -> {
                setQuestion()
            } else -> {
                val totalPoints = calcPoints()
                val match = Match(auth.currentUser!!.uid, "TF", allQuestions, correctAnswers, totalPoints)
                uploadToFirestore(match)

                val bundle = Bundle()
                bundle.putInt("corrects", correctAnswers.size)
                bundle.putInt("wrongs", allQuestions.size - correctAnswers.size)
                bundle.putInt("points", totalPoints)
                dialogResults.arguments = bundle

                Toast.makeText(this, "Your match results are saved!", Toast.LENGTH_SHORT).show()
                dialogResults.show(supportFragmentManager, "dialogResults")
            }
        }
    }

    private fun uploadToFirestore(match: Match) {
        val mid = matchIdGenerator()

        db.collection("matches").document(mid).set(match)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("quiz", "createMatch:success")
                } else {
                    Log.d("quiz", "createUserMatch:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
        db.collection("users").document(auth.currentUser!!.uid)
            .update("matches", FieldValue.arrayUnion(mid))
    }

    private fun matchIdGenerator(): String {
        return Calendar.getInstance().timeInMillis.toString().plus("TF")
            .plus(auth.currentUser!!.uid)
    }

    private fun calcPoints(): Int {
        var totalPoints = 0
        correctAnswers.forEach{ ca ->
            for (i in questionList!!){
                if (ca==i.id) {
                    totalPoints += i.points
                    continue
                }
            }
        }
        return totalPoints
    }

    private fun setQuestion() {
        val question = questionList!![currentPos - 1]

        defaultOptionsView()

        if (currentPos == questionList!!.size) {
            skipBtn.text = "Finish"
            Toast.makeText(this, "last one", Toast.LENGTH_SHORT).show()
        }
        questionText.text = question.text

    }

    private fun animateProgressBar() {
        progressBar.max = questionList!!.size*100
        var anim = ProgressBarAnimation(progressBar, (currentPos-1)*100F, (currentPos)*100F)
        anim.duration = 1000
        progressBar.startAnimation(anim)
    }

    private fun defaultOptionsView() {
        trueBtn.backgroundTintList = ContextCompat.getColorStateList(this, R.color.white)
        falseBtn.backgroundTintList = ContextCompat.getColorStateList(this, R.color.white)
    }

    private fun initUI(){
        progressBar = findViewById(R.id.determinateBar)
        questionText =findViewById(R.id.questionText)
        trueBtn = findViewById(R.id.trueButton)
        falseBtn = findViewById(R.id.falseButton)
        skipBtn = findViewById(R.id.skipButton)
    }
}