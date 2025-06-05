package com.example.recipeapp.ui.recipes.recipe

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.Const.ARG_RECIPE
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    private val viewModel: RecipeViewModel by viewModels()
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
        viewModel.loadRecipe(recipe?.id ?: -1)
        initUI()
    }

    fun initUI() {
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
                binding.ivRecipe.setImageDrawable(viewModel.recipeState.value?.recipeImage)

                state.recipe?.let {
                    ingredientsAdapter.updateIngredients(state.portionsCount.toDouble())
                    ingredientsAdapter.newData(it.ingredients)
                    methodsAdapter.newData(it.method)
                    binding.tvFragmentRecipeNumber.text = state.portionsCount.toString()
                }

                binding.sbFragmentRecipe.setOnSeekBarChangeListener(object :
                    SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        viewModel.updateStateOfSeekbar(progress)
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    }
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


