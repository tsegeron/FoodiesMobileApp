package com.example.foodies.data.model


/**
 * A class for Dish parsed from API
 *
 * @param quantity the amount of this dish ordered
 */
data class Dish(
    val id: Int,
    val category_id: Int,
    val name: String,
    val description: String,
    val image: String,
    val price_current: Int,
    val price_old: Int?,
    val measure: Int,
    val measure_unit: String,
    val energy_per_100_grams: Double,
    val proteins_per_100_grams: Double,
    val fats_per_100_grams: Double,
    val carbohydrates_per_100_grams: Double,
    val tag_ids: List<Int>,

    val quantity: Int = 0
)
