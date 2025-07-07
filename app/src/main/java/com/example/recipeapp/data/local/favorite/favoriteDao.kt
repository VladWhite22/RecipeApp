package com.example.recipeapp.data.local.favorite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT numbers FROM saved_numbers WHERE id = 1")
    suspend fun getNumbersString(): String?

    suspend fun getNumbers(): List<Int> {
        return getNumbersString()?.let {
            FavoriteConverter().toList(it)
        } ?: emptyList()
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNumbers(holder: Favorite)
}
