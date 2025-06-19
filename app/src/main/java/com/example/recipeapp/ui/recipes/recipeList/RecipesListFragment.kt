package com.example.recipeapp.ui.recipes.recipeList


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.Const
import com.example.recipeapp.Const.ARG_RECIPE
import com.example.recipeapp.data.STUB.getRecipeById
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipesListBinding


class RecipesListFragment : Fragment(R.layout.fragment_recipes_list) {
    private var argCategoryId: Int = 0
    private var argCategoryName: String? = null
    private var argCategoryImageUrl: String? = null
    private var adapter = RecipesListAdapter(emptyList())
    private var _binding: FragmentRecipesListBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must be not null")

    private val viewModel: RecipesListVIewModel by viewModels()

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
        initBundleData()
        viewModel.loadRecipe(argCategoryId)
        initUI()
    }

    fun initBundleData() {
        arguments?.let { args ->
            argCategoryId = args.getInt(Const.ARG_CATEGORY_ID)
            argCategoryName = args.getString(Const.ARG_CATEGORY_NAME)
            argCategoryImageUrl = args.getString(Const.ARG_CATEGORY_IMAGE_URL)
        }
    }

    fun initUI() {
        viewModel.recipeListState.observe(viewLifecycleOwner) { state ->
            state.recipeList.let { recipes ->
                adapter = RecipesListAdapter(recipes)
                adapter.newData(recipes)
                binding.tvBurgersRecipes.text = argCategoryName
                argCategoryImageUrl?.let { imageUrl ->
                    try {
                        val context = requireContext()
                        val inputStream = context.assets.open(imageUrl)
                        val drawable = Drawable.createFromStream(inputStream, null)
                        binding.ivFragmentRecipeList.setImageDrawable(drawable)
                        inputStream.close()
                    } catch (e: Exception) {
                        Log.e("argCategoryImageUrl", "Failed to load image from assets: $imageUrl")

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

        val recipe: Recipe? = getRecipeById(recipeId)
        val bundle = bundleOf(ARG_RECIPE to recipe)

        findNavController().navigate(R.id.recipeFragment, args = bundle)
    }
}


