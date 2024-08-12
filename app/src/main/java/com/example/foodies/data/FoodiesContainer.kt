package com.example.foodies.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodies.network.RetrofitInstance
import com.example.foodies.data.repo.DishesRepository
import com.example.foodies.data.repo.DishesRepositoryImpl
import com.example.foodies.data.repo.UserPreferencesRepo


private const val SEARCH_PREFERENCE_NAME = "search_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SEARCH_PREFERENCE_NAME
)

/**
 * App container for Dependency injection.
 */
interface FoodiesContainer {
    val dishesRepository: DishesRepository
    val userPreferencesRepo: UserPreferencesRepo
}

/**
 * [FoodiesContainer] implementation, provides instance of [DishesRepository]
 * and [UserPreferencesRepo]
 */
class FoodiesDataContainer(
    private val context: Context
): FoodiesContainer {
    override val dishesRepository: DishesRepository by lazy {
        DishesRepositoryImpl(RetrofitInstance.api)
    }

    override val userPreferencesRepo: UserPreferencesRepo by lazy {
        UserPreferencesRepo(context.dataStore)
    }
}
