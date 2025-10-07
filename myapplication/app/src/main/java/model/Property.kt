package com.example.myapplication.model

data class Property(
    val id: Int,
    val title: String,
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val type: String,
    val location: String
)
