package com.example.recipeapp.di

import android.app.Application
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.RecipesApplication

class AppContainer(application: Application) {


    private val dbCategory = (application as RecipesApplication).categoryDB
    private val dbFavorite = (application as RecipesApplication).favoriteDb
    private val dbRecipe = (application as RecipesApplication).recipeDb

    private val repository = RecipeRepository(dbCategory, dbFavorite, dbRecipe)

    val categoryViewModelFactory = CategoryListViewModelFactory(repository)
    val favoritesViewModelFactory = FavoritesViewModelFactory(repository)
    val recipesViewModelFactory = RecipesViewModelFactory(repository)
    val recipeViewModelFactory = RecipeViewModelFactory(repository)
}
