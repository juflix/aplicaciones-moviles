package com.example.cards.decks

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.cards.R
import com.example.cards.database.CardDatabase
import com.example.cards.databinding.FragmentDeckEditBinding
import java.util.concurrent.Executors

class DeckEditFragment : Fragment() {
    private val executor = Executors.newSingleThreadExecutor()
    lateinit var deck : Deck
    lateinit var binding : FragmentDeckEditBinding
    private val deckViewModel : DeckEditViewModel by lazy {
        ViewModelProvider(this)[DeckEditViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        val nameTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                deckViewModel.name = p0.toString()
            }
            override fun afterTextChanged(p0: Editable?) {}
        }
        binding.deckNameEditText.addTextChangedListener(nameTextWatcher)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_deck_edit,
            container,
            false)
        val args = DeckEditFragmentArgs.fromBundle(requireArguments())
        if(args.deckId == null){
            deck = Deck("")
            binding.deck?.name = deckViewModel.name
        }
        args.deckId?.let {
            deckViewModel.loadDeckId(it)
            deckViewModel.deck.observe(viewLifecycleOwner) { d ->
                deck = d
                binding.deck?.name = deckViewModel.name
            }
        }
        binding.acceptDeckEditButton.setOnClickListener {

            deck.name = deckViewModel.name
            if (args.deckId == null){
                executor.execute {
                    CardDatabase.getInstance(requireContext()).cardDao.addDeck(deck)
                }
            } else {
                executor.execute {
                    CardDatabase.getInstance(requireContext()).cardDao.updateDeck(deck)
                }
            }
            it.findNavController()
                .navigate(DeckEditFragmentDirections.actionDeckEditFragmentToDeckListFragment())
        }
        binding.cancelDeckEditButton.setOnClickListener {
            it.findNavController()
                .navigate(DeckEditFragmentDirections.actionDeckEditFragmentToDeckListFragment())
        }
        return binding.root
    }

}