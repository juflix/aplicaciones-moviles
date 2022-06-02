package com.example.cards

import android.app.Application
import timber.log.Timber
import java.time.LocalDateTime

class CardsApplication : Application() {

    init {
        cards.add(Card("bone", "huesos"))
        cards.add(Card("nightmare", "pesadilla"))
        cards.add(Card("tent", "carpa"))
        cards.add(Card("staw", "pajita"))
        cards.add(Card("stamp", "sello"))
        cards.add(Card("to blink", "parpadear"))
        cards.add(Card("to shave", "afeitar"))
        cards.add(Card("couverture", "cobija"))
        cards.add(Card("chauve", "pelón"))
        cards.add(Card("trinquer", "bridar"))
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    companion object{
        var cards: MutableList<Card> = mutableListOf()
        fun numberOfDueCards() = cards.filter{it.isDue(LocalDateTime.now())}.size
        fun getCard(idCard : String) = cards.find { it.id == idCard }
        fun addCard(newCard : Card) = cards.add(newCard)
    }
}