package com.example.cards.decks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cards.R
import com.example.cards.database.CardDatabase
import com.example.cards.database.DeckWithCards
import com.example.cards.databinding.ListItemDeckBinding
import java.time.LocalDateTime
import java.util.concurrent.Executors

class DeckAdapter : RecyclerView.Adapter<DeckAdapter.DeckHolder>() {
    var data = listOf<DeckWithCards>()
    private lateinit var binding : ListItemDeckBinding
    inner class DeckHolder(view: View) : RecyclerView.ViewHolder(view){
        private var local = binding
        fun bind(deckWithCards: DeckWithCards){
            local.deck = deckWithCards.deck
            local.apply {
                cardNum = deckWithCards.cards.size
                dueCards = deckWithCards.cards.filter {
                    it.isDue(LocalDateTime.now())
                }.size

                deckDeleteButton.setOnClickListener {
                    val cardDatabase = CardDatabase.getInstance(local.deckInfo.context).cardDao
                    Executors.newSingleThreadExecutor().execute {
                        cardDatabase.deleteCardsByDeckId(deck!!.id)
                        cardDatabase.deleteDeck(deck!!.id)
                    }
                    notifyItemRemoved(bindingAdapterPosition)
                }
                deckCardsButton.setOnClickListener {
                    it.findNavController()
                        .navigate(DeckListFragmentDirections.actionDeckListFragmentToCardListFragment(deck!!.id))
                }
                deckEditButton.setOnClickListener {
                    it.findNavController()
                        .navigate(DeckListFragmentDirections.actionDeckListFragmentToDeckEditFragment(deck!!.id))
                }
                deckReviewButton.setOnClickListener {
                    if(dueCards > 0){
                        it.findNavController()
                            .navigate(DeckListFragmentDirections.actionDeckListFragmentToStudyFragment(deck!!.id))
                    } else {
                        Toast.makeText(it.context,it.resources.getString(R.string.no_more_cards_toast_message), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ListItemDeckBinding.inflate(layoutInflater, parent,false)
        return DeckHolder(binding.root)
    }
    override fun getItemCount() = data.size
    override fun onBindViewHolder(holder: DeckHolder, position: Int) {
        holder.bind(data[position])
    }
}