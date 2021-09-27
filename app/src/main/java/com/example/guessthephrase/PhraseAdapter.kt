package com.example.guessthephrase

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class PhraseAdapter(val context: Context, val phrase_msg: ArrayList<String>):
    RecyclerView.Adapter<PhraseAdapter.ItemViewHolder>() {
    class ItemViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val text = phrase_msg[position]

        holder.itemView.apply {
            user_guss_tv.text = text
            if(text.startsWith("Found") || text.startsWith("You")){
                user_guss_tv.setTextColor(Color.GREEN)
            }else if(text.startsWith("No")||text.startsWith("Wrong")|| text.startsWith("Invalid")||text.startsWith("Incorrect")){
                user_guss_tv.setTextColor(Color.RED)
            }else{
                user_guss_tv.setTextColor(Color.BLACK)
            }
        }
    }
    override fun getItemCount(): Int = phrase_msg.size
}