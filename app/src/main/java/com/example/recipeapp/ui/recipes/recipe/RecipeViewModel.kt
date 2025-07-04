package com.example.recipeapp.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.FAVORITE_SET_KEY
import com.example.recipeapp.model.Recipe
import androidx.core.content.edit
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.local.DB
import com.example.recipeapp.model.RequestResult
import com.example.recipeapp.ui.recipes.recipeList.RecipesListVIewModel.RecipeListUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeViewModel(private val application: Application) : AndroidViewModel(application) {
    data class RecipeUIState(
        val recipe: Recipe? = null,
        val isFavorite: Boolean = false,
        val portionsCount: Int = 1,

        )

    private val recipeRepository = RecipeRepository()
    private val privateRecipeState = MutableLiveData(RecipeUIState())
    val recipeState: LiveData<RecipeUIState>
        get() = privateRecipeState


    fun loadRecipe(id: Int) {
        viewModelScope.launch {
            if (id == -1) {
                Log.d("RecipeViewModel", "id == -1, download nothing  ")
                return@launch
            }
            val favorites =
                getFavorites().getStringSet(FAVORITE_SET_KEY, mutableSetOf()) ?: mutableSetOf()
            val isFavorite = favorites.contains(id.toString())
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
                    val recipeFromDb = withContext(Dispatchers.IO) {
                        (application as DB).recipeDb.recipesDao().getRecipeById(id)
                    }
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


    private fun getFavorites(): SharedPreferences {
        return application.getSharedPreferences(FAVORITE_SET_KEY, Context.MODE_PRIVATE)
    }

    fun updateStateOfSeekbar(newState: Int) {
        privateRecipeState.value = privateRecipeState.value?.copy(portionsCount = newState)
    }

    fun onFavoriteClicked() {
        val currentState = privateRecipeState.value ?: return
        val recipeId = currentState.recipe?.id ?: return

        val favorites =
            getFavorites().getStringSet(FAVORITE_SET_KEY, mutableSetOf())?.toMutableSet()
                ?: mutableSetOf()
        val newFavoriteState = !currentState.isFavorite

        if (newFavoriteState) {
            favorites.add(recipeId.toString())
        } else {
            favorites.remove(recipeId.toString())
        }
        saveFavorites(favorites)
        privateRecipeState.value = currentState.copy(isFavorite = newFavoriteState)
    }

    fun saveFavorites(favorites: MutableSet<String>) {
        getFavorites().edit() {
            putStringSet(FAVORITE_SET_KEY, favorites)
            apply()
        }
    }

}