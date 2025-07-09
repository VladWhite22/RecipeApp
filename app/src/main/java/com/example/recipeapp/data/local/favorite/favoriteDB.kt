package com.example.recipeapp.data.local.favorite

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [Favorite::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(FavoriteConverter::class)
abstract class FavoriteBase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}