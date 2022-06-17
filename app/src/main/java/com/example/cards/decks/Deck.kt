package com.example.cards.decks

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "decks_table")
data class Deck(
    var name: String,
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
){
    var difficultCards: Int = 0
    var doubtCards: Int = 0
    var easyCards: Int = 0
    constructor() : this(
        "",
        UUID.randomUUID().toString()
    )
}