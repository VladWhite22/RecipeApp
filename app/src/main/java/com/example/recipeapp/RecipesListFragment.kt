package com.example.recipeapp


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.recipeapp.CategoriesListFragment.Companion.ARG_CATEGORY_IMAGE_URL
import com.example.recipeapp.databinding.FragmentRecipesListBinding
import com.example.recipeapp.STUB


class RecipesListFragment : Fragment(R.layout.fragment_recipes_list) {
    private var argCategoryId: Int? = null
    private var argCategoryName: String? = null
    private var argCategoryImageUrl: String? = null

    private var _binding: FragmentRecipesListBinding? = null
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

        initBundleData()
        initUI()
        initRecycler()
    }
    fun initBundleData(){
        arguments?.let { args ->
            argCategoryId = requireArguments().getInt(CategoriesListFragment.ARG_CATEGORY_ID)
            argCategoryName = requireArguments().getString(CategoriesListFragment.ARG_CATEGORY_NAME)
            argCategoryImageUrl = requireArguments().getString(ARG_CATEGORY_IMAGE_URL)
        }
    }
    fun initUI(){

        // отображение изображения происходит неккоректно переходить на glide?
        // для настройки UI элементов (заголовка, изображения)
        binding.tvBurgersRecipes.text = argCategoryName
        try {
            var imageName = argCategoryImageUrl?.dropLast(4)

            val imageUrl = resources.getIdentifier(imageName, "drawable", requireContext().packageName)
            binding.ivFragmentRecipeList.setImageResource(imageUrl)
            // argCategoryImageUrl not image
        }catch (e: Exception) {
            Log.e("argCategoryImageUrl", "image not found", e)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun openRecipesByRecipeId(recipeId: Int) {
        parentFragmentManager?.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer)
            addToBackStack(null)
        }
    }

    private fun initRecycler() {
        val adapter = RecipesListAdapter(STUB.getRecipesByCategoryId(argCategoryId ?: 0))
        binding.rvRecipes.adapter = adapter
        adapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipesByRecipeId(recipeId)
            }
        })
    }



}


