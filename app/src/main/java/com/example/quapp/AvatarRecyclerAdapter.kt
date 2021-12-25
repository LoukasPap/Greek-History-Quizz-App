package com.example.quapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AvatarRecyclerAdapter: RecyclerView.Adapter<AvatarRecyclerAdapter.ViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    private var avatarImagesArray = intArrayOf(
        R.drawable.man1_ma, R.drawable.woman1_5, R.drawable.man2_ma2, R.drawable.woman2_aa1)
    private var avatarSelectedArray = arrayOf(false, false, false, false)

    private var imgViews: MutableList<ImageView?> = mutableListOf(null, null, null, null)
    private var textViews: MutableList<TextView?> = mutableListOf(null, null, null, null)


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

    @SuppressLint("ResourceAsColor")
    inner class ViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView = itemView.findViewById(R.id.avatarImage)
        var itemSelected = false

        init {
            itemView.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }


            itemImage.setOnClickListener {
                uncheckRest()

                imgViews.add(layoutPosition, itemImage)
                itemImage.alpha = 0.7F
//                itemImage.setColorFilter(Color.argb(255-255/3,255,255, 255))


                unselectRestAvatars()
                avatarSelectedArray[layoutPosition] = true

                android.util.Log.d("ava", "" +
                        "\n                     " +
                        "\nPOSITION ${this.layoutPosition} " +
                        "\nTHIS ITEM ${avatarSelectedArray[this.layoutPosition]}")

            }
        }

        private fun unselectRestAvatars() {
            for (av in avatarSelectedArray.indices) avatarSelectedArray[av]=false
        }

        private fun uncheckRest() {
            for (img in imgViews) {
                img?.alpha = 1F
            }
        }
    }


}