package com.example.recipeapp.di

import android.content.Context
import androidx.room.Room
import com.example.recipeapp.data.local.AppDatabase
import com.example.recipeapp.data.local.CategoryDao
import com.example.recipeapp.data.local.FavoriteBase
import com.example.recipeapp.data.local.FavoriteDao
import com.example.recipeapp.data.local.RecipeDB
import com.example.recipeapp.data.local.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RecipeModule {

    @Provides
    @Singleton
    fun provideCategoryDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "categories-database"
            ).fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipeDatabase(@ApplicationContext context: Context): RecipeDB {
        return Room.databaseBuilder(
                context,
                RecipeDB::class.java,
                "recipe-database"
            ).fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideFavoriteDatabase(@ApplicationContext context: Context): FavoriteBase {
        return Room.databaseBuilder(
                context,
                FavoriteBase::class.java,
                "favorite-database"
            ).fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    fun provideRecipeDao(database: RecipeDB): RecipeDao {
        return database.recipesDao()
    }

    @Provides
    fun provideFavoriteDao(database: FavoriteBase): FavoriteDao {
        return database.favoriteDao()
    }


}
