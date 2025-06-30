package com.example.recipeapp.data

import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.RequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository {

    suspend fun getCategories(): RequestResult<List<Category>> = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitClient.apiService.getCategories().execute()
            val categories = if (response.isSuccessful) {
                response.body()?.toList() ?: emptyList()
            } else {
                emptyList()
            }
            RequestResult.Success(categories)
        } catch (e: Exception) {
            RequestResult.Error(e)
        }
    }

    suspend fun getRecipesByCategoryId(categoryId: Int): RequestResult<List<Recipe>> =
        withContext(Dispatchers.IO) {
            try {
                val response =
                    RetrofitClient.apiService.getRecipesByCategoryId(categoryId).execute()
                val result = if (response.isSuccessful) {
                    response.body()?.toList() ?: emptyList()
                } else {
                    emptyList()
                }
                RequestResult.Success(result)
            } catch (e: Exception) {
                RequestResult.Error(e)
            }
        }

    suspend fun getRecipeById(id: Int): RequestResult<Recipe> = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitClient.apiService.getRecipesById(id).execute()
            val recipe = response.body()
            if (recipe != null) {
                RequestResult.Success(recipe)
            } else {
                RequestResult.Error(Exception("Recipe not found (null response)"))
            }
        } catch (e: Exception) {
            RequestResult.Error(e)
        }
    }

    suspend fun getRecipesByCategoryIds(setInt: Set<Int>): RequestResult<List<Recipe>> =
        withContext(
            Dispatchers.IO
        ) {
            try {
                val response =
                    RetrofitClient.apiService.getRecipesByIds(setInt.joinToString(","))
                        .execute()
                val list = response.body()
                if (list != null) {
                    RequestResult.Success(list)
                } else {
                    RequestResult.Error(Exception("Recipe not found (null response)"))
                }
            } catch (e: Exception) {
                RequestResult.Error(e)
            }
        }
}




