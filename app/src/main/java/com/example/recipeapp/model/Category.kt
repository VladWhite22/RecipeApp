package com.example.recipeapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
@Parcelize
@Serializable
@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
) : Parcelable