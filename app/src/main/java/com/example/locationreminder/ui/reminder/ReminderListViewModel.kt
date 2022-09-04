package com.example.locationreminder.ui.reminder

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locationreminder.database.Reminders
import com.example.locationreminder.repository.RemindersRepository
import com.example.locationreminder.repository.RemindersRepositoryImpl
import com.example.locationreminder.utils.Event
import kotlinx.coroutines.launch
import timber.log.Timber

class ReminderListViewModel(
    private val repository: RemindersRepository
) : ViewModel() {
    // Variable used for two way dataBinding
    val tittle = MutableLiveData<String?>()
    val description = MutableLiveData<String?>()
    val location = MutableLiveData<String?>()

    // Getting all the reminders
    val getAllReminders = repository.reminder

    // Declaring Event
    private val _statusMessage = MutableLiveData<Event<String>>()
    val statusMessage: LiveData<Event<String>>
        get() = _statusMessage

    val reminders = MutableLiveData<Reminders>()

    // For handling the navigation of add FAB
    private val _statusOfAddFab = MutableLiveData<Boolean>()
    val statusOfAddFab: LiveData<Boolean>
        get() = _statusOfAddFab

    private var _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean>
        get() = _refresh

    // For handling the navigation of save FAB
    private val _statusOfSaveButton = MutableLiveData<Boolean>()
    val statusOfSaveButton: LiveData<Boolean>
        get() = _statusOfSaveButton

    init {
        _refresh.value = false
    }

    // it will update room database
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

    // save FAB function
    fun saveButtonStatusChangeOnClicked() {
        // adding validation check of the input field
        Log.e("hare", "re")
        if (tittle.value == null) {
            _statusMessage.value = Event("Please enter a Tittle")
        } else if (description.value == null) {
            _statusMessage.value = Event("Please enter a Description")
        } else if (location.value == null) {
            _statusMessage.value = Event("Please select a Location of Interest")
        } else {
            reminders.value = Reminders(
                tittle.value!!,
                description.toString(),
                location.value.toString()
            )
            updateLocalDatabase(reminders.value!!)
            _statusOfSaveButton.value = true
            resetData()
        }
    }

    private fun resetData() {
        tittle.value = null
        description.value = null
        location.value = null
    }

    fun saveButtonStatusChangeOnNavigated() {
        _statusOfSaveButton.value = false
    }

    fun onSwipe() {
        _refresh.value = true
    }

    fun onSwipeEnd() {
        _refresh.value = false
    }

}