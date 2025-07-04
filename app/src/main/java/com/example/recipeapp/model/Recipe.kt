package com.example.recipeapp.model

import android.os.Parcelable
import com.example.aseducationalproject.Domain.Ingredient
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Recipe(
    val id: Int,
    val title: String,
    val ingredients: List<Ingredient>,
    val method: List<String>,
    val imageUrl: String
): Parcelable