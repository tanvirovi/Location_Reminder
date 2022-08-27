package com.example.locationreminder.ui.reminder

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locationreminder.database.Reminders
import com.example.locationreminder.repository.RemindersRepository
import com.example.locationreminder.repository.RemindersRepositoryImpl
import kotlinx.coroutines.launch
import timber.log.Timber

class ReminderListViewModel(
    private val repository : RemindersRepository
): ViewModel() {

    val getAllReminders = repository.reminder

    init {
        viewModelScope.launch {
            repository.insertReminders(Reminders("wdwdw","wdwdw","dwdw"))
        }
        Timber.e(repository.getAllReminders().value.toString())

    }

}