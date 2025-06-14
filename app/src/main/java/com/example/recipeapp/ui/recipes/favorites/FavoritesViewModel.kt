package com.example.recipeapp.ui.recipes.favorites

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.FAVORITE_SET_KEY
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Recipe

class FavoritesViewModel(private val application: Application) : AndroidViewModel(application) {

    data class FavoriteUIState(
        val favoriteList: List<Recipe> = listOf()
    )

    private val privateFavoriteList = MutableLiveData(FavoriteUIState())
    val favoriteList: LiveData<FavoriteUIState>
        get() = privateFavoriteList

    init {
        privateFavoriteList.value = FavoriteUIState()
    }

    fun loadFavorites() {
        return privateLoadFavorites()
    }

    private fun privateLoadFavorites() {
        val favoritesPrefs = getFavorites()
        val favoritesList = favoritesPrefs.getStringSet(FAVORITE_SET_KEY, emptySet())
            ?.mapNotNull { it.toIntOrNull() }
            ?.toSet() ?: emptySet()
        val favorites = STUB.getRecipesByIds(favoritesList)
        privateFavoriteList.value = FavoriteUIState(favoriteList = favorites)
    }

    private fun getFavorites(): SharedPreferences {
        return application.getSharedPreferences(FAVORITE_SET_KEY, Context.MODE_PRIVATE)
    }
}