package com.example.recipeapp.ui.recipes.recipeList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Recipe

class RecipesListVIewModel( application: Application) : AndroidViewModel(application) {
    data class RecipeListUIState(
        val recipeList: List<Recipe> = listOf(),
        val argCategoryId: Int? = null,
        val argCategoryName: String? = null,
        val argCategoryImageUrl: String? = null,
    )

    private val privateRecipeListState = MutableLiveData(RecipeListUIState())
    val recipeListState: LiveData<RecipeListUIState>
        get() = privateRecipeListState

    fun loadRecipe(id: Int) {
        val recipeList = STUB.getRecipesByCategoryId(id)
        privateRecipeListState.value = RecipeListUIState(
            recipeList = recipeList,
        )
    }

    fun returnList(): List<Recipe> {
        return recipeListState.value?.recipeList ?: listOf<Recipe>()
    }
}