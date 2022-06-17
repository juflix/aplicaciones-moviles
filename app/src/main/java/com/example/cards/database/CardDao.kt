package com.example.cards.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cards.card.Card
import com.example.cards.decks.Deck

@Dao
interface CardDao {
    @Query("SELECT * FROM cards_table")
    fun getCards(): LiveData<List<Card>>

    @Query("SELECT * FROM cards_table WHERE id = :id")
    fun getCard(id : String): LiveData<Card?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCard(card: Card)

    @Update
    fun update(card: Card)

    @Query("DELETE FROM cards_table")
    fun deleteCards()

    @Query("DELETE FROM cards_table WHERE deckId = :id")
    fun deleteCardsByDeckId(id: String)


    @Query("SELECT * FROM decks_table WHERE id = :id")
    fun getDeck(id : String): LiveData<Deck?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDeck(deck: Deck)

    @Update
    fun updateDeck(deck: Deck)

    @Query("DELETE FROM decks_table WHERE id = :id")
    fun deleteDeck(id: String)

    @Transaction
    @Query("SELECT * FROM decks_table")
    fun getDecksWithCards(): LiveData<List<DeckWithCards>>

    @Transaction
    @Query("SELECT * FROM decks_table WHERE id = :deckId")
    fun getDeckWithCards(deckId: Long): LiveData<List<DeckWithCards>>
}