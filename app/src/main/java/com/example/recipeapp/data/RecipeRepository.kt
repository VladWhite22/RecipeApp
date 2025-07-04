package com.example.recipeapp.data

import android.util.Log
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.RequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository {

    suspend fun getCategories(): RequestResult<List<Category>> = withContext(Dispatchers.IO) {
        try {
            val categories = RetrofitClient.apiService.getCategories()
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
}





