package com.example.recipeapp.data

import com.example.recipeapp.data.local.AppDatabase
import android.util.Log
import com.example.recipeapp.data.local.favorite.Favorite
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.RequestResult
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository @Inject constructor(
    private val appDB: AppDatabase,
) {


    suspend fun getCategories(): RequestResult<List<Category>> = withContext(Dispatchers.IO) {
        try {
            val categories = RetrofitClient.apiService.getCategories()
            appDB.FoodCategoryDao().insertCategory(categories)
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
                appDB.FoodCategoryDao().insertRecipe(response)
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
        return appDB.FoodCategoryDao().getNumbers()
    }

    suspend fun saveFavorites(favoriteEntity: Favorite) {
        appDB.FoodCategoryDao().saveNumbers(favoriteEntity)
    }

    suspend fun getAllFromCategoryDB(): List<Category> {
        return appDB.FoodCategoryDao().getAllCategories()
    }

    suspend fun recipeFromDBByRecipeId(id: Int): Recipe? {
        return appDB.FoodCategoryDao().getRecipeById(id)
    }

    suspend fun getRecipesByCategoryDivision(categoryId: Int): List<Recipe> {
        return appDB.FoodCategoryDao().getRecipesByCategoryDivision(categoryId )
    }


}






