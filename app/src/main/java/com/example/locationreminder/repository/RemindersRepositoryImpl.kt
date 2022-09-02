package com.example.locationreminder.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.locationreminder.database.Reminders
import com.example.locationreminder.database.RemindersDAO
import com.example.locationreminder.database.RemindersDatabase
import com.example.locationreminder.database.asDatabaseDomainModel
import com.example.locationreminder.model.Reminder
import timber.log.Timber

class RemindersRepositoryImpl(
    private val dao: RemindersDAO
) : RemindersRepository {

    override val reminder: LiveData<List<Reminder>> =
        Transformations.map(dao.getAllRemindersById()) {
            it.asDatabaseDomainModel()
        }

    override suspend fun deleteReminders(reminders: Reminders) {
        dao.deleteReminders(reminders)
    }

    override suspend fun insertReminders(reminders: Reminders) {
        dao.insertReminders(reminders)
    }

    override fun getAllReminders(): LiveData<List<Reminders>> {
        return dao.getAllRemindersById()
    }

}