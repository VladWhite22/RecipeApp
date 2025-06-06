package com.example.recipeapp.ui.recipes.favorites

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.example.recipeapp.FAVORITE_SET_KEY
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Recipe

class FavoritesViewModel(private val application: Application) : AndroidViewModel(application) {

    fun loadFavorites(): List<Recipe> {
        return privateLoadFavorites()
    }

    private fun privateLoadFavorites(): List<Recipe> {
        val favoritesPrefs = getFavorites()
        val favoritesList = favoritesPrefs.getStringSet(FAVORITE_SET_KEY, emptySet())
            ?.mapNotNull { it.toIntOrNull() }
            ?.toSet() ?: emptySet()
        return STUB.getRecipesByIds(favoritesList)
    }

    private fun getFavorites(): SharedPreferences {
        return application.getSharedPreferences(FAVORITE_SET_KEY, Context.MODE_PRIVATE)
    }
}
