package com.example.recipeapp
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipeapp.databinding.FragmentListCategoriesBinding

class CategoriesListFragment : Fragment(R.layout.fragment_list_categories) {

    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must be not null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentListCategoriesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun openRecipesByCategoryId(categoryId:Int) {
        val category: Category = STUB.getCategories()[categoryId]
        val categoryName = category.title
            val categoryImageUrl = category.imageUrl
        val bundle = bundleOf( "ARG_CATEGORY_ID" to categoryId,
            "ARG_CATEGORY_NAME" to categoryName,
            "ARG_CATEGORY_IMAGE_URL" to categoryImageUrl)
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipesListFragment>(R.id.mainContainer, args = bundle)
            addToBackStack(null)
        }
    }

    private fun initRecycler() {
        val adapter = CategoriesListAdapter(STUB.getCategories())
        binding.rvCategories.layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
        binding.rvCategories.adapter = adapter
        adapter.setOnItemClickListener (object : CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
    }
}