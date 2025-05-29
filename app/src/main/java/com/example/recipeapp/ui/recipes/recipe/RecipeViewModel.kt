package com.example.recipeapp.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.Recipe

class RecipeViewModel : ViewModel() {
    data class RecipeUIState(
        val recipe: Recipe? = null,
        val isFavorite: Boolean = false,
        val portion: Int = 1,
    )

    private val privateRecipeState = MutableLiveData<RecipeUIState>()
    val recipeState: LiveData<RecipeUIState>
        get() = privateRecipeState
    init {
        Log.d("RecipeViewModel","isFavorite передано")
        privateRecipeState.value = RecipeUIState(isFavorite = true)
    }
}