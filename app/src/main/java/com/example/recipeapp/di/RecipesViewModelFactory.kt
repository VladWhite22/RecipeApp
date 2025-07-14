package com.example.recipeapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.ui.recipes.recipeList.RecipesListVIewModel

class RecipesViewModelFactory(
    private val recipesRepository: RecipeRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipesListVIewModel::class.java)) {
            return RecipesListVIewModel(recipesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}