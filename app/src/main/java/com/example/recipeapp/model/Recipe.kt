package com.example.recipeapp.model

import android.os.Parcelable
import com.example.aseducationalproject.Domain.Ingredient
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Recipe(
    val id: Int = 0,
    val title: String = "",
    val ingredients: List<Ingredient> = emptyList(),
    val method: List<String> = emptyList(),
    val imageUrl: String = ""
): Parcelable