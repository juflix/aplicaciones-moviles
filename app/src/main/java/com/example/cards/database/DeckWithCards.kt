package com.example.cards.database

import androidx.room.Embedded
import androidx.room.Relation
import com.example.cards.card.Card
import com.example.cards.decks.Deck

data class DeckWithCards(
    @Embedded
    val deck: Deck,
    @Relation(
        parentColumn = "id",
        entityColumn = "deckId"
    )
    val cards: List<Card>
)