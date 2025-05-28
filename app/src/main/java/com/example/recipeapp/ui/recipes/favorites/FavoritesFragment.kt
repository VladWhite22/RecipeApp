package com.example.recipeapp.ui.recipes.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipeapp.Const
import com.example.recipeapp.FAVORITE_SET_KEY
import com.example.recipeapp.R
import com.example.recipeapp.SP_KEY
import com.example.recipeapp.data.STUB
import com.example.recipeapp.data.STUB.getRecipeById
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.recipes.recipe.RecipeFragment
import com.example.recipeapp.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private lateinit var favoritesList: Set<Int>
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
        favoritesList = getFavorites().mapNotNull { it.toIntOrNull() }.toSet()
        recipeList = STUB.getRecipesByIds(favoritesList)
        initRecycler()
    }

    fun openRecipesByRecipesId(recipeId: Int) {
        val recipe = getRecipeById(recipeId)
        val bundle = bundleOf(Const.ARG_RECIPE to recipe )

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            addToBackStack(null)

        }

}

    private fun initRecycler() {
        if (recipeList.isEmpty()) {
            binding.rvFavorites.visibility = View.GONE
            binding.tvFavoriteHided.visibility = View.VISIBLE
        } else {
            val adapter = FavoriteListAdapter(recipeList)
            binding.rvFavorites.layoutManager =
                GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            binding.rvFavorites.adapter = adapter
            adapter.setOnItemClickListener(object : FavoriteListAdapter.OnItemClickListener {
                override fun onItemClick(categoryId: Int) {
                    openRecipesByRecipesId(categoryId)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getFavorites(): HashSet<String> {
        val sharedPrefs = requireContext().getSharedPreferences(SP_KEY, Context.MODE_PRIVATE)
        return HashSet<String>(sharedPrefs.getStringSet(FAVORITE_SET_KEY, emptySet()))
    }

}