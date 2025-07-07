package com.example.recipeapp.data.local

import AppDatabase
import android.app.Application
import androidx.room.Room
import com.example.recipeapp.data.local.recipeList.RecipeDB

class DB : Application() {
    val categoryDB: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "categories-database"
        ).build()
    }
    val recipeDb: RecipeDB by lazy {
        Room.databaseBuilder(
            applicationContext,
            RecipeDB::class.java,
            "recipe-database"
        ).build()
    }
}