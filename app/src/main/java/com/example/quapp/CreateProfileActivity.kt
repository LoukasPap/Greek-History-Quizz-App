package com.example.quapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quapp.menu_dashboard.UserMenu
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateProfileActivity : AppCompatActivity() {

    private lateinit var finishButton: Button
    private lateinit var usernameField: TextInputLayout
    private lateinit var usernameInput: TextInputEditText

    private lateinit var recyclerView: RecyclerView
    private lateinit var auth : FirebaseAuth

    private var layoutManager: RecyclerView.LayoutManager?= null
//    private var adapter: RecyclerView.Adapter<AvatarRecyclerAdapter.ViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)
        initUI()

        auth = Firebase.auth

        // Check if user is signed in (non-null), and update UI accordingly.
        val userId: String? = auth.currentUser ?.uid

        layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        var adapter = AvatarRecyclerAdapter()
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : AvatarRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(this@CreateProfileActivity, "Selected item no.$position", Toast.LENGTH_SHORT).show()

            }
        })

        finishButton.setOnClickListener {
            if (checkFields()) {
                completeRegistration(userId, usernameInput.text.toString(),0)
                Toast.makeText(this, "Completed", Toast.LENGTH_LONG).show()
                val userScreen = Intent(this, UserMenu::class.java)
                startActivity(userScreen)
            }
        }
    }

    private fun checkFields(): Boolean {
        var username = usernameInput.text.toString()
        var usernameErrorCounter = 1
        var usernameErrorText = ""

        val specialCharactersPattern = Regex("\\W")
        if (specialCharactersPattern.containsMatchIn(username)) {
            usernameErrorText += "${usernameErrorCounter++}. Username can only consist of letters, numbers and underscores\n"
        }
        if (username.length < 3) {
            usernameErrorText += "${usernameErrorCounter++}. Username should be at least 3 characters"
        }

        if (usernameErrorText.isNotEmpty()){
            usernameField.error = usernameErrorText
        }
        return usernameErrorText.isEmpty()
    }

    private fun completeRegistration(uid: String?, username: String, level: Int) {
        val db = Firebase.firestore
        db.collection("users").document(uid!!)
            .update(mapOf(
                "username" to username,
                "level" to level,
            ))
    }

    private fun initUI() {
        finishButton = findViewById(R.id.finishButton)
        usernameField = findViewById(R.id.usernameField)
        usernameInput = findViewById(R.id.usernameInput)
        recyclerView = findViewById(R.id.avatar_recycler_view)
    }
}
