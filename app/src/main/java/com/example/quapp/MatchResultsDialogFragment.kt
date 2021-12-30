package com.example.quapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.quapp.menu_dashboard.UserMenu


class MatchResultsDialogFragment: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.activity_match_results, container, false)

        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setTitle("Results")



        return rootView
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var correctsView: TextView = view.findViewById(R.id.corrects)
        var wrongsView: TextView = view.findViewById(R.id.wrongs)
        var pointsView: TextView = view.findViewById(R.id.points)
        val replayBtn: ImageView = view.findViewById(R.id.replay)
        val messageBtn: ImageView = view.findViewById(R.id.message)
        val leaveBtn: ImageView = view.findViewById(R.id.leave)

        val spanResults = boldResults()

        correctsView.text = spanResults[0]
        wrongsView.text = spanResults[1]
        pointsView.text = spanResults[2]


        replayBtn.setOnClickListener {
            val startGame = Intent(activity, QuizQuestionActivity::class.java)
            startActivity(startGame)
        }
        messageBtn.setOnClickListener {
            Toast.makeText(activity, "Soon to be added!", Toast.LENGTH_LONG).show()

        }
        leaveBtn.setOnClickListener {
            val userScreen = Intent(activity, UserMenu::class.java)
            startActivity(userScreen)
        }
    }

    private fun boldResults(): ArrayList<Spannable> {
        val bundleArgs: Bundle? = arguments

        val resC = bundleArgs!!.getInt("corrects").toString()
        val resW = bundleArgs!!.getInt("wrongs").toString()
        val resP = bundleArgs!!.getInt("points").toString()

        var correct: Spannable = SpannableString("Correct: ".plus(resC))
        var wrong: Spannable = SpannableString("Wrong:   ".plus(resW))
        var points: Spannable = SpannableString("Points:  ".plus(resP))

        correct = multiSpan(correct, resC.length)
        wrong = multiSpan(wrong, resW.length)
        points = multiSpan(points, resP.length)

        return arrayListOf(correct, wrong, points)
    }

    private fun multiSpan(span: Spannable, resLen: Int): Spannable {
        span.setSpan(
            StyleSpan(Typeface.BOLD), 10, span.length-resLen+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        span.setSpan(
            ForegroundColorSpan(Color.BLACK), 10, span.length-resLen+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )

        return span
    }
}
