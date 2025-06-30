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
import com.example.recipeapp.data.RecipeRepository

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
        if (id == -1) {
            Log.d("RecipeViewModel", "id == -1, download nothing  ")
            return
        }
        recipeRepository.getRecipeById(id) { recipe ->

            val favorites =
                getFavorites().getStringSet(FAVORITE_SET_KEY, mutableSetOf()) ?: mutableSetOf()
            val isFavorite = favorites.contains(id.toString())
            privateRecipeState.postValue(
                (recipeState.value ?: RecipeUIState()).copy(
                    recipe = recipe,
                    isFavorite = isFavorite,

                )
            )
            Log.d("RecipeViewModel", "privateRecipeState.value:${privateRecipeState.value}")
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