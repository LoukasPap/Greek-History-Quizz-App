package com.example.quapp.menu_dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quapp.Match
import com.example.quapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class UserHistoryFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    private var layoutManager: RecyclerView.LayoutManager?= null
    private lateinit var recyclerView: RecyclerView

    var matchIdList: MutableList<String> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        auth = Firebase.auth
        initUI()

        val userid = authenticate()

//        retrieveMatchIdsFromFirestore(userid) { mIds ->


//            Log.d("hist", "The match ids:\n${mIds}")
//            mIds.forEach{matchIdList.add(it)}

        retrieveMatchInfoFromFirestore(userid) { mInfo ->
            layoutManager = LinearLayoutManager(activity)
            recyclerView.layoutManager = layoutManager
            val adapter = MyMatchAdapter()
            adapter.replaceItems(mInfo)
            recyclerView.adapter = adapter
        }
//        }
    }

   private fun retrieveMatchIdsFromFirestore(uid: String, cb:(MutableList<String>) -> Unit) {
        val userDocRef = db.collection("users").document(uid)
        userDocRef.get()
            .addOnSuccessListener { documentSnapShot ->
                cb(documentSnapShot.get("matches") as MutableList<String>)

        }
    }

    private fun retrieveMatchInfoFromFirestore(uid: String, cb:(MutableList<Match>) -> Unit) {
        var matchesInfoList = mutableListOf<Match>()
        val matchDocRef = db.collection("matches").whereEqualTo("playerID", uid)
        matchDocRef.get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    matchesInfoList.add(doc.toObject<Match>())
                }
                Log.d("hist", "$uid \nMATCHES: $matchesInfoList")
                cb(matchesInfoList)
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

    private fun initUI() {
        recyclerView = activity!!.findViewById(R.id.match_recycler_view)
    }


    class MyMatchAdapter : RecyclerView.Adapter<MyMatchAdapter.ViewHolder>() {
        private var items = listOf<Match>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_match, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]

            holder.pointsView.text = item.totalPoints.toString().plus("\nPoints")
            holder.correctView.text = (item.correctAnswers.size).toString()
            holder.wrongView.text = (item.questions.size - item.correctAnswers.size).toString()
            holder.datetimeView.text = formatTimestamp(item.timestamp)
//            holder.categoryView.text = "True\nFalse"
        }

        fun formatTimestamp(date: Date?): String {
            val sdf = SimpleDateFormat("dd/MM/yy")
            return sdf.format(date).toString()
        }

        fun replaceItems(items: MutableList<Match>) {
            this.items = items
        }

        override fun getItemCount(): Int = items.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var pointsView: TextView = view.findViewById(R.id.history_points_view)
            var correctView: TextView = view.findViewById(R.id.history_correct_text)
            var wrongView: TextView = view.findViewById(R.id.history_wrong_text)
            var datetimeView: TextView = view.findViewById(R.id.history_datetime_text)
            var categoryView: TextView = view.findViewById(R.id.history_category_text)

        }
    }
}