package com.example.recipeapp.data.local.recipeList

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.aseducationalproject.Domain.Converters
import com.example.recipeapp.model.Recipe

@Database(
    entities = [Recipe::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RecipeDB : RoomDatabase() {
    abstract fun recipesDao(): RecipeDao
}





