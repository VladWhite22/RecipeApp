package com.example.recipeapp.data.local.recipeList

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.model.Recipe

@Dao
interface RecipeDao {
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