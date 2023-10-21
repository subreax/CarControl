package com.subreax.hackaton.ui.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subreax.hackaton.data.user.auth.AuthRepository
import com.subreax.hackaton.data.user.auth.SignUpData
import com.subreax.hackaton.ui.Validators
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    var email by mutableStateOf("")
        private set

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

    fun updateEmail(newEmail: String) {
        email = newEmail
        updateSignUpButtonState()
    }

    fun updateUsername(newUsername: String) {
        username = newUsername
        updateSignUpButtonState()
    }

    fun updatePassword(newPassword: String) {
        password = newPassword
        updateSignUpButtonState()
    }

    fun updatePasswordRepeat(newPasswordRepeat: String) {
        passwordRepeat = newPasswordRepeat
        updateSignUpButtonState()
    }

    private fun updateSignUpButtonState() {
        buttonSignUpEnabled = Validators.isEmailCorrect(email) &&
                username.isNotEmpty() &&
                password.length > 5 &&
                password == passwordRepeat
    }

    fun signUp() {
        error = ""
        viewModelScope.launch {
            try {
                authRepository.signUp(SignUpData(email, username, password))
            } catch (ex: Exception) {
                error = ex.message ?: "Неизвестная ошибка"
            }
        }
    }

    fun navHomeScreenHandled() {
        eventNavHomeScreen = false
    }
}