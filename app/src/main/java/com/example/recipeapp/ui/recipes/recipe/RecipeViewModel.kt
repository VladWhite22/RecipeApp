package com.example.recipeapp.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.Recipe

class RecipeViewModel : ViewModel() {

    data class RecipeUIState(
        val recipe: Recipe? = null,
        val favorite: Boolean = false,
        val portion: Int = 1,
    )
}