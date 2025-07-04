package com.example.recipeapp.ui.categories

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.local.DBInit
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.RequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        viewModelScope.launch {
            val result = recipeRepository.getCategories()
            when (result) {
                is RequestResult.Success<List<Category>> -> {
                    withContext(Dispatchers.IO) {
                        val db = (application as DBInit).database
                        db.categoryDao().clearAll()
                        db.categoryDao().insertAll(result.data)
                    }
                    privateCategoryState.value = CategoriesUIState(
                        category = result.data,
                    )
                }

                is RequestResult.Error -> {
                    Log.d("!!", "loadCategory $result")
                    val categoriesFromDb = withContext(Dispatchers.IO) {
                        (application as DBInit).database.categoryDao().getAllCategories()
                    }
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





