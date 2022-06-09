package com.example.cards.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.cards.Card

@Dao
interface CardDao {
    @Query("SELECT * FROM cards_table")
    fun getCards(): LiveData<List<Card>>

    @Query("SELECT * FROM cards_table WHERE id = :id")
    fun getCard(id : String): LiveData<Card?>

    @Insert
    fun addCard(card: Card)

    @Update
    fun update(card: Card)

    @Query("DELETE FROM cards_table")
    fun deleteCards()
}