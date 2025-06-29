package com.example.recipeapp.data;

import com.example.recipeapp.model.Category;
import com.example.recipeapp.model.Recipe
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {
    @GET("category")
    fun getCategories(): Call <List<Category>>

    @GET("category/{categoryId}/recipes")
    fun getRecipesByCategoryId(@Path("categoryId") categoryId: Int): Call<kotlin.collections.List<Recipe>>

    @GET("recipe/{recipeId}")
    fun getRecipesById(@Path("recipeId")recipeId: Int): Call<Recipe>

    @GET("recipes")
    fun getRecipesByIds(@Query("ids") ids: String): Call<kotlin.collections.List<Recipe>>
}


