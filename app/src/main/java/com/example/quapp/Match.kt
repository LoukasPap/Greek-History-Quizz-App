package com.example.quapp

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Match(
    var playerID: String,
    var quizType: String,
    var questions: MutableList<Int>,
    var correctAnswers: MutableList<Int>,
    var totalPoints: Int,
    @ServerTimestamp
    val timestamp: Date? = null,
)
