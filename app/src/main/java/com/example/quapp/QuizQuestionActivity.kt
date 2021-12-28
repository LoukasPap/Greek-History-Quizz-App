package com.example.quapp

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class QuizQuestionActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var questionText: TextView
    private lateinit var trueBtn: MaterialButton
    private lateinit var falseBtn: MaterialButton
    private lateinit var skipBtn: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)
        initUI()

        val questionList = Constants.getQuestions()

        val currentPosition = 0
        val question: Question = questionList[currentPosition]

        progressBar.progress = currentPosition
    }

    private fun initUI(){
        progressBar = findViewById(R.id.determinateBar)
        questionText =findViewById(R.id.questionText)
        trueBtn = findViewById(R.id.trueButton)
        falseBtn = findViewById(R.id.falseButton)
        skipBtn = findViewById(R.id.skipButton)
    }
}