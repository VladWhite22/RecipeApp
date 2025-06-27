package com.example.recipeapp.data

import android.util.Log
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RecipeRepository {

    private val executorService: ExecutorService = Executors.newFixedThreadPool(1)

    fun getCategories(callback: (List<Category>) -> Unit) {
        executorService.execute {
            try {
                val response = RetrofitClient.apiService.getCategories().execute()
                val result = if (response.isSuccessful) {
                    response.body()?.toList() ?: emptyList()
                } else {
                    emptyList()
                }
                callback(result)
                Log.d("!!", "getCategories $result")
            } catch (e: Exception) {
                Log.e("!!", "getCategories Error")

            }
        }
    }

    fun getRecipesByCategoryId(categoryId: Int, callback: (List<Recipe>) -> Unit) {
        executorService.execute {
            try {
                val response =
                    RetrofitClient.apiService.getRecipesByCategoryId(categoryId).execute()
                val result = if (response.isSuccessful) {
                    response.body()?.toList() ?: emptyList()
                } else {
                    emptyList()
                }
                callback(result)
                Log.d("!!", "getRecipesByCategoryId $result")
            } catch (e: Exception) {
                Log.e("!!", "getRecipesByCategoryId Error")
            }
        }
    }

    fun getRecipeById(id: Int, callback: (Recipe?) -> Unit) {
        executorService.execute {
            try {
                val response = RetrofitClient.apiService.getRecipesById(id).execute()
                val result = if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
                callback(result)
                Log.d("!!", "getRecipeById $result")
            } catch (e: Exception) {
                Log.e("!!", "getRecipeById Error")
            }
        }
    }

    fun getRecipesByCategoryIds(setInt: Set<Int>, callback: (List<Recipe>?) -> Unit) {
        executorService.execute {
            try {
                val response =
                    RetrofitClient.apiService.getRecipesByIds(setInt.joinToString(",")).execute()
                val result = if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
                callback(result)
            } catch (e: Exception) {
                Log.e("!!", "getRecipesByCategoryIds Error")
            }
        }
    }

}


