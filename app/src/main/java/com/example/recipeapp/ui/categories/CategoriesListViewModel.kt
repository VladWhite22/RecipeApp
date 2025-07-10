package com.example.recipeapp.ui.categories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.RequestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesListViewModel @Inject constructor(private val application: RecipeRepository): ViewModel() {

    data class CategoriesUIState(
        val category: List<Category> = listOf()
    )

    private val privateCategoryState = MutableLiveData(CategoriesUIState())
    val categoryState: LiveData<CategoriesUIState>
        get() = privateCategoryState

    fun loadCategory() {

        viewModelScope.launch {
            val result = application.getCategories()
            when (result) {
                is RequestResult.Success<List<Category>> -> {
                    privateCategoryState.value = CategoriesUIState(
                        category = result.data,
                    )
                }

                is RequestResult.Error -> {
                    Log.d("!!", "loadCategory $result")
                    val categoriesFromDb = application.getAllFromCategoryDB()
                    privateCategoryState.value = CategoriesUIState(
                        category = categoriesFromDb,
                    )
                }
            }
        }
    }

    fun returnCategory(): List<Category> {
        return categoryState.value?.category ?: emptyList()
    }

}





