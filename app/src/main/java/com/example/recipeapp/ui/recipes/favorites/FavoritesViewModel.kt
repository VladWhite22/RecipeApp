package com.example.recipeapp.ui.recipes.favorites

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.RequestResult
import kotlinx.coroutines.launch

class FavoritesViewModel(private val application: Application) : AndroidViewModel(application) {

    data class FavoriteUIState(
        val favoriteList: List<Recipe>? = listOf()
    )

    private val recipeRepository = RecipeRepository(application)

    private val privateFavoriteList = MutableLiveData(FavoriteUIState())
    val favoriteList: LiveData<FavoriteUIState>
        get() = privateFavoriteList


    fun loadFavorites() {
        return privateLoadFavorites()
    }

    private fun privateLoadFavorites() {
        viewModelScope.launch {
            val favoritesList = recipeRepository.getFavorites().toSet()
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

}