package com.example.locationreminder.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.locationreminder.model.Reminder

@Entity(tableName = "reminders_table")
data class Reminders(
    var tittle: String,
    var description: String,
    var selectedLocation: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}


fun List<Reminders>.asDatabaseDomainModel(): List<Reminder> {
    return map {
        Reminder(
            id = it.id,
            tittle = it.tittle,
            description = it.description,
            selectedLocation = it.selectedLocation
        )
    }
}