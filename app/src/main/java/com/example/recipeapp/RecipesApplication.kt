package com.example.recipeapp

import AppDatabase
import android.app.Application
import androidx.room.Room
import com.example.recipeapp.data.local.favorite.FavoriteBase
import com.example.recipeapp.data.local.recipeList.RecipeDB
import com.example.recipeapp.di.AppContainer

class RecipesApplication() : Application() {
    val appContainer by lazy { AppContainer(this) }

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
