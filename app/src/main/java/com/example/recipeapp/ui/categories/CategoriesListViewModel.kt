package com.example.recipeapp.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Category

class CategoriesListViewModel(application: Application) :
    AndroidViewModel(application) {

    data class CategoriesUIState(
        val category: List<Category> = listOf()
    )

    private val recipeRepository = RecipeRepository()

    private val privateCategoryState = MutableLiveData(CategoriesUIState())
    val categoryState: LiveData<CategoriesUIState>
        get() = privateCategoryState

    fun loadCategory() {
        recipeRepository.getCategories { categories ->
            privateCategoryState.postValue(
                 (categoryState.value ?: CategoriesUIState()).copy(
                    category = categories
                )
            )
        }
    }

    fun returnCategory(): List<Category> {
        return categoryState.value?.category ?: emptyList()
    }
}


