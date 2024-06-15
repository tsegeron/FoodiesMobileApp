package com.example.foodies.data.model


/**
 * A class for Tags parsed from API
 *
 * @param isSelected the marked state of tag
 */
data class Tag(
    val id: Int,
    val name: String,

    val isSelected: Boolean = false
)
