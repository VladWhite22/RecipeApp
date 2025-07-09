package com.example.recipeapp.data.local

import AppDatabase
import android.app.Application
import androidx.room.Room
import com.example.recipeapp.data.local.favorite.FavoriteBase
import com.example.recipeapp.data.local.recipeList.RecipeDB
import kotlin.jvm.java

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
    val favoriteDb: FavoriteBase by lazy {
        Room.databaseBuilder(
            applicationContext,
            FavoriteBase::class.java,
            "favorite-database"
        ).build()
    }
}