package com.example.locationreminder.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.locationreminder.utils.FirebaseUserLiveData

class WelcomeViewModel : ViewModel() {
    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }
}
