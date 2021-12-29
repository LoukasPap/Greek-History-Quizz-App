package com.example.quapp

data class Match(
    var playerID: String,
    var quizType: String,
    var questions: MutableList<Int>,
    var correctAnswers: MutableList<Int>,
    var totalPoints: Int,
)
