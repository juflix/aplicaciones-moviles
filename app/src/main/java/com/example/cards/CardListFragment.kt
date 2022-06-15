package com.example.cards

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.example.cards.database.CardDatabase
import com.example.cards.databinding.FragmentCardListBinding
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.Executors


class CardListFragment : Fragment(){
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var adapter: CardAdapter

    private val cardListViewModel by lazy{
        ViewModelProvider(this).get(CardListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        PreferenceManager.setDefaultValues(
            requireContext(),
            R.xml.root_preferences,
            false
        )
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

        SettingsActivity.setLoggedIn(requireContext(), true)

        // TODO("add a card -> Cancel creates an empty card")
        binding.cardListRecyclerView.adapter = adapter
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_card_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(requireContext(), SettingsActivity::class.java))
            }
        }
        return true
    }
}
