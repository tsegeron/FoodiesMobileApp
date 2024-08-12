package com.example.foodies.network

import com.example.foodies.data.model.Category
import com.example.foodies.data.model.Dish
import com.example.foodies.data.model.Tag
import retrofit2.http.GET


interface DishApiService {
    @GET("Products.json")
    suspend fun getDishesList(): List<Dish>

    @GET("Categories.json")
    suspend fun getCategoriesList(): List<Category>

    @GET("Tags.json")
    suspend fun getTagsList(): List<Tag>

    companion object {
        const val BASE_URL = "https://anika1d.github.io/WorkTestServer/"
    }
}
