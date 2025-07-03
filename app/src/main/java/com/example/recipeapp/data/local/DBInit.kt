package com.example.recipeapp.data.local

import AppDatabase
import android.app.Application
import androidx.room.Room

class DBInit : Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "recipe-database"
        ).build()
    }
}