package com.example.locationreminder.model

data class Reminder(
    val id : Long,
    val tittle : String,
    val description : String,
    val selectedLocation : String
)
