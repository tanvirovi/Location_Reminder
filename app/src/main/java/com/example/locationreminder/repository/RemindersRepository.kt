package com.example.locationreminder.repository

import androidx.lifecycle.LiveData
import com.example.locationreminder.database.Reminders
import com.example.locationreminder.model.Reminder

interface RemindersRepository {

    val reminder: LiveData<List<Reminder>>

    suspend fun deleteReminders(reminders: Reminders)

    suspend fun insertReminders(reminders: Reminders)

    fun getAllReminders(): LiveData<List<Reminders>>
}