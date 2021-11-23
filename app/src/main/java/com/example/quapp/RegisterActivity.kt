package com.example.quapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val fab: View = findViewById(R.id.goto_log_fab)

        fab.setOnClickListener{
            val login_screen = Intent(this, LoginActivity::class.java)
            startActivity(login_screen)
        }
    }
}