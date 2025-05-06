package com.example.recipeapp.Domain

import android.os.Parcelable
import com.example.aseducationalproject.Domain.Ingredient
import kotlinx.parcelize.Parcelize

@Parcelize
class Recipe (
    val id: Int,
    val title: String,
    val ingredients: List<Ingredient>,
    val method: List<String>,
    val imageUrl: String,
) : Parcelable