package com.example.cards.decks

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cards.database.CardDatabase
import com.example.cards.database.DeckWithCards

class DeckListViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    var decksWithCards: LiveData<List<DeckWithCards>> = CardDatabase.getInstance(context).cardDao.getDecksWithCards()
    var deckNumber: LiveData<Int> = Transformations.map(decksWithCards) {
        it.size
    }
}