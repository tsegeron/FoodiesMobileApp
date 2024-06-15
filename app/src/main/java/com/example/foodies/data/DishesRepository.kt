package com.example.foodies.data

import com.example.foodies.data.model.Category
import com.example.foodies.data.model.Dish
import com.example.foodies.data.model.Tag
import kotlinx.coroutines.flow.Flow

interface DishesRepository {
    suspend fun getDishesList(): Flow<Result<List<Dish>>>
    suspend fun getCategoriesList(): Flow<Result<List<Category>>>
    suspend fun getTagsList(): Flow<Result<List<Tag>>>
}
