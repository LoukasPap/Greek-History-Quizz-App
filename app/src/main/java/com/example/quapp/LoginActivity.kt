package com.example.quapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val fab: View = findViewById(R.id.goto_reg_fab)

        fab.setOnClickListener{
            val register_screen = Intent(this, RegisterActivity::class.java)
            startActivity(register_screen)
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up)
        }

    }
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up)
    }
}