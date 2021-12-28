package com.example.quapp

data class Question(
    var id: Int,
    var text: String,
    var correctAnswer: Boolean,
    var skip: Boolean = false,
)
