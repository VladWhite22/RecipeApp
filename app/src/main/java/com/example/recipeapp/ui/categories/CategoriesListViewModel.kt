package com.example.recipeapp.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Category

class CategoriesListViewModel(application: Application) :
    AndroidViewModel(application) {

    data class CategoriesUIState(
        val category: List<Category> = listOf()
    )

    private val privateCategoryState = MutableLiveData(CategoriesUIState())
    val categoryState: LiveData<CategoriesUIState>
        get() = privateCategoryState

    fun loadCategory() {
        val categories = STUB.getCategories()
        val currentState = categoryState.value ?: CategoriesUIState()
        val updatedState = currentState.copy(category = categories)
        privateCategoryState.value = updatedState
    }

    fun returnCategory(): List<Category> {
        return categoryState.value?.category ?: emptyList()
    }
}


