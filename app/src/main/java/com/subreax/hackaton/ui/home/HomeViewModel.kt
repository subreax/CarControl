package com.subreax.hackaton.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subreax.hackaton.data.user.User
import com.subreax.hackaton.data.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    var counter by mutableStateOf(0)
        private set

    var user by mutableStateOf<User?>(null)
        private set

    init {
        viewModelScope.launch {
            while (isActive) {
                counter += 1
                delay(1000)
            }
        }

        viewModelScope.launch {
            user = userRepository.getUser()
        }
    }
}