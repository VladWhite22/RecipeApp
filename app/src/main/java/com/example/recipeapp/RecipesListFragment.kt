package com.example.recipeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipeapp.databinding.FragmentRecipesListBinding


class RecipesListFragment:Fragment(R.layout.fragment_recipes_list) {
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
        savedInstanceState: Bundle?): View {
        _binding = FragmentRecipesListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        argCategoryId = requireArguments().getInt("ARG_CATEGORY_ID")
        argCategoryName = requireArguments().getString("ARG_CATEGORY_IMAGE_URL")
        argCategoryImageUrl = requireArguments().getString("ARG_CATEGORY_IMAGE_URL")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}