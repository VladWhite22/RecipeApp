package com.example.recipeapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.recipeapp.databinding.ActivityMainBinding
import androidx.navigation.findNavController
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.Collections
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val threadPool = Executors.newFixedThreadPool(10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val thread = Thread {
            try {
                var categories: List<Category> = emptyList()
                var recipeList: List<Recipe> = emptyList()

                categoryRequest { result -> categories = result }

                val categoryId = categories.map { it.id }
                recipeListRequest(categoryId) { result -> recipeList = result }

                Log.d("!!", "categories: $categories")
                Log.d("!!", "recipeList: $recipeList")
                for (recipe in recipeList)
                {
                    Log.d("!!", "recipeList${recipe.id}: ${recipe.title}")
                }

            } catch (e: Exception) {
                Log.d("Server Error", "something goes wrong")
            }
        }
        thread.start()

        binding.btmCategoryButton.setOnClickListener {
            findNavController(R.id.mainContainer).navigate(R.id.categoriesListFragment)
        }

        binding.btmFavoritesButton.setOnClickListener {
            findNavController(R.id.mainContainer).navigate(R.id.favoritesFragment)
        }
    }

    fun categoryRequest(callback: (List<Category>) -> Unit) {
        var categories: List<Category>? = null
        val client: OkHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
            .url("https://recipes.androidsprint.ru/api/category")
            .build()

        client.newCall(request).execute().use { response ->
            val httpCategoryResponce = response.body?.string()
            Log.i("!!", "HttpCategoryResponce:${httpCategoryResponce}")
            if (httpCategoryResponce != null) {
                categories = Json.decodeFromString<List<Category>>(httpCategoryResponce)
                Log.d("!!", "Thread.currentThread: ${Thread.currentThread().name}")
                callback(categories)
            }
        }
    }

    fun recipeListRequest(listInt: List<Int>, callback: (List<Recipe>) -> Unit) {
        val recipeList = Collections.synchronizedList(mutableListOf<Recipe>())
        var counter = CountDownLatch(listInt.size)

        listInt.forEach { listInt ->
            threadPool.submit {
                val client: OkHttpClient = OkHttpClient()
                val request: Request = Request.Builder()
                    .url("https://recipes.androidsprint.ru/api/category/$listInt/recipes")
                    .build()
                client.newCall(request).execute().use { responce ->
                    Log.d("!!", "Thread.currentThread: ${Thread.currentThread().name}")
                    val httpRecipeList = responce.body?.string()
                    if (httpRecipeList != null) {
                        val recipes = Json.decodeFromString<List<Recipe>>(httpRecipeList)
                        recipeList.addAll(recipes)
                        counter.countDown()
                    }
                }
            }

        }
        counter.await()
        callback(recipeList)
    }

}





