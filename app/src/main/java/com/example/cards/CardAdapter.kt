package com.example.cards

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CardAdapter : RecyclerView.Adapter<CardAdapter.CardHolder>() {

    var data =  listOf<Card>()

    class CardHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        return CardHolder(TextView(parent.context))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(
        holder: CardHolder,
        position: Int
    ) {
        val item = data[position]
        (holder.itemView as TextView).text = "${item.question} \n ${item.answer}"
    }
}