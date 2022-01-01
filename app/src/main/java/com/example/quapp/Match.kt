package com.example.quapp

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Match(
    var playerID: String = "",
    var quizType: String = "",
    var questions: MutableList<Int> = mutableListOf(),
    var correctAnswers: MutableList<Int> = mutableListOf(),
    var totalPoints: Int = 0,
    @ServerTimestamp
    val timestamp: Date? = null,
)
