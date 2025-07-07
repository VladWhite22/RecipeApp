package com.example.recipeapp.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.recipeapp.data.local.DB
import com.example.recipeapp.data.local.favorite.Favorite
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.RequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository(application: Application) : AndroidViewModel(application) {

    private val dbCategory = (application as DB).categoryDB
    private val dbRecipe = (application as DB).recipeDb
    private val dbFavorite = (application as DB).favoriteDb

    suspend fun getCategories(): RequestResult<List<Category>> = withContext(Dispatchers.IO) {
        try {
            val categories = RetrofitClient.apiService.getCategories()
            dbCategory.categoryDao().insertCategory(categories)
            RequestResult.Success(categories)
        } catch (e: Exception) {
            Log.d("!!", "getCategories")
            RequestResult.Error(e)
        }
    }

    suspend fun getRecipesByCategoryId(categoryId: Int): RequestResult<List<Recipe>> =
        withContext(Dispatchers.IO) {
            try {
                val response =
                    RetrofitClient.apiService.getRecipesByCategoryId(categoryId)
                val result = response
                dbRecipe.recipesDao().insertRecipe(response)
                RequestResult.Success(result)
            } catch (e: Exception) {
                Log.d("!!", "getRecipesByCategoryId")
                RequestResult.Error(e)
            }
        }

    suspend fun getRecipeById(id: Int): RequestResult<Recipe> = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitClient.apiService.getRecipeById(id)
            val recipe = response
            RequestResult.Success(recipe)

        } catch (e: Exception) {
            Log.d("!!", "getRecipesByCategoryId")
            RequestResult.Error(e)
        }
    }

    suspend fun getRecipesByCategoryIds(setInt: Set<Int>): RequestResult<List<Recipe>> =
        withContext(Dispatchers.IO) {
            try {
                val idsString = setInt.joinToString(",")
                val recipes = RetrofitClient.apiService.getRecipesByIds(idsString)
                RequestResult.Success(recipes)
            } catch (e: Exception) {
                Log.d("!!", "getRecipesByCategoryId")
                RequestResult.Error(e)
            }
        }

     suspend fun getFavorites(): List<Int> {
        return dbFavorite.favoriteDao().getNumbers()
    }

     suspend fun saveFavorites(favoriteEntity: Favorite) {
        dbFavorite.favoriteDao().saveNumbers(favoriteEntity)
    }
}






