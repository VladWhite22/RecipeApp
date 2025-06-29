package com.example.recipeapp.ui.recipes.recipeList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe

class RecipesListVIewModel(application: Application) : AndroidViewModel(application) {
    data class RecipeListUIState(
        val recipeList: List<Recipe> = listOf(),
    )

    private val recipeRepository = RecipeRepository()

    private val privateRecipeListState = MutableLiveData(RecipeListUIState())
    val recipeListState: LiveData<RecipeListUIState>
        get() = privateRecipeListState

    fun loadRecipe(id: Int) {

        recipeRepository.getRecipesByCategoryId(id) { callback ->
            privateRecipeListState.postValue(
                (recipeListState.value ?: RecipeListUIState()).copy(
                    recipeList = callback
                )
            )
        }
    }
}