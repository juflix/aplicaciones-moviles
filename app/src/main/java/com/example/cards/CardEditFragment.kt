package com.example.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.cards.databinding.FragmentCardListBinding
import com.example.cards.databinding.FragmentTitleBinding

class CardEditFragment : Fragment() {
    lateinit var card: Card
    lateinit var binding: FragmentCardListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_card_edit,
            container,
            false)

        val args = CardEditFragmentArgs.fromBundle(requireArguments())
        card = CardsApplication.getCard(args.cardId) ?: throw Exception("Wrong id")
        binding.card = card

        return binding.root
    }
}