package com.example.foodies.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodies.data.DishesRepository
import com.example.foodies.data.model.Dish
import com.example.foodies.data.Result
import com.example.foodies.data.model.Category
import com.example.foodies.data.model.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


private const val TAG = "ViewModel"

class FoodiesViewModel(
    private val dishesRepository: DishesRepository
): ViewModel() {

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow(FoodiesUiState())
    val uiState = _uiState.asStateFlow()


    init {
        _getTagsList()
        _getDishesList()
        _getCategoriesList()
    }


    fun onSearchActionClick(searchPrompt: String) {
        _uiState.update {
            it.copy(
                searchPrompt = searchPrompt
            )
        }
    }

    fun getDishesBySearchPrompt(searchPrompt: String): List<Dish> {
        return _uiState.value.dishesList.filter { it.name.contains(searchPrompt, ignoreCase = true) }
    }

    fun markCategory(category: Category) {
        val updatedCategory: Category = category.copy(isSelected = !category.isSelected)
        val updatedCategoryList = _uiState.value.categoriesList.map { if (it == category) updatedCategory else it }

        _uiState.update {
            it.copy(
                categoriesList = updatedCategoryList,
                dishesByFilterList = _getCurrentDishesList(updatedCategoryList = updatedCategoryList)
            )
        }
    }

    fun applyFilterChanges(tagsCheckedState: MutableList<Boolean>) {
        val updatedTagsList = _uiState.value.tagsList.mapIndexed { index, tag ->
            tag.copy(isSelected = tagsCheckedState[index])
        }

        _uiState.update {
            it.copy(
                tagsList = updatedTagsList,
                dishesByFilterList = _getCurrentDishesList(
                    updatedTagsList = updatedTagsList.filter { tag -> tag.isSelected }
                )
            )
        }
    }

    fun getSelectedTagsCount(): Int {
        return _uiState.value.tagsList.count { it.isSelected }
    }

    fun addDishToCart(dish: Dish) {
        val updatedDish: Dish = dish.copy(quantity = dish.quantity + 1)

        _updateDishes(
            dishesInCartUpdated = _uiState.value.dishesInCartList + updatedDish,
            dishesListUpdated = _uiState.value.dishesList.map { if (it == dish) updatedDish else it },
            totalCostUpdated = _uiState.value.totalCost + dish.price_current
        )
    }

    fun emptyCart() {
        val updatedDishList: List<Dish> = _uiState.value.dishesList.map { dish ->
            if (dish.quantity != 0) dish.copy(quantity = 0) else dish
        }

        _uiState.update {
            it.copy(
                dishesList = updatedDishList,
                dishesInCartList = emptyList(),
                dishesByFilterList = _getCurrentDishesList(updatedDishList = updatedDishList),
                searchPrompt = "",
                totalCost = 0
            )
        }
    }

    fun increaseDishCounter(dish: Dish) {
        val updatedDish: Dish = dish.copy(quantity = dish.quantity + 1)

        _updateDishes(
            dishesInCartUpdated = _uiState.value.dishesInCartList.map { if (it == dish) updatedDish else it },
            dishesListUpdated = _uiState.value.dishesList.map { if (it == dish) updatedDish else it },
            totalCostUpdated = _uiState.value.totalCost + dish.price_current
        )
    }

    fun decreaseDishCounter(dish: Dish) {
        val updatedDish: Dish = dish.copy(quantity = dish.quantity - 1)
        val dishesInCartUpdated: List<Dish> =
            if (updatedDish.quantity == 0)
                _uiState.value.dishesInCartList - dish
            else
                _uiState.value.dishesInCartList.map { if (it == dish) updatedDish else it }

        _updateDishes(
            dishesInCartUpdated = dishesInCartUpdated,
            dishesListUpdated = _uiState.value.dishesList.map { if (it == dish) updatedDish else it },
            totalCostUpdated = _uiState.value.totalCost - dish.price_current
        )
    }

    private fun _getCurrentDishesList(
        updatedDishList: List<Dish> = _uiState.value.dishesList,
        updatedCategoryList: List<Category> = _uiState.value.categoriesList,
        updatedTagsList: List<Tag> = _uiState.value.tagsList,
    ): List<Dish> {
        val markedCategoriesIds: Set<Int> = updatedCategoryList.filter { it.isSelected }.map { it.id }.toSet()
        val markedTagsIds: Set<Int> = updatedTagsList.filter { it.isSelected }.map { it.id }.toSet()

        return updatedDishList.toMutableList().apply {
            if (markedCategoriesIds.isNotEmpty()) {
                retainAll { it.category_id in markedCategoriesIds }
            }
            if (markedTagsIds.isNotEmpty()) {
                retainAll { dish -> dish.tag_ids.isNotEmpty() && markedTagsIds.all { it in dish.tag_ids } }
            }
        }
    }

    private fun _updateDishes(
        dishesInCartUpdated: List<Dish>,
        dishesListUpdated: List<Dish>,
        totalCostUpdated: Int
    ) {
        _uiState.update {
            it.copy(
                dishesInCartList = dishesInCartUpdated,
                dishesList = dishesListUpdated,
                dishesByFilterList = _getCurrentDishesList(updatedDishList = dishesListUpdated),
                totalCost = totalCostUpdated
            )
        }
    }


    private fun _getDishesList() {
        viewModelScope.launch {
            dishesRepository.getDishesList().collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        Log.d(TAG, "_getDishesList Error")
                    }

                    is Result.Success -> {
                        val newTagId = _uiState.value.tagsList.size
                        result.data?.let { dishes ->
                            val dishesUpdated = dishes.map { dish ->
                                dish.copy(
                                    price_current = dish.price_current.div(100),
                                    price_old = dish.price_old?.div(100),
                                    tag_ids = if (dish.price_old != null) dish.tag_ids + newTagId else dish.tag_ids
                                )
                            }
                            _uiState.update {
                                it.copy(
                                    dishesList = dishesUpdated,
                                    dishesByFilterList = dishesUpdated
                                )
                            }
                        }
                        Log.d(TAG, "_getDishesList Success")
                    }

                    is Result.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                        Log.d(TAG, "_getDishesList Loading")
                    }
                }
            }
        }
    }

    private fun _getCategoriesList() {
        viewModelScope.launch {
            dishesRepository.getCategoriesList().collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }

                    is Result.Success -> {
                        result.data?.let { categories ->
                            _uiState.update {
                                it.copy(
                                    categoriesList = categories
                                )
                            }
                        }
                    }

                    is Result.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                }
            }
        }
    }

    private fun _getTagsList() {
        viewModelScope.launch {
            dishesRepository.getTagsList().collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }

                    is Result.Success -> {
                        result.data?.let { tags ->
                            _uiState.update {
                                it.copy(
                                    tagsList = tags + Tag(
                                        tags.size + 1,
                                        "Со скидкой"
                                    ) // TODO: add string?
                                )
                            }
                        }
                    }

                    is Result.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                }
            }
        }
    }
}
