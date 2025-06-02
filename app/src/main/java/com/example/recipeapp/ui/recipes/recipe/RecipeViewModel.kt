package com.example.recipeapp.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.FAVORITE_SET_KEY
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Recipe
import androidx.core.content.edit

class RecipeViewModel(private val application: Application) : AndroidViewModel(application) {
    data class RecipeUIState(
        val recipe: Recipe? = null,
        val isFavorite: Boolean = false,
        val portionsCount: Int = 1,
        val recipeImage: Drawable? = null,
    )

    private val privateRecipeState = MutableLiveData(RecipeUIState())
    val recipeState: LiveData<RecipeUIState>
        get() = privateRecipeState

    init {
        Log.d("RecipeViewModel", "isFavorite передано")
        privateRecipeState.value = RecipeUIState(isFavorite = true)
    }

    fun loadRecipe(id: Int) {
        if (id == -1) {
            Log.d("RecipeViewModel", "id == -1, download nothing  ")
            return
        }
        val recipe = STUB.getRecipeById(id)
        val favorites =
            getFavorites().getStringSet(FAVORITE_SET_KEY, mutableSetOf()) ?: mutableSetOf()
        val isFavorite = favorites.contains(id.toString())
        val image: Drawable? = try {
            Drawable.createFromStream(
                this.application.assets?.open(recipe?.imageUrl ?: ""),
                null)
        }catch (e: Exception) {
            Log.d("RecipeViewModel", "Image not found: ${recipeState.value?.recipe?.imageUrl}")
            null
        }

        privateRecipeState.value = RecipeUIState(
            recipe = recipe,
            isFavorite = isFavorite,
            recipeImage = image
        )
        Log.d("RecipeViewModel", "privateRecipeState.value:${privateRecipeState.value}")
    }

    private fun getFavorites(): SharedPreferences {
        return application.getSharedPreferences(FAVORITE_SET_KEY, Context.MODE_PRIVATE)
    }

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