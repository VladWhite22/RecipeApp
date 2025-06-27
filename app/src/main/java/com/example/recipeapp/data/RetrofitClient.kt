package com.example.recipeapp.data

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitClient {

    class RawResponseInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            Log.d("!!", ">>> ${request.method} ${request.url}")
            val response = chain.proceed(request)
            val responseBody = response.peekBody(Long.MAX_VALUE)
            Log.d("!!", ">>>${responseBody.string()}")
            return response
        }
    }

    private const val BASE_URL = "https://recipes.androidsprint.ru/api/"
    private val retrofit: Retrofit by lazy {

        val client = OkHttpClient.Builder()
            .addInterceptor(RawResponseInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .build()
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            isLenient = true
            explicitNulls = false
        }
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
    }
    val apiService: RecipeService by lazy {
        retrofit.create(RecipeService::class.java)
    }
}