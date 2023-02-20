package com.example.amphibians.data

import kotlinx.serialization.SerialName

data class Amphibian(
    val name: String,
    val type: String,
    val description: String,
    @SerialName(value = "img_src")
    val imgSrc: String
)
