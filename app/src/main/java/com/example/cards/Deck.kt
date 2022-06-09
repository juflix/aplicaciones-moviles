package com.example.cards

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "decks_table")
data class Deck(
    @PrimaryKey val deckId: Long,
    val name: String
)