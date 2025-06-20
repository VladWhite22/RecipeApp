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
import kotlin.getValue

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    val viewModel: FavoritesViewModel by viewModels()
    private lateinit var recipeList: List<Recipe>
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
    }

    fun openRecipesByRecipesId(recipeId: Int) {

        val direction: NavDirections =
            FavoritesFragmentDirections.actionFavoritesFragmentToRecipeFragment2(
                recipeId = recipeId,
            )
        findNavController().navigate(direction)
    }

    private fun initRecycler() {
        viewModel.loadFavorites()
        viewModel.favoriteList.observe(viewLifecycleOwner) { state ->
            state?.let { uiState ->
                recipeList = uiState.favoriteList
                if (recipeList.isEmpty()) {
                    binding.rvFavorites.visibility = View.GONE
                    binding.tvFavoriteHided.visibility = View.VISIBLE
                } else {
                    val adapter = FavoriteListAdapter(recipeList)
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
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}