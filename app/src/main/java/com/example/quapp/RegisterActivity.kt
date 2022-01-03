package com.example.quapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailInput: TextInputEditText
    private lateinit var emailField : TextInputLayout
    private lateinit var passwordInput : TextInputEditText
    private lateinit var passwordField : TextInputLayout
    private lateinit var registerBtn: Button
    private lateinit var gotoLoginBtn: View

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initUI()

        auth = Firebase.auth

        registerBtn.setOnClickListener {
            if (!checkFields()) {
                registerUser(stringify(emailInput), stringify(passwordInput))
            }
        }

        gotoLoginBtn.setOnClickListener{
            gotoLoginForm()
        }
    }

    private fun checkFields(): Boolean {
        var hasError = false
        var email = stringify(emailInput)
        var emailErrorText = ""
        var emailErrorCounter = 1

        var password = stringify(passwordInput)
        var passwordErrorText = ""
        var passwordErrorCounter = 1

        if (email.isEmpty()) {
            emailErrorText += "${emailErrorCounter++}. Email address can not be empty\n"
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailErrorText += "${emailErrorCounter++}. Invalid email address\n"
        }

        val specialCharactersPattern = Regex("\\W")
        if (password.isEmpty()) {
            passwordErrorText += "${passwordErrorCounter++}. Password must not be empty\n"
        }
        if (specialCharactersPattern.containsMatchIn(password)) {
            passwordErrorText += "${passwordErrorCounter++}. Password must only consist of letters, numbers and underscores\n"
        }
        if (password.length < 6) {
            passwordErrorText += "${passwordErrorCounter++}. Password should be at least 6 characters"
        }

        if (emailErrorText.isNotEmpty()) {
            emailField.error = emailErrorText
            hasError = true
        } else {
            emailField.error = null
        }
        if (passwordErrorText.isNotEmpty()) {
            passwordField.error = passwordErrorText
            hasError = true
        } else {
            passwordField.error = null
        }

        return hasError
    }

    private fun registerUser(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val uid = user!!.uid

                    val userInfo = User(email=email, password=password)
                    writeUserToFirestore(userInfo, uid)

                    val createProfileScreen = Intent(this, CreateProfileActivity::class.java)
                    startActivity(createProfileScreen)

                } else {
                    Log.w("INS", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun initUI() {
        emailInput = findViewById(R.id.email_input)
        emailField = findViewById(R.id.email_field)
        passwordInput = findViewById(R.id.passwordLoginField)
        passwordField = findViewById(R.id.register_field)
        registerBtn = findViewById(R.id.register_button)
        gotoLoginBtn = findViewById(R.id.goto_log_fab)
    }

    private fun writeUserToFirestore(userInfo: User, docId: String) {
        val db = Firebase.firestore
        db.collection("users").document(docId).set(userInfo)
    }

    private fun stringify(x: Any): String {
        return when (x) {
            is TextInputEditText -> return x.text.toString()
            is Int -> x.toString()
            is Boolean -> x.toString()
            is CheckBox -> x.isChecked.toString()
            else -> {
                throw ClassNotFoundException()
            }
        }
    }

    private fun gotoLoginForm() {
        val loginScreen = Intent(this, LoginActivity::class.java)
        startActivity(loginScreen)
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
    }
}
