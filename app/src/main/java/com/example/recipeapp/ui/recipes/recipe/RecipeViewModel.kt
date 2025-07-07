package com.example.recipeapp.ui.recipes.recipe

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.model.Recipe
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.local.DB
import com.example.recipeapp.data.local.favorite.Favorite
import com.example.recipeapp.data.local.favorite.FavoriteConverter
import com.example.recipeapp.model.RequestResult
import kotlinx.coroutines.launch

class RecipeViewModel(private val application: Application) : AndroidViewModel(application) {
    data class RecipeUIState(
        val recipe: Recipe? = null,
        val isFavorite: Boolean = false,
        val portionsCount: Int = 1,

        )

    private val recipeRepository = RecipeRepository(application)
    private val privateRecipeState = MutableLiveData(RecipeUIState())
    val recipeState: LiveData<RecipeUIState>
        get() = privateRecipeState


    fun loadRecipe(id: Int) {
        viewModelScope.launch {
            if (id == -1) {
                Log.d("RecipeViewModel", "id == -1, download nothing  ")
                return@launch
            }
            val favorites = recipeRepository.getFavorites()
            val isFavorite = favorites.contains(id)
            val result = recipeRepository.getRecipeById(id)
            when (result) {
                is RequestResult.Success<Recipe> -> {
                    privateRecipeState.value = RecipeUIState(
                        recipe = result.data,
                        isFavorite = isFavorite,
                    )
                }

                is RequestResult.Error -> {
                    Log.d("!!", "loadRecipe $result")
                    val recipeFromDb = (application as DB).recipeDb.recipesDao().getRecipeById(id)
                    if (recipeFromDb != null) {
                        privateRecipeState.value = RecipeUIState(
                            recipe = recipeFromDb,
                            isFavorite = isFavorite
                        )
                    } else {
                        Log.d("!!", "recipeFromDb not found")
                    }
                }
            }
        }
    }


    fun updateStateOfSeekbar(newState: Int) {
        privateRecipeState.value = privateRecipeState.value?.copy(portionsCount = newState)
    }

    fun onFavoriteClicked() {
        val currentState = privateRecipeState.value ?: return
        val recipeId = currentState.recipe?.id ?: return
        viewModelScope.launch {
            val favorites = recipeRepository.getFavorites().toMutableSet()
            val newFavoriteState = !currentState.isFavorite
            if (newFavoriteState) {
                favorites.add(recipeId)
            } else {
                favorites.remove(recipeId)
            }
            saveFavorites(favorites.toList())
            privateRecipeState.value = currentState.copy(isFavorite = newFavoriteState)
        }
    }

    fun saveFavorites(favorites: List<Int>) {
        val favoriteEntity = Favorite(
            id = 1,
            numbers = FavoriteConverter().fromList(favorites)
        )
        viewModelScope.launch {
            recipeRepository.saveFavorites(favoriteEntity)
        }
    }

}