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

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var progressBar: ProgressBar
    private lateinit var questionText: TextView
    private lateinit var trueBtn: MaterialButton
    private lateinit var falseBtn: MaterialButton
    private lateinit var skipBtn: MaterialButton

    private var currentPos = 1
    private var questionList : ArrayList<Question>? = null
    private var givenAnswer: Boolean? = null

    private var correctAnswers = mutableListOf<Int>()
    private var didAnswer = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)
        initUI()

        questionList = Constants.getQuestions()
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
                Toast.makeText(this, "End of questions", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setQuestion() {
        val question = questionList!![currentPos - 1]

        defaultOptionsView()

        progressBar.progress = currentPos
        questionText.text = question.text

        if (currentPos == questionList!!.size) {
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