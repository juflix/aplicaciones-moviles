package com.example.cards.decks

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.example.cards.*
import com.example.cards.databinding.FragmentDeckListBinding
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.Executors


class DeckListFragment : Fragment(){
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var adapter: DeckAdapter
    private var reference = FirebaseDatabase
        .getInstance()
        .getReference("decks")

    private val deckListViewModel by lazy{
        ViewModelProvider(this).get(DeckListViewModel::class.java)
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
        val binding = DataBindingUtil.inflate<FragmentDeckListBinding>(
            inflater,
            R.layout.fragment_deck_list,
            container,
            false)

        adapter = DeckAdapter()
        adapter.data = emptyList()
        binding.apply {
            deckListRecyclerView.adapter = adapter
            newDeckFab.setOnClickListener {
                it.findNavController()
                    .navigate(DeckListFragmentDirections.actionDeckListFragmentToDeckEditFragment(null))
            }
        }
        deckListViewModel.deckNumber.observe(viewLifecycleOwner) {
            binding.deckNumber = it
        }
        deckListViewModel.decksWithCards.observe(viewLifecycleOwner) {
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