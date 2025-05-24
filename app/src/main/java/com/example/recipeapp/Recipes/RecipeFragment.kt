package com.example.recipeapp.Recipes

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.Categories.CategoriesListFragment.Companion.ARG_RECIPE
import com.example.recipeapp.DataTest.STUB
import com.example.recipeapp.Domain.Recipe
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private lateinit var ingredientsAdapter: IngredientsAdapter
    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must be not null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipe: Recipe? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            requireArguments().getParcelable(ARG_RECIPE)
        }
        initUI(recipe)
        initRecycler(recipe)
    }

    fun initUI(recipe: Recipe?) {
        val drawable = try {
            Drawable.createFromStream(
                recipe?.imageUrl?.let { this.context?.assets?.open(it) }, null
            )
        } catch (e: Exception) {
            Log.d("Not found", "Image not found: ${recipe?.imageUrl}")
            null
        }
        if (recipe?.title == null) {
            recipe?.title == "я потерял ${recipe?.title}"
        }
        if (recipe?.title != null) {
            binding.tvRecipe.text = recipe.title
        }
        binding.ibFavorite.setOnClickListener {
            try {
                binding.ibFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_heart
                    )
                )
            } catch (e: Exception) {
                Log.d("Not found", "Image not found: ${"ibFavorite"}")
                null
            }
        }
        binding.ivRecipe.setImageDrawable(drawable)
        binding.sbFragmentRecipe.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvFragmentRecipeNumber.text = progress.toString()
                ingredientsAdapter.updateIngredients(progress.toDouble())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

    }

    private fun initRecycler(recipeId: Recipe?) {
        val id: Int = recipeId?.id ?: 1
        ingredientsAdapter = IngredientsAdapter(STUB.getRecipeById(id)?.ingredients ?: emptyList())
        binding.rvIngredients.adapter = ingredientsAdapter
        val methodsAdapter = MethodsAdapther(STUB.getRecipeById(id)?.method ?: emptyList())
        binding.rvMethod.adapter = methodsAdapter
        val divider = MaterialDividerItemDecoration(
            this.requireContext(), LinearLayoutManager.VERTICAL
        ).apply {
            isLastItemDecorated = false
            dividerInsetStart = 24
            dividerInsetEnd = 24
            dividerColor = ContextCompat.getColor(
                requireContext(), R.color.recipe_fragment_color
            )
            dividerThickness = 6
        }
        binding.rvIngredients.addItemDecoration(divider)
        binding.rvMethod.addItemDecoration(divider)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


