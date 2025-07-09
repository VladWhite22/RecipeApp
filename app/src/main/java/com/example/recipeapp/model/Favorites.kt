package com.example.recipeapp.data.local.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "saved_numbers")
data class Favorite(
    @PrimaryKey val id: Int = 1,
    val numbers: String
)
class FavoriteConverter {
    @TypeConverter
    fun fromList(numbers: List<Int>): String = numbers.joinToString(",")

    @TypeConverter
    fun toList(data: String): List<Int> =
        if (data.isEmpty()) emptyList()
        else data.split(",").map { it.toInt() }
}