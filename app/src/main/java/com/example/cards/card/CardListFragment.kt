package com.example.cards.card

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.cards.R
import com.example.cards.SettingsActivity
import com.example.cards.databinding.FragmentCardListBinding
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.Executors


class CardListFragment : Fragment(){
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var adapter: CardAdapter

    private var reference = FirebaseDatabase
        .getInstance()
        .getReference("cards")

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

    @SuppressLint("NotifyDataSetChanged")
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

        binding.cardListRecyclerView.adapter = adapter
        binding.newCardFab.setOnClickListener {
            val card = Card("", "")
            reference.child(card.id).setValue(card)
        }

        cardListViewModel.cards.observe(viewLifecycleOwner) {
            adapter.data = it
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_menu, menu)
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
