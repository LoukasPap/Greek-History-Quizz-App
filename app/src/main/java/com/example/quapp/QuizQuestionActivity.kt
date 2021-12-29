package com.example.quapp

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
    private var questionList : ArrayList<Question>? = null
    private var givenAnswer: Boolean? = null

//    variables for Match model
    private var correctAnswers = mutableListOf<Int>()
    private var allQuestions = mutableListOf<Int>()

    private var didAnswer = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)
        initUI()
        auth = Firebase.auth


//        store all questions
        questionList = Constants.getQuestions()
        for (n in questionList!!){
            allQuestions.add(n.id)
        }
        setQuestion()

        trueBtn.setOnClickListener(this)
        falseBtn.setOnClickListener(this)
        skipBtn.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v) {
            trueBtn -> {
                givenAnswer = true
                Log.d("quiz", "$givenAnswer Button")
            }
            falseBtn -> {
                givenAnswer = false
                Log.d("quiz", "$givenAnswer Button")
            }
            skipBtn -> {
                givenAnswer = null
                Log.d("quiz", "$givenAnswer Button")
            }
        }
        checkIfCorrect(v)
    }

    private fun checkIfCorrect(v: View?) {
        val question = questionList?.get(currentPos - 1)
        when (givenAnswer){
            null -> {
                // check if he answered so as to continue, or if he skipped
                if (didAnswer) {
                    checkForNextQuestion()
                    revertClickability()
                } else{
                    question?.skipped = true
                    Log.d("quiz", "CHOSE TO SKIP\n--${question?.id} | ${question?.text}")
                    checkForNextQuestion()
                }
            } else -> {
                revertClickability()
                if (givenAnswer == question!!.correctAnswer) {
                    correctAnswers.add(question.id) // add correct answer
                    Log.d("quiz", "CHOSE CORRECT")
                    viewCorrectAnswer(v, R.color.correct)
                } else {
                    Log.d("quiz", "CHOSE WRONG")
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
        skipBtn.text = if (skipBtn.text=="Skip") {"Continue"} else {"Skip"}
        didAnswer = !didAnswer
    }

    private fun viewCorrectAnswer(v: View?, color: Int) {
        v?.backgroundTintList = ContextCompat.getColorStateList(this, color);
    }

    private fun checkForNextQuestion() {
        currentPos++
        when {
            currentPos <= questionList!!.size -> {
                setQuestion()
            } else -> {
                val match = Match(auth.currentUser!!.uid, "TF", allQuestions, correctAnswers, calcPoints())
                uploadToFirestore(match)
                Log.d("quiz", "POINTS ${calcPoints()}")
            }
        }
    }

    private fun uploadToFirestore(match: Match) {
        val db = Firebase.firestore
        val mid = matchIdGenerator()

        db.collection("matches").document(mid).set(match).addOnCompleteListener(this) { task ->
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

    private fun matchIdGenerator(): String{
        val mid = Calendar.getInstance().timeInMillis.toString().plus("TF").plus(auth.currentUser!!.uid)
        Log.d("quiz", "Datetime ${mid}")
        return mid

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

        progressBar.progress = currentPos
        questionText.text = question.text

        if (currentPos == questionList!!.size) {
            skipBtn.text = "Finish"
            Toast.makeText(this, "last one", Toast.LENGTH_SHORT).show()
        }

    }

    private fun defaultOptionsView() {
        trueBtn.backgroundTintList = ContextCompat.getColorStateList(this, R.color.white);
        falseBtn.backgroundTintList = ContextCompat.getColorStateList(this, R.color.white);
    }

    private fun initUI(){
        progressBar = findViewById(R.id.determinateBar)
        questionText =findViewById(R.id.questionText)
        trueBtn = findViewById(R.id.trueButton)
        falseBtn = findViewById(R.id.falseButton)
        skipBtn = findViewById(R.id.skipButton)
    }
}