package com.example.quapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MenuActivity : AppCompatActivity() {

    private lateinit var userEmail: TextView
    private lateinit var userPassword: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        initUIComponents()

        auth = Firebase.auth

        var uid: String? = if (savedInstanceState == null) {
            val extras = intent.extras
            extras?.getString("userid")
        } else {
            savedInstanceState.getSerializable("userid") as String
        }

        updateUI(uid!!)
    }

    private fun initFirebaseAuth(): String {
        val user = auth.currentUser
        return user?.uid ?: ""
    }

    private fun initUIComponents() {
        userEmail = findViewById(R.id.userEmail)
        userPassword = findViewById(R.id.userPassword)
    }



    private fun updateUI(uid : String) {
        val db = Firebase.firestore
        val docRef = db.collection("users").document(uid)
        docRef.get()
            .addOnSuccessListener { documentSnapShot ->
                userEmail.text = documentSnapShot.data?.getValue("email").toString().plus(documentSnapShot.data?.getValue("username").toString())
                userPassword.text = documentSnapShot.data?.getValue("password").toString()
            }
    }
}
