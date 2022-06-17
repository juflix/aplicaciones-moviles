package com.example.cards

import android.app.Application
import com.example.cards.card.Card
import com.example.cards.database.CardDatabase
import timber.log.Timber
import java.time.LocalDateTime
import java.util.concurrent.Executors

class CardsApplication : Application() {
    private val executor = Executors.newSingleThreadExecutor()

    override fun onCreate() {
        super.onCreate()
        val cardDatabase = CardDatabase.getInstance(applicationContext)

        Timber.plant(Timber.DebugTree())
    }

    companion object{
        var cards: MutableList<Card> = mutableListOf()
        fun numberOfDueCards() = cards.filter{it.isDue(LocalDateTime.now())}.size
        fun getCard(idCard : String) = cards.find { it.id == idCard }
        fun addCard(newCard : Card) = cards.add(newCard)
    }


}