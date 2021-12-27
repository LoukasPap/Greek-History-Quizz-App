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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateProfileActivity : AppCompatActivity() {

    private lateinit var finishButton: Button
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
        adapter.setOnItemClickListener(object : AvatarRecyclerAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(this@CreateProfileActivity, "Selected item no.$position", Toast.LENGTH_SHORT).show()
            }
        })

        finishButton.setOnClickListener {
            completeRegistration(userId, usernameInput.text.toString(), 1, 0)
            Toast.makeText(this, "Completed", Toast.LENGTH_LONG).show()
            val userScreen = Intent(this, UserMenu::class.java)
            startActivity(userScreen)
        }
    }

    private fun completeRegistration(uid: String?, username: String, avatar: Int, level: Int) {
        val db = Firebase.firestore
        db.collection("users").document(uid!!)
            .update(mapOf(
                "username" to username,
                "avatar" to avatar,
                "level" to level,
            ))
    }

    private fun initUI() {
        finishButton = findViewById(R.id.finishButton)
        usernameInput = findViewById(R.id.usernameInput)
        recyclerView = findViewById(R.id.avatar_recycler_view)
    }
}
