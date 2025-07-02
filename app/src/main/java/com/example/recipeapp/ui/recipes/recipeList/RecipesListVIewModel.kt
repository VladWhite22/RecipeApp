package com.example.recipeapp.ui.recipes.recipeList

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

class RecipesListVIewModel(application: Application) : AndroidViewModel(application) {
    data class RecipeListUIState(
        val recipeList: List<Recipe> = listOf(),
    )

    private val recipeRepository = RecipeRepository()

    private val privateRecipeListState = MutableLiveData(RecipeListUIState())
    val recipeListState: LiveData<RecipeListUIState>
        get() = privateRecipeListState

    fun loadRecipe(id: Int) {
        viewModelScope.launch {
            val result = recipeRepository.getRecipesByCategoryId(id)

            when (result) {
                is RequestResult.Success<List<Recipe>> -> {
                    privateRecipeListState.value = RecipeListUIState(
                        recipeList = result.data
                    )
                }
                is RequestResult.Error -> Log.d("!!", "loadRecipe $result")
            }
        }
    }
}