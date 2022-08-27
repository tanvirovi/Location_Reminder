package com.example.locationreminder.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RemindersDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminders(reminders: Reminders)

    @Delete
    suspend fun deleteReminders(reminders: Reminders)

    @Query("select * from reminders_table order by id desc")
    fun getAllRemindersById(): LiveData<List<Reminders>>

}