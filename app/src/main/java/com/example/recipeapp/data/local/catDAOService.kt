package com.example.recipeapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.data.local.favorite.Favorite
import com.example.recipeapp.data.local.favorite.FavoriteConverter
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe

@Dao
interface FoodCategoryDao  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categories: List<Category>)

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<Category>

    @Query("DELETE FROM categories")
    suspend fun clearAll()

    @Query("SELECT numbers FROM saved_numbers WHERE id = 1")
    suspend fun getNumbersString(): String?

    suspend fun getNumbers(): List<Int> {
        return getNumbersString()?.let {
            FavoriteConverter().toList(it)
        } ?: emptyList()
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNumbers(holder: Favorite)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertRecipe(recipes: List<Recipe>)

    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<Recipe>

    @Query("DELETE FROM recipes")
    suspend fun clearAllRecipes()

    @Query(
        """
    SELECT * FROM recipes 
    WHERE id / 100 = :categoryId
"""
    )
    suspend fun getRecipesByCategoryDivision(categoryId: Int): List<Recipe>

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    suspend fun getRecipeById(recipeId: Int): Recipe?
}