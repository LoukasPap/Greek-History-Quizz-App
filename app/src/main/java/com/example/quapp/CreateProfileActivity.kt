package com.example.quapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CreateProfileActivity : AppCompatActivity() {

    private lateinit var finishButton: Button

    private var layoutManager: RecyclerView.LayoutManager?= null
//    private var adapter: RecyclerView.Adapter<AvatarRecyclerAdapter.ViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)

        val recyclerView: RecyclerView = findViewById(R.id.avatar_recycler_view)

        layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        var adapter = AvatarRecyclerAdapter()
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : AvatarRecyclerAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(this@CreateProfileActivity, "Selected item no.$position", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun initUI() {
        finishButton = findViewById(R.id.finishButton)

    }
}