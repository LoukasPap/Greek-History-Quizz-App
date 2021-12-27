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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var emailField: TextInputEditText
    private lateinit var passwordField: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var gotoRegisterActivityButton: View

    var isFilled = false

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initUI()

        auth = Firebase.auth

        loginButton.setOnClickListener {
            authenticateWithFirebase(emailField.text.toString(), passwordField.text.toString())
            isRegistrationCompleted() { result ->
                if (result) {
                    gotoMenuActivity()
                } else {
                    gotoCreateProfileActivity()
                }
            }
        }

        gotoRegisterActivityButton.setOnClickListener {
            gotoRegisterForm()
        }
    }

    private fun isRegistrationCompleted(callback:(Boolean) -> Unit){
        val docRef = db.collection("users").document(auth.currentUser?.uid.toString())
        docRef.get()
            .addOnSuccessListener { documentSnapShot ->
                val username = documentSnapShot.data?.getValue("username").toString()
                if (username.isEmpty()) {
                    Log.d("Login", "1++++ ${documentSnapShot.data!!.getValue("username")}")
                    callback(false)
                } else {
                    Log.d("Login", "2++++ ${documentSnapShot.data!!.getValue("username")}")
                    callback(true)
                }
            }
    }

    private fun authenticateWithFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Login", "signInWithEmail:success | ${auth.currentUser?.uid}")
//                    id = auth.currentUser?.uid
                } else {
                    Log.w("Login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun initUI() {
        emailField = findViewById(R.id.emailInput)
        passwordField = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
        gotoRegisterActivityButton = findViewById(R.id.goto_reg_fab)
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