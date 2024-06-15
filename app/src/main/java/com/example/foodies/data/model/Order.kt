package com.example.foodies.data.model

data class Order(
    val dish: Dish,
    var quantity: Int = 0
)
