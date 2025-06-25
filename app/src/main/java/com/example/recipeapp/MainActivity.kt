package com.example.recipeapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.recipeapp.databinding.ActivityMainBinding
import androidx.navigation.findNavController
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.serialization.json.Json
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val threadPool = Executors.newFixedThreadPool(10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainThread = Thread.currentThread().name
        Log.i("!!", "currentThread:${mainThread}")
        val thread = Thread {
            try {
                val url = URL("https://recipes.androidsprint.ru/api/category")
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connect()
                val listCategory =
                    connection.inputStream.bufferedReader().readText().substringAfter("Body").trim()
                val category = Json.decodeFromString<List<Category>>(listCategory)
                val currentThread = Thread.currentThread().name
                Log.i("!!", "currentThread:${currentThread}")
                Log.i("!!", "Ready to use:${category}")

                val categoryId = category.map { it.id }
                Log.i("!!", "categoryId:${categoryId}")
                val recipeList: MutableList<Recipe> = mutableListOf()
                var counter = CountDownLatch(categoryId.size)
                categoryId.forEach { categoryId ->
                    threadPool.submit {
                        val mainThread = Thread.currentThread().name
                        Log.i("!!", "currentThread:${mainThread}")

                        val idURL =
                            URL("https://recipes.androidsprint.ru/api/category/$categoryId/recipes")
                        val connected = idURL.openConnection() as HttpURLConnection
                        connected.connect()
                        connected.connectTimeout = 5000
                        connected.readTimeout = 5000
                        val serverAnswer = connected.inputStream.bufferedReader().readText()
                        Log.i("!!", "serverAnswer:${serverAnswer}")
                        var newRecipes = Json.decodeFromString<List<Recipe>>(serverAnswer)
                        recipeList.addAll(newRecipes)
                        counter.countDown()
                    }
                }
                counter.await()
                Log.i("!!", "конечный:${recipeList}")
                for (recipe in recipeList) {
                    Log.i("!!", "ID: ${recipe.id}, Title: ${recipe.title}")
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
}
