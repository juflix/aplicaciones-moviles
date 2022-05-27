package com.example.cards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CardAdapter : RecyclerView.Adapter<CardAdapter.CardHolder>() {

    var data =  listOf<Card>()

    class CardHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val questionTextView: TextView = itemView.findViewById(R.id.list_item_question)
        private val answerTextView:TextView = itemView.findViewById(R.id.list_item_answer)
        private val dateTextView: TextView = itemView.findViewById(R.id.list_item_date)

        fun bind(card: Card){
            questionTextView.text = card.question
            answerTextView.text = card.answer
            dateTextView.text = card.creationDate.substring(0,10)

            itemView.setOnClickListener {
                Toast.makeText(it.context, card.question, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_card, parent, false)
        return CardHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(
        holder: CardHolder,
        position: Int
    ) {
        holder.bind(data[position])
    }
}