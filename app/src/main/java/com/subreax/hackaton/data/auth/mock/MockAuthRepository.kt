package com.subreax.hackaton.data.auth.mock

import com.subreax.hackaton.data.auth.AuthRepository
import com.subreax.hackaton.data.auth.SignInData
import com.subreax.hackaton.data.auth.SignUpData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MockAuthRepository @Inject constructor(): AuthRepository {
    private var isAuthorized = false

    override suspend fun signIn(data: SignInData) {
        withContext(Dispatchers.IO) {
            //delay(1000)
            if (data.email == "throw@gmail.com") {
                throw Exception("Неправильный email или пароль")
            }
            isAuthorized = true
        }
    }

    override suspend fun signUp(data: SignUpData) {
        withContext(Dispatchers.IO) {
            //delay(1000)
            if (data.email == "throw@gmail.com") {
                throw Exception("Ошибка")
            }
            isAuthorized = true
        }
    }

    override fun isAuthorized() = isAuthorized

    override fun getToken(): String {
        return "mock_token"
    }
}