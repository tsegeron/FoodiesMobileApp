package com.example.foodies.presentation

import com.example.foodies.data.model.Category
import com.example.foodies.data.model.Dish
import com.example.foodies.data.model.Tag

data class FoodiesUiState(
    val isLoading: Boolean = false,

    val dishesList: List<Dish> = emptyList(),
    val categoriesList: List<Category> = emptyList(),
    val tagsList: List<Tag> = emptyList(),

    val dishesInCartList: List<Dish> = emptyList(),
    val dishesByFilterList: List<Dish> = emptyList(),

    val searchPrompt: String = "",

    val totalCost: Int = 0
)
