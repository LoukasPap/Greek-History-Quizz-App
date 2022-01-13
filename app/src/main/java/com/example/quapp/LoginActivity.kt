package com.example.quapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quapp.menu_dashboard.UserMenu
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var emailField: TextInputLayout
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordField: TextInputLayout
    private lateinit var passwordInput: TextInputEditText
    private lateinit var loginBtn: Button
    private lateinit var gotoRegisterBtn: View

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initUI()

        auth = Firebase.auth

        loginBtn.setOnClickListener {
            if (!checkFields()) {
                authenticateWithFirebase(emailInput.text.toString(), passwordInput.text.toString()) {
                    if (it) {
                        isLoginCompleted { result ->
                            if (result) {
                                gotoMenuActivity()
                            } else {
                                gotoCreateProfileActivity()
                            }
                        }
                    }
                }
            }
        }
        gotoRegisterBtn.setOnClickListener {
            gotoRegisterForm()
        }
    }

    private fun checkFields(): Boolean {
        var hasErrors = false
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()

        if (email.isEmpty()) {
            emailField.error = "Email can not be empty\n"
            hasErrors = true
        } else {
            emailField.error = ""
        }
        if (password.isEmpty()) {
            passwordField.error = "Password can not be empty\n"
            hasErrors = true
        } else {
            passwordField.error = ""
        }
        return hasErrors
    }

    private fun isLoginCompleted(callback:(Boolean) -> Unit){
        val docRef = db.collection("users").document(auth.currentUser?.uid.toString())
        docRef.get()
            .addOnSuccessListener { documentSnapShot ->
                val username = documentSnapShot.data?.getValue("username").toString()
                if (username.isEmpty()) {
                    callback(false)
                } else {
                    callback(true)
                }
            }
    }

    private fun authenticateWithFirebase(email: String, password: String, cb: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Login", "signInWithEmail:success | ${auth.currentUser?.uid}")
                    cb(true)
                } else {
                    Log.w("Login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_LONG).show()
                    cb(false)
                }
            }
    }

    private fun initUI() {
        emailField = findViewById(R.id.emailLoginField)
        emailInput = findViewById(R.id.emailInput)
        passwordField = findViewById(R.id.passwordLoginField)
        passwordInput = findViewById(R.id.passwordInput)
        loginBtn = findViewById(R.id.loginButton)
        gotoRegisterBtn = findViewById(R.id.goto_reg_fab)
    }

    private fun gotoCreateProfileActivity() {
        val menuScreen = Intent(this, CreateProfileActivity::class.java)
        startActivity(menuScreen)
    }

    private fun gotoMenuActivity() {
        val menuScreen = Intent(this, UserMenu::class.java)
        startActivity(menuScreen)
    }

    private fun gotoRegisterForm() {
        val registerScreen = Intent(this, RegisterActivity::class.java)
        startActivity(registerScreen)
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up)
    }
}