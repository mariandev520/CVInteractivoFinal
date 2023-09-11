package com.example.myapplication

import android.widget.ImageView

data class User(
    val name: Name,
    val email: String,
    val phone: String,
    val location: Location,
    val picture: Picture
)

data class Name(
    val first: String,
    val last: String
)

data class Location(
    val city: String,
    val country: String
)

data class Picture(
    val large: ImageView
)
