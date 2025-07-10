package com.example.recipeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipeapp.data.local.favorite.Favorite
import com.example.recipeapp.data.local.favorite.FavoriteConverter


@Database(
    entities = [Favorite::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(FavoriteConverter::class)
abstract class FavoriteBase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}