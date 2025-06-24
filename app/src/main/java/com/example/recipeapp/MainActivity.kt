package com.example.recipeapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.recipeapp.databinding.ActivityMainBinding
import androidx.navigation.findNavController
import com.example.recipeapp.model.Category
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentThread = Thread.currentThread().name
        Log.i("!!", "currentThread:${currentThread}")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btmCategoryButton.setOnClickListener {
            findNavController(R.id.mainContainer).navigate(R.id.categoriesListFragment)
        }

        binding.btmFavoritesButton.setOnClickListener {
            findNavController(R.id.mainContainer).navigate(R.id.favoritesFragment)
        }


        val thread = Thread {
            val url = URL("https://recipes.androidsprint.ru/api/category")
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.connect()
            val listCategory =
                connection.inputStream.bufferedReader().readText().substringAfter("Body").trim()
            var category = Json.decodeFromString<List<Category>>(listCategory)
            val currentThread = Thread.currentThread().name
            Log.i("!!", "currentThread:${currentThread}")
            Log.i("!!", "Ready to use:${category}")

        }
        thread.start()

    }


}
