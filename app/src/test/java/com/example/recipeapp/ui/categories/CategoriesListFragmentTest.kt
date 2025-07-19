package com.example.recipeapp.ui.categories

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.example.recipeapp.model.Category
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class CategoriesListFragmentTest {

    val mockViewModel = mockk<CategoriesListViewModel>()
    val mockNavController = mockk<NavController>(relaxed = true)
    val navigator = CategoriesNavigator(mockViewModel, mockNavController)

    val categories: List<Category> = listOf(
        Category(0, "Бургеры", "Рецепты всех популярных видов бургеров", "burger.png"),
        Category(1, "Десерты", "Самые вкусные рецепты десертов специально для вас", "dessert.png"),
        Category(2, "Пицца", "Пицца на любой вкус и цвет. Лучшая подборка для тебя", "pizza.png"),
        Category(3, "Рыба", "Печеная, жареная, сушеная, любая рыба на твой вкус", "fish.png"),
        Category(4, "Супы", "От классики до экзотики: мир в одной тарелке", "soup.png"),
        Category(5, "Салаты", "Хрустящий калейдоскоп под соусом вдохновения", "salad.png"),
    )

    @Test
    fun `openRecipesByCategoryId should navigate when category exists`() {
        every { mockViewModel.returnCategory() } returns listOf(
            Category(
                id = 1,
                title = "Test Category",
                description = "Test Description",
                imageUrl = "http://example.com/image.jpg"
            )
        )

        navigator.openRecipesByCategoryId(1)

        verify { mockNavController.navigate(any<NavDirections>()) }
    }

}

class CategoriesNavigator(
    private val viewModel: CategoriesListViewModel,
    private val navController: NavController
) {
    fun openRecipesByCategoryId(categoryId: Int) {
        val idCategory = viewModel.returnCategory().find { it.id == categoryId }
            ?: throw IllegalArgumentException("Category with id $categoryId not found")
        val direction =
            CategoriesListFragmentDirections.actionCategoriesListFragmentToRecipesListFragment(
                category = idCategory
            )
        navController.navigate(direction)
    }
}