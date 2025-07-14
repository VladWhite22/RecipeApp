package com.example.recipeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.aseducationalproject.Domain.Converters
import com.example.recipeapp.data.local.favorite.Favorite
import com.example.recipeapp.data.local.favorite.FavoriteConverter
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe

@Database(
    entities = [Category::class, Recipe::class, Favorite::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class, FavoriteConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun FoodCategoryDao(): FoodCategoryDao
}





