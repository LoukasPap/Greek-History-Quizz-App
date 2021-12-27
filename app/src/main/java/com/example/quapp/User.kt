package com.example.quapp

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class User(
    var username: String? = "",
    var email: String? = null,
    var password: String? = null,
    @field:JvmField
    val isGreek: Boolean? = null,
    @ServerTimestamp
    val timestamp: Date? = null,
    var level: Int? = null,
    var avatar: Int? = 0,
)
