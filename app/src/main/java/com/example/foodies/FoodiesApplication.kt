package com.example.foodies

import android.app.Application
import com.example.foodies.data.FoodiesContainer
import com.example.foodies.data.FoodiesDataContainer

class FoodiesApplication: Application() {
    lateinit var container: FoodiesContainer

    override fun onCreate() {
        super.onCreate()
        container = FoodiesDataContainer(this)
    }
}
