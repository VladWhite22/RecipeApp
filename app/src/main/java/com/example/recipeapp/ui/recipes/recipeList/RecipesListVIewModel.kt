package com.example.recipeapp.ui.recipes.recipeList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.local.DB
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.RequestResult
import kotlinx.coroutines.launch

class RecipesListVIewModel(val appContext: Application) : AndroidViewModel(appContext) {
    data class RecipeListUIState(
        val recipeList: List<Recipe> = listOf(),
    )

    private val recipeRepository = RecipeRepository(appContext)

    private val privateRecipeListState = MutableLiveData(RecipeListUIState())
    val recipeListState: LiveData<RecipeListUIState>
        get() = privateRecipeListState

    fun loadRecipe(categoryId: Int) {
        viewModelScope.launch {
            val result = recipeRepository.getRecipesByCategoryId(categoryId)

            when (result) {
                is RequestResult.Success<List<Recipe>> -> {
                    privateRecipeListState.value = RecipeListUIState(
                        recipeList = result.data
                    )
                }

                is RequestResult.Error -> {
                    Log.d("!!", "loadRecipe $result")
                    val recipeFromDb =
                        (appContext as DB).recipeDb.recipesDao()
                            .getRecipesByCategoryDivision(categoryId)
                    privateRecipeListState.value = RecipeListUIState(
                        recipeList = recipeFromDb
                    )

                }
            }
        }
    }
}