package com.example.quapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var emailField: TextInputEditText
    private lateinit var passwordField: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var gotoRegisterActivityButton: View

    private lateinit var auth: FirebaseAuth

    private var ra: RegisterActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initUI()

        auth = Firebase.auth
        loginButton.setOnClickListener {
            authenticateWithFirebase(emailField.text.toString(), passwordField.text.toString())
        }

        gotoRegisterActivityButton.setOnClickListener {
            gotoRegisterForm()
        }
    }

    private fun authenticateWithFirebase(email: String, password: String) {
        val currentUser = auth.currentUser
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("Login", "signInWithEmail:success")
                        val user = auth.currentUser
                        gotoMenuActivity(user!!.uid)
                    } else {
                        Log.w("Login", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.

    }

    private fun initUI() {
        emailField = findViewById(R.id.emailInput)
        passwordField = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
        gotoRegisterActivityButton = findViewById(R.id.goto_reg_fab)
    }

    private fun gotoMenuActivity(uid: String) {
        val menuScreen = Intent(this, MenuActivity::class.java)
        menuScreen.putExtra("userid", uid)
        startActivity(menuScreen)
    }

    private fun gotoRegisterForm() {
        val registerScreen = Intent(this, RegisterActivity::class.java)
        startActivity(registerScreen)
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up)
    }
}