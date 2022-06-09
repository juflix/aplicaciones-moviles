package com.example.cards

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.cards.database.CardDatabase
import com.example.cards.databinding.FragmentCardListBinding
import java.util.concurrent.Executors


class CardListFragment : Fragment(){
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var adapter: CardAdapter

    private val cardListViewModel by lazy{
        ViewModelProvider(this).get(CardListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentCardListBinding>(
            inflater,
            R.layout.fragment_card_list,
            container,
            false)

        adapter = CardAdapter()
        adapter.data = emptyList()
        binding.cardListRecyclerView.adapter = adapter

        // TODO("add a card -> Cancel creates an empty card")

        binding.newCardFab.setOnClickListener {
            val card = Card("", "")
            executor.execute{
                CardDatabase.getInstance(it.context).cardDao.addCard(card)
            }
            it.findNavController()
                .navigate(CardListFragmentDirections.actionCardListFragmentToCardEditFragment(card.id))
        }

        cardListViewModel.cards.observe(viewLifecycleOwner) {
            adapter.data = it
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }
}