package com.example.quapp.menu_dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.quapp.LoginActivity
import com.example.quapp.QuizQuestionActivity
import com.example.quapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class UserHomeFragment : Fragment() {

    private lateinit var avatar: ImageView
    private lateinit var username: TextView
    private lateinit var level: TextView
    private lateinit var card: CardView
    private lateinit var settingsBtn: ImageButton

    private lateinit var userid: String

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        auth = Firebase.auth

        userid = authenticate()
        updateUI(userid)

        card.setOnClickListener {
            val startGame = Intent(activity, QuizQuestionActivity::class.java)
            startActivity(startGame)
            activity?.overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out)
        }

        settingsBtn.setOnClickListener { v: View ->
            showMenu(v, R.menu.settings_popup_menu)
        }
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val wrapper = ContextThemeWrapper(context, R.style.SettingMenu_PopupMenu)
        val popup = PopupMenu(wrapper, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.account -> {
                    Toast.makeText(activity,"Coming soon!", Toast.LENGTH_SHORT).show()
                }
                R.id.logout -> {
                    FirebaseAuth.getInstance().signOut()
                    gotoLoginForm()
                    Toast.makeText(activity,"Hope we see you soon!", Toast.LENGTH_LONG).show()
                }
            }
            true
        }

        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }

        // Show the popup menu.
        popup.show()
    }

    private fun updateUI(uid: String) {
        val docRef = db.collection("users").document(uid)
        docRef.get()
            .addOnSuccessListener { documentSnapShot ->
                username.text = documentSnapShot.data?.getValue("username").toString()
                level.text = "level ".plus(documentSnapShot.data?.getValue("level").toString())

                val selection = loadSelectedAvatar((documentSnapShot.data?.getValue("avatar")).toString())
                avatar.setImageResource(selection)
            }
    }

    private fun initUI() {
        settingsBtn = view!!.findViewById(R.id.settingsMenu)
        avatar = view!!.findViewById(R.id.avatarView)
        username = view!!.findViewById(R.id.usernameView)
        level = view!!.findViewById(R.id.levelView)
        card = view!!.findViewById(R.id.cardView)
    }

    private fun loadSelectedAvatar(selection: String): Int {
        return when(selection.toInt()) {
            0 -> R.drawable.man1__
            1 -> R.drawable.woman1_2
            2 -> R.drawable.man2_20
            3 -> R.drawable.woman2_wb
            4 -> R.drawable.man3_be
            else -> R.drawable.woman3_18
        }
    }

    private fun authenticate(): String {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        return if (currentUser != null) {
            Log.d("menu", "UID IS: ${auth.currentUser?.uid}")
            currentUser.uid
        } else {
            Log.d("menu", "Not signed in")
            ""
        }
    }

    private fun gotoLoginForm() {
        val loginScreen = Intent(activity, LoginActivity::class.java)
        startActivity(loginScreen)
    }
}