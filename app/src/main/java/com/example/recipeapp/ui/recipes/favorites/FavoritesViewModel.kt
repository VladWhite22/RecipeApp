package com.example.recipeapp.ui.recipes.favorites

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.FAVORITE_SET_KEY
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.RequestResult
import kotlinx.coroutines.launch

class FavoritesViewModel(private val application: Application) : AndroidViewModel(application) {

    data class FavoriteUIState(
        val favoriteList: List<Recipe>? = listOf()
    )

    private val recipeRepository = RecipeRepository()

    private val privateFavoriteList = MutableLiveData(FavoriteUIState())
    val favoriteList: LiveData<FavoriteUIState>
        get() = privateFavoriteList


    fun loadFavorites() {
        return privateLoadFavorites()
    }

    private fun privateLoadFavorites() {
        viewModelScope.launch {
            val favoritesPrefs = getFavorites()
            val favoritesList = favoritesPrefs.getStringSet(FAVORITE_SET_KEY, emptySet())
                ?.mapNotNull { it.toIntOrNull() }
                ?.toSet() ?: emptySet()

            val result = recipeRepository.getRecipesByCategoryIds(favoritesList)
            when (result) {
                is RequestResult.Success<List<Recipe>> -> {
                    privateFavoriteList.value = FavoriteUIState(
                        favoriteList = result.data
                    )
                }

                is RequestResult.Error -> Log.d("!!", "loadCategory $result")
            }
        }
    }

    private fun getFavorites(): SharedPreferences {
        return application.getSharedPreferences(FAVORITE_SET_KEY, Context.MODE_PRIVATE)
    }

}