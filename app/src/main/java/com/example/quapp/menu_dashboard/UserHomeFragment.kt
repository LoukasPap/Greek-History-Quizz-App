package com.example.quapp.menu_dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.quapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class UserHomeFragment : Fragment() {

    private lateinit var avatar: ImageView
    private lateinit var username: TextView
    private lateinit var level: TextView

    private lateinit var userid: String

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        avatar = view.findViewById(R.id.avatarView)
        username = view.findViewById(R.id.usernameView)
        level = view.findViewById(R.id.levelView)

        userid = authenticate()
        updateUI(userid)
    }

    private fun updateUI(uid: String) {
        val docRef = db.collection("users").document(uid)
        docRef.get()
            .addOnSuccessListener { documentSnapShot ->
                username.text = documentSnapShot.data?.getValue("username").toString().plus(documentSnapShot.data?.getValue("username").toString())
                level.text = "level ".plus(documentSnapShot.data?.getValue("level").toString())

                var selection = loadSelectedAvatar((documentSnapShot.data?.getValue("avatar")).toString())
                avatar.setImageResource(selection)
            }
    }

    private fun loadSelectedAvatar(selection: String): Int {
        return when(selection.toInt()) {
            0 -> R.drawable.man1_ma
            1 -> R.drawable.woman1_5
            2 -> R.drawable.man2_ma2
            3 -> R.drawable.woman2_aa1
            else -> R.drawable.man1_ma
        }
    }

    private fun authenticate(): String {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.d("menu", "UID IS: ${auth.currentUser?.uid}")
            return currentUser.uid
        } else {
            Log.d("menu", "Not signed in")
            return ""
        }
    }
}