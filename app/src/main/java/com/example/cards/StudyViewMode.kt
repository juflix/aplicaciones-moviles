package com.example.cards

import android.app.Application
import androidx.lifecycle.*
import com.example.cards.database.CardDatabase
import java.time.LocalDateTime
import java.util.concurrent.Executors
import kotlin.NoSuchElementException

class StudyViewModel(application: Application) : AndroidViewModel(application) {
    private val executor = Executors.newSingleThreadExecutor()
    private val context = getApplication<Application>().applicationContext

    var card: Card? = null
    var cards: LiveData<List<Card>> = CardDatabase.getInstance(context).cardDao.getCards()
    var dueCard: LiveData<Card?> = Transformations.map(cards) {
        try {
            it.filter {
                it.isDue(LocalDateTime.now())
            }.random()
        } catch (e: Exception) {
            null
        }
    }

    var nDueCards: LiveData<Int> = Transformations.map(cards) { list ->
        list.filter {
            it.isDue(LocalDateTime.now())
        }.size
    }

    fun update(quality: Int) {
        card?.quality = quality
        card?.update(LocalDateTime.now())
        executor.execute {
            CardDatabase.getInstance(context).cardDao.update(card!!)
        }
    }
}
