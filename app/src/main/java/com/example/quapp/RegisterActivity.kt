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

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var email: TextInputEditText
    private lateinit var password : TextInputEditText
    private lateinit var isGreek : CheckBox
    private lateinit var registerButton: Button
    private lateinit var fab: View

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initUIComponents()

        auth = Firebase.auth
        registerButton.setOnClickListener {
            registerUser(stringify(email), stringify(password))
        }

        fab.setOnClickListener{
            gotoLoginForm()
        }

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
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun initUIComponents() {
        email = findViewById(R.id.email_field)
        password = findViewById(R.id.passwordLoginField)
        isGreek = findViewById(R.id.isGreek_field);
        registerButton = findViewById(R.id.register_button)
        fab = findViewById(R.id.goto_log_fab)
    }

    private fun writeUserToFirestore(userInfo: User, docId: String) {
        Log.d("HERE", "FIRESTORE ")
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
