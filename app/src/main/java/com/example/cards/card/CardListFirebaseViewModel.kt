package com.example.cards.card

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CardListFirebaseViewModel : ViewModel() {

    var cards = MutableLiveData<List<Card>>()
    private var reference = FirebaseDatabase.getInstance().getReference("cards")

    init {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listOfCards = mutableListOf<Card>()

                for (child in snapshot.children) {
                    child.getValue(Card::class.java)?.let {
                        listOfCards.add(it)
                    }
                }
                cards.value = listOfCards
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}