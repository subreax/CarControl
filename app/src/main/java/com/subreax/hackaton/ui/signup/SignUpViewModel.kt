package com.subreax.hackaton.ui.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SignUpViewModel @Inject constructor() : ViewModel() {
    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var passwordRepeat by mutableStateOf("")
        private set

    var buttonSignUpEnabled by mutableStateOf(false)
        private set

    var error by mutableStateOf("")
        private set

    var eventNavHomeScreen by mutableStateOf(false)
        private set

    fun updateUsername(newUsername: String) {
        username = newUsername
    }

    fun updatePassword(newPassword: String) {
        password = newPassword
    }

    fun updatePasswordRepeat(newPasswordRepeat: String) {
        passwordRepeat = newPasswordRepeat
    }

    fun signUp() {

    }

    fun navHomeScreenHandled() {
        eventNavHomeScreen = false
    }
}