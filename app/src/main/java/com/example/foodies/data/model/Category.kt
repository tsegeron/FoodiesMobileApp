package com.example.foodies.data.model


/**
 * A class for Categories parsed from API
 *
 * @param isSelected the marked state of Category
 */
data class Category(
    val id: Int,
    val name: String,

    val isSelected: Boolean = false
)
