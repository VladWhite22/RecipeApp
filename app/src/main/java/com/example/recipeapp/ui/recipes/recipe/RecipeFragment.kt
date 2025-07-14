package com.example.recipeapp.ui.recipes.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recipeapp.IMAGE_BASE_URL
import com.example.recipeapp.R
import com.example.recipeapp.RecipesApplication
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.model.RequestResult
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlin.getValue

class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    private lateinit var viewModel: RecipeViewModel
    private val args: RecipeFragmentArgs by navArgs()
    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must be not null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (requireActivity().application as RecipesApplication).appContainer
        viewModel = ViewModelProvider(
            this,
            appContainer.recipeViewModelFactory
        )[RecipeViewModel::class.java]
    }

    class PortionSeekBarListener(
        private val onChangeIngredients: (Int) -> Unit
    ) : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(
            seekBar: SeekBar?,
            progress: Int,
            fromUser: Boolean
        ) {
            onChangeIngredients(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadRecipe(args.recipeId)
        initUI()
    }

    fun initUI() {
        try {
            val ingredientsAdapter = IngredientsAdapter(listOf())
            val methodsAdapter = MethodsAdapther(listOf())
            binding.rvIngredients.adapter = ingredientsAdapter
            binding.rvMethod.adapter = methodsAdapter
            viewModel.recipeState.observe(viewLifecycleOwner) { state ->
                state?.let {
                    if (state.isFavorite == true) {
                        binding.ibFavorite.setImageResource(R.drawable.ic_heart)
                    } else {
                        binding.ibFavorite.setImageResource(R.drawable.ic_heart_empty)
                    }
                    binding.ibFavorite.setOnClickListener {
                        viewModel.onFavoriteClicked()
                    }
                    state.recipe?.imageUrl?.let { imageUrl ->
                        if (imageUrl.isNotBlank() && imageUrl != "null") {
                            val fullImageUrl = "$IMAGE_BASE_URL$imageUrl"
                            Log.d("ImageLoad", "Loading image from: $fullImageUrl")
                            Glide.with(this@RecipeFragment)
                                .load(fullImageUrl)
                                .centerCrop()
                                .placeholder(R.drawable.img_placeholder)
                                .error(R.drawable.img_error)
                                .into(binding.ivRecipe)
                        } else {
                            binding.ivRecipe.setImageResource(R.drawable.img_placeholder)
                        }
                    } ?: run {
                        binding.ivRecipe.setImageResource(R.drawable.img_placeholder)
                    }

                    state.recipe?.let {
                        ingredientsAdapter.updateIngredients(state.portionsCount.toDouble())
                        ingredientsAdapter.newData(it.ingredients)
                        methodsAdapter.newData(it.method)
                        binding.tvFragmentRecipeNumber.text = state.portionsCount.toString()
                    }
                    binding.sbFragmentRecipe.setOnSeekBarChangeListener(PortionSeekBarListener {
                        viewModel.updateStateOfSeekbar(it)
                    })
                }
            }
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
        } catch (e: Exception) {
            Log.d("!!", "recipe fragment initUI error")
            RequestResult.Error(e)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}