package com.example.foodies.data

import com.example.foodies.data.model.Category
import com.example.foodies.data.model.Dish
import com.example.foodies.data.model.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class DishesRepositoryImpl(
    private val api: DishApiService
): DishesRepository {
    override suspend fun getDishesList(): Flow<Result<List<Dish>>> {
        return flow {
            val dishesFromApi = try {
                api.getDishesList()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading dishes"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading dishes"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading dishes"))
                return@flow
            }

            emit(Result.Success(data = dishesFromApi))
        }
    }

    override suspend fun getCategoriesList(): Flow<Result<List<Category>>> {
        return flow {
            val categoriesFromApi = try {
                api.getCategoriesList()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading categories"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading categories"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading categories"))
                return@flow
            }

            emit(Result.Success(data = categoriesFromApi))
        }
    }

    override suspend fun getTagsList(): Flow<Result<List<Tag>>> {
        return flow {
            val tagsFromApi = try {
                api.getTagsList()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading tags"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading tags"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading tags"))
                return@flow
            }

            emit(Result.Success(data = tagsFromApi))
        }
    }
}
