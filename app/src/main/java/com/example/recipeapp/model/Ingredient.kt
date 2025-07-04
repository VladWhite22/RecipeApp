package com.example.aseducationalproject.Domain

import android.os.Parcelable
import androidx.room.TypeConverter
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Serializable
@Parcelize
data class Ingredient(
    val quantity: String,
    val unitOfMeasure: String,
    val description: String,
) : Parcelable

class Converters {
    @TypeConverter
    fun ingredientsToJson(value: List<Ingredient>): String =
        Json.encodeToString(value)

    @TypeConverter
    fun jsonToIngredients(value: String): List<Ingredient> =
        Json.decodeFromString(value)

    @TypeConverter
    fun stringsToJson(value: List<String>): String =
        Json.encodeToString(value)

    @TypeConverter
    fun jsonToStrings(value: String): List<String> =
        Json.decodeFromString(value)
}