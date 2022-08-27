package com.example.locationreminder.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Reminders::class], version = 1, exportSchema = false)
abstract class RemindersDatabase : RoomDatabase() {
    abstract fun getRemindersDao(): RemindersDAO
}