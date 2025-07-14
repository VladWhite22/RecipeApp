package com.example.recipeapp.ui.recipes.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {


    private  val viewModel: FavoritesViewModel  by viewModels()
    val adapter = FavoriteListAdapter(emptyList<Recipe>())
    private var recipeList: List<Recipe> = emptyList()
    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must be not null")



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initUI()
    }

    fun openRecipesByRecipesId(recipeId: Int) {

        val direction: NavDirections =
            FavoritesFragmentDirections.actionFavoritesFragmentToRecipeFragment2(
                recipeId = recipeId,
            )
        findNavController().navigate(direction)
    }

    fun initRecycler() {
        binding.rvFavorites.layoutManager =
            GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
        binding.rvFavorites.adapter = adapter
        adapter.setOnItemClickListener(object :
            FavoriteListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByRecipesId(categoryId)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        viewModel.loadFavorites()
        viewModel.favoriteList.observe(viewLifecycleOwner) { state ->
            state?.let { uiState ->
                recipeList = uiState.favoriteList!!
                adapter.newData(recipeList)
                updateVisibility(recipeList.isNotEmpty())
            }
        }
    }

    private fun updateVisibility(hasRecipes: Boolean) {
        binding.rvFavorites.visibility = if (hasRecipes) View.VISIBLE else View.GONE
        binding.tvFavoriteHided.visibility = if (hasRecipes) View.GONE else View.VISIBLE
    }
}