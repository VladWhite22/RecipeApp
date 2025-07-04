package com.example.recipeapp.ui.recipes.recipeList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.local.DB
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.RequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipesListVIewModel(application: Application) : AndroidViewModel(application) {
    data class RecipeListUIState(
        val recipeList: List<Recipe> = listOf(),
    )

    private val recipeRepository = RecipeRepository()

    private val privateRecipeListState = MutableLiveData(RecipeListUIState())
    val recipeListState: LiveData<RecipeListUIState>
        get() = privateRecipeListState

    fun loadRecipe(categoryId: Int) {
        viewModelScope.launch {
            val result = recipeRepository.getRecipesByCategoryId(categoryId)

            when (result) {
                is RequestResult.Success<List<Recipe>> -> {
                    withContext(Dispatchers.IO) {
                        val db = (application as DB).recipeDb
                        db.recipesDao().insertRecipe(result.data)
                    }
                    privateRecipeListState.value = RecipeListUIState(
                        recipeList = result.data
                    )
                }

                is RequestResult.Error -> {
                    Log.d("!!", "loadRecipe $result")
                    val recipeFromDb = withContext(Dispatchers.IO) {
                        (application as DB).recipeDb.recipesDao().getRecipesByCategoryDivision(categoryId)
                    }
                    privateRecipeListState.value = RecipeListUIState(
                        recipeList = recipeFromDb
                    )

                }
            }
        }
    }
}