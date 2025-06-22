package com.example.recipeapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.categoresList.CategoriesListAdapter
import com.example.recipeapp.databinding.FragmentListCategoriesBinding

class CategoriesListFragment : Fragment(R.layout.fragment_list_categories) {
    private var adapter = CategoriesListAdapter(emptyList())
    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must be not null")

    private val viewModel: CategoriesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadCategory()
        initUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun openRecipesByCategoryId(categoryId: Int) {
        val idCategory = viewModel.returnCategory().find { it.id == categoryId }
            ?: throw IllegalArgumentException("Category with id $categoryId not found")
        val direction =
            CategoriesListFragmentDirections.actionCategoriesListFragmentToRecipesListFragment(
                category = idCategory,
            )
        findNavController().navigate(direction)
    }

    fun initUi() {

        val category = viewModel.returnCategory()
        adapter = CategoriesListAdapter(category)
        viewModel.categoryState.observe(viewLifecycleOwner) { state ->
            adapter.newData(state.category)
            binding.rvCategories.layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            binding.rvCategories.adapter = adapter
            adapter.setOnItemClickListener(object : CategoriesListAdapter.OnItemClickListener {
                override fun onItemClick(categoryId: Int) {
                    openRecipesByCategoryId(categoryId)
                }
            })
        }

    }
}



