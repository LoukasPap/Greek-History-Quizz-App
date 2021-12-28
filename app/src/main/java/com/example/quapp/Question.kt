package com.example.quapp

data class Question(
    var id: Int,
    var text: String,
    var correctAnswer: Boolean,
    var skipped: Boolean = false,
    var points: Int = 100,
    )
