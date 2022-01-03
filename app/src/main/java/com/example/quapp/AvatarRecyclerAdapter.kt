package com.example.quapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AvatarRecyclerAdapter: RecyclerView.Adapter<AvatarRecyclerAdapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickListener
    private var auth = Firebase.auth

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    private var avatarImagesArray = intArrayOf(
        R.drawable.man1__, R.drawable.woman1_2,
        R.drawable.man2_20, R.drawable.woman2_wb,
        R.drawable.man3_be, R.drawable.woman3_18)

    private var avatarSelectedArray = arrayOf(false, false, false, false ,false, false)

    private var imgViews: MutableList<ImageView?> = mutableListOf(null, null, null, null, null, null)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_avatar, parent, false)
        return ViewHolder(v, mListener)
    }

    override fun onBindViewHolder(holder: AvatarRecyclerAdapter.ViewHolder, position: Int) {
        holder.itemSelected = avatarSelectedArray[position]
        holder.itemImage.setImageResource(avatarImagesArray[position])
    }

    override fun getItemCount(): Int {
        return avatarImagesArray.size
    }

    inner class ViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView = itemView.findViewById(R.id.avatarImage)
        var itemSelected = false

        init {
            itemView.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }

            itemImage.setOnClickListener {
                updateAvatarSelection(layoutPosition)
                uncheckRest()

                imgViews.add(layoutPosition, itemImage)

                itemImage.foreground = ContextCompat.getDrawable(itemImage.context, R.drawable.avatar_selected)
                unselectRestAvatars()
                avatarSelectedArray[layoutPosition] = true

            }
        }

        private fun updateAvatarSelection (selection: Int) {
            val db = Firebase.firestore
            db.collection("users").document(auth.currentUser!!.uid)
                .update("avatar", selection)
        }

        private fun unselectRestAvatars() {
            for (av in avatarSelectedArray.indices) avatarSelectedArray[av]=false
        }

        private fun uncheckRest() {
            for (img in imgViews) {
                img?.foreground = null
            }
        }
    }
}