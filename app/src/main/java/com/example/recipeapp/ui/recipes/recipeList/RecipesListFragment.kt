package com.example.recipeapp.ui.recipes.recipeList


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipesListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesListFragment : Fragment(R.layout.fragment_recipes_list) {

    private val viewModel: RecipesListVIewModel by viewModels()
    private var adapter = RecipesListAdapter(emptyList())
    private var _binding: FragmentRecipesListBinding? = null
    private val args: RecipesListFragmentArgs by navArgs()
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must be not null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadRecipe(args.category.id )
        initUI()
    }


    fun initUI() {
        viewModel.recipeListState.observe(viewLifecycleOwner) { state ->
            state.recipeList.let { recipes ->
                if (binding.rvRecipes.adapter == null) {
                    adapter = RecipesListAdapter(recipes)
                    binding.rvRecipes.adapter = adapter
                    adapter.setOnItemClickListener { recipeId ->
                        openRecipesByRecipeId(recipeId)
                    }
                } else {
                    adapter.newData(recipes)
                }
                binding.tvBurgersRecipes.text = args.category.title
                args.category.imageUrl.let { imageUrl ->
                    try {
                        val context = requireContext()
                        val inputStream = context.assets.open(imageUrl)
                        val drawable = Drawable.createFromStream(inputStream, null)
                        binding.ivFragmentRecipeList.setImageDrawable(drawable)
                        inputStream.close()
                    } catch (_: Exception) {
                        Log.e("!!", "Failed to load image from assets: $imageUrl")

                    }
                    binding.rvRecipes.adapter = adapter
                    adapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
                        override fun onItemClick(recipeId: Int) {
                            openRecipesByRecipeId(recipeId)
                        }
                    })
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun openRecipesByRecipeId(recipeId: Int) {

        val direction = RecipesListFragmentDirections.actionRecipesListFragmentToRecipeFragment(
            recipeId = recipeId
        )
        findNavController().navigate(direction)
    }
}


