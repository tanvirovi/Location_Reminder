package com.example.locationreminder.model

data class Reminder(
    val id: Int?,
    val tittle: String,
    val description: String,
    val selectedLocation: String,
    val lat: Double?,
    val lng: Double?
)
