package com.example.recipeapp

object STUB {

    private val burgerCategory = Category(
        0,
        "Бургеры",
        "Рецепты всех популярных видов бургеров",
        "burger.png"
    )
    private val dessertCategory = Category(
        1,
        "Десерты",
        "Самые вкусные рецепты десертов специально для вас",
        "dessert.png"
    )
    private val pizzaCategory = Category(
        2,
        "Пицца",
        "Пицца на любой вкус и цвет. Лучшая подборка для тебя",
        "pizza.png"
    )
    private val fishCategory = Category(
        3,
        "Рыба",
        "Печеная, жареная, сушеная, любая рыба на твой вкус",
        "fish.png"
    )
    private val soupCategory = Category(
        4,
        "Супы",
        "От классики до экзотики: мир в одной тарелке",
        "soup.png"
    )
    private val saladCategory = Category(
        5,
        "Салаты",
        "Хрустящий калейдоскоп под соусом вдохновения",
        "salad.png"
    )

    private val categories: List<Category> = listOf(
        burgerCategory,
        dessertCategory,
        pizzaCategory,
        fishCategory,
        soupCategory,
        saladCategory
    )

    fun getCategories(): List<Category> {
        return categories
    }
}