package com.example.recipeapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.recipeapp.databinding.ActivityMainBinding
import androidx.fragment.app.add
import androidx.fragment.app.commit


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must be not null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<CategoriesListFragment>(R.id.mainContainer)
        }
    }
}
