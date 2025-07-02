package com.example.recipeapp.data

import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {
    @GET("category")
    suspend fun getCategories(): List<Category>

    @GET("category/{categoryId}/recipes")
    suspend fun getRecipesByCategoryId(@Path("categoryId") categoryId: Int): List<Recipe>

    @GET("recipe/{recipeId}")
    suspend fun getRecipeById(@Path("recipeId") recipeId: Int): Recipe

    @GET("recipes")
    suspend fun getRecipesByIds(@Query("ids") ids: String): List<Recipe>
}