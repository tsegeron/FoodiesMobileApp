package com.example.foodies.data.repo

import com.example.foodies.network.Result
import com.example.foodies.data.model.Category
import com.example.foodies.data.model.Dish
import com.example.foodies.data.model.Tag
import kotlinx.coroutines.flow.Flow


/**
 * Repository that provides [Dish], [Category], [Tag] from the server.
 */
interface DishesRepository {

    /**
     * Retrieve all the [Dish] from the the server.
     */
    suspend fun getDishesList(): Flow<Result<List<Dish>>>

    /**
     * Retrieve all the [Category] from the the server.
     */
    suspend fun getCategoriesList(): Flow<Result<List<Category>>>

    /**
     * Retrieve all the [Tag] from the the server.
     */
    suspend fun getTagsList(): Flow<Result<List<Tag>>>
}
