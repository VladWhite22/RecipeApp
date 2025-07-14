package com.example.recipeapp.ui.recipes.recipeList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.RequestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesListVIewModel @Inject constructor(val appContext: RecipeRepository) : ViewModel() {
    data class RecipeListUIState(
        val recipeList: List<Recipe> = listOf(),
    )


    private val privateRecipeListState = MutableLiveData(RecipeListUIState())
    val recipeListState: LiveData<RecipeListUIState>
        get() = privateRecipeListState

    fun loadRecipe(categoryId: Int) {
        viewModelScope.launch {
            val result = appContext.getRecipesByCategoryId(categoryId)

            when (result) {
                is RequestResult.Success<List<Recipe>> -> {
                    privateRecipeListState.value = RecipeListUIState(
                        recipeList = result.data
                    )
                }

                is RequestResult.Error -> {
                    Log.d("!!", "loadRecipe $result")
                    privateRecipeListState.value = RecipeListUIState(
                        recipeList = appContext.getRecipesByCategoryDivision(categoryId)
                    )

                }
            }
        }
    }
}