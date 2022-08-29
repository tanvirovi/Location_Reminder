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
    private val repository: RemindersRepository
) : ViewModel() {
    val tittle = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val location = MutableLiveData<String>()
    val getAllReminders = repository.reminder

    val reminders = MutableLiveData<Reminders>()

    private val _statusOfAddFab = MutableLiveData<Boolean>()
    val statusOfAddFab: LiveData<Boolean>
        get() = _statusOfAddFab
    private val _statusOfSaveFab = MutableLiveData<Boolean>()
    val statusOfSaveFab: LiveData<Boolean>
        get() = _statusOfAddFab

    init {

    }

    private fun updateLocalDatabase(reminders: Reminders) {
        viewModelScope.launch {
            repository.insertReminders(
                reminders
            )
        }
    }

    fun fabStatusChangeOnClicked() {
        _statusOfAddFab.value = true
    }
    fun fabStatusChangeOnNavigated() {
        _statusOfAddFab.value = false
    }

    fun saveFabStatusChangeOnClicked() {
        reminders.value = Reminders(
            tittle.value!!,
            description.toString(),
            "asdsad"
        )
        updateLocalDatabase(reminders.value!!)
        _statusOfAddFab.value = true
    }
    fun saveFabStatusChangeOnNavigated() {
        _statusOfAddFab.value = false
    }

}