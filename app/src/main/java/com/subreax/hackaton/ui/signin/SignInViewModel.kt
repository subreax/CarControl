package com.subreax.hackaton.ui.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subreax.hackaton.data.user.UserRepository
import com.subreax.hackaton.data.auth.AuthRepository
import com.subreax.hackaton.data.auth.SignInData
import com.subreax.hackaton.ui.Validators
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    enum class NavEvent {
        None, NavHome, NavToCarPicker
    }

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var signInButtonEnabled by mutableStateOf(false)
        private set

    var error by mutableStateOf("")
        private set

    var navEvent by mutableStateOf(NavEvent.None)
        private set

    fun updateEmail(newUsername: String) {
        email = newUsername
        updateSignInButtonState()
    }

    fun updatePassword(newPassword: String) {
        password = newPassword
        updateSignInButtonState()
    }

    private fun updateSignInButtonState() {
        signInButtonEnabled = Validators.isEmailCorrect(email) && password.length > 5
    }

    fun signIn() {
        error = ""
        viewModelScope.launch {
            try {
                authRepository.signIn(SignInData(email, password))
                navEvent = if (isUserHasACar()) {
                    NavEvent.NavHome
                } else {
                    NavEvent.NavToCarPicker
                }
            } catch (ex: Exception) {
                error = ex.message ?: "Неизвестная ошибка"
            }
        }
    }

    private suspend fun isUserHasACar(): Boolean {
        return userRepository.hasAtLeastOneCar()
    }

    fun resetNavEvent() {
        navEvent = NavEvent.None
    }
}