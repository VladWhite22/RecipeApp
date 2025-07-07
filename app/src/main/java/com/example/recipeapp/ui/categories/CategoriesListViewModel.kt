package com.example.recipeapp.ui.categories

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.local.DB
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.RequestResult
import kotlinx.coroutines.launch

class CategoriesListViewModel(private val application: Application) :
    AndroidViewModel(application) {

    data class CategoriesUIState(
        val category: List<Category> = listOf()
    )

    private val recipeRepository = RecipeRepository(application)

    private val privateCategoryState = MutableLiveData(CategoriesUIState())
    val categoryState: LiveData<CategoriesUIState>
        get() = privateCategoryState

    fun loadCategory() {

        viewModelScope.launch {
            val result = recipeRepository.getCategories()
            when (result) {
                is RequestResult.Success<List<Category>> -> {
                    privateCategoryState.value = CategoriesUIState(
                        category = result.data,
                    )
                }
                is RequestResult.Error -> {
                    Log.d("!!", "loadCategory $result")
                    val categoriesFromDb = (application as DB).categoryDB.categoryDao().getAllCategories()
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





