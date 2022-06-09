package com.example.cards

import android.app.Application
import com.example.cards.database.CardDatabase
import timber.log.Timber
import java.time.LocalDateTime
import java.util.concurrent.Executors

class CardsApplication : Application() {
    private val executor = Executors.newSingleThreadExecutor()

    override fun onCreate() {
        super.onCreate()
        val cardDatabase = CardDatabase.getInstance(applicationContext)

        executor.execute{
            cardDatabase.cardDao.deleteCards()
            cardDatabase.cardDao.addCard(Card("tent", "carpa"))
            cardDatabase.cardDao.addCard(Card("stamp", "sello"))
            cardDatabase.cardDao.addCard(Card("bone", "huesos"))
        }

        Timber.plant(Timber.DebugTree())
    }

    init {
        cards.add(Card("bone", "huesos"))
        cards.add(Card("nightmare", "pesadilla"))
        cards.add(Card("tent", "carpa"))
        cards.add(Card("staw", "paja"))
        cards.add(Card("stamp", "sello"))
        cards.add(Card("to blink", "parpadear"))
        cards.add(Card("to shave", "afeitar"))
        cards.add(Card("couverture", "cobija"))
        cards.add(Card("chauve", "pelón"))
        cards.add(Card("trinquer", "bridar"))
    }

    companion object{
        var cards: MutableList<Card> = mutableListOf()
        fun numberOfDueCards() = cards.filter{it.isDue(LocalDateTime.now())}.size
        fun getCard(idCard : String) = cards.find { it.id == idCard }
        fun addCard(newCard : Card) = cards.add(newCard)
    }
}