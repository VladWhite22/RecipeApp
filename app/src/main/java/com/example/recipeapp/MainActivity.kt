package com.example.recipeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recipeapp.databinding.ActivityMainBinding
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btmCategoryButton.setOnClickListener {
            findNavController(R.id.mainContainer).navigate(R.id.categoriesListFragment)
        }

        binding.btmFavoritesButton.setOnClickListener {
            findNavController(R.id.mainContainer).navigate(R.id.favoritesFragment)
        }
    }
}
