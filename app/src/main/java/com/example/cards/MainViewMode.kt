package com.example.cards

import androidx.lifecycle.ViewModel
import timber.log.Timber
import java.time.LocalDateTime
import java.util.*
import kotlin.NoSuchElementException
import kotlin.random.Random.Default.nextInt

class MainViewModel : ViewModel() {
    var card: Card? = null
    private var cards: MutableList<Card> = mutableListOf<Card>()

    init {
        cards.add(Card("bone", "huesos"))
        cards.add(Card("nightmare", "pesadilla"))
        cards.add(Card("tent", "carpa"))
        cards.add(Card("staw", "pajita"))
        cards.add(Card("stamp", "sello"))
        cards.add(Card("to blink", "parpadear"))
        cards.add(Card("to shave", "afeitar"))
        card = random_card()
    }

    private fun dueCards() = cards.filter{card -> card.isDue(LocalDateTime.now())}

    private fun random_card() =  try {
        dueCards().random()
    } catch (e: NoSuchElementException) {
        null
    }

    fun update(quality: Int) {
        card?.quality = quality
        card?.update(LocalDateTime.now())
        card = random_card()
    }
}
