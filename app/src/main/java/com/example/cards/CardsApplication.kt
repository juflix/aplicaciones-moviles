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

        for(i in 1..100){
            cards.add(Card("question$i", "pregunta$i"))
        }

    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    companion object{
        var cards: MutableList<Card> = mutableListOf<Card>()
        fun numberOfDueCards() = cards.filter{it.isDue(LocalDateTime.now())}.size
    }
}