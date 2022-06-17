package com.example.cards.decks

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.cards.database.CardDatabase

class DeckEditViewModel(application: Application): AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val deckId = MutableLiveData<String>()

    var name = ""

    val deck: LiveData<Deck> = Transformations.switchMap(deckId) {
        CardDatabase.getInstance(context).cardDao.getDeck(it)
    }

    fun loadDeckId(id: String) {
        deckId.value = id
    }
}