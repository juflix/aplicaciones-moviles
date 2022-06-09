package com.example.cards.database

import androidx.room.Embedded
import androidx.room.Relation
import com.example.cards.Card
import com.example.cards.Deck

data class DeckWithCards(
    @Embedded
    val deck: Deck,
    @Relation(
        parentColumn = "deckId",
        entityColumn="deckId"
    )
    val cards: List<Card>
)