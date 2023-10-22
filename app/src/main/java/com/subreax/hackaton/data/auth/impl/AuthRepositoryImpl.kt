package com.subreax.hackaton.data.auth.impl

import com.subreax.hackaton.data.auth.AuthRepository
import com.subreax.hackaton.data.auth.SignInData
import com.subreax.hackaton.data.auth.SignUpData
import com.subreax.hackaton.data.retrofit.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val retrofitService: RetrofitService,
    private val localTokenDataSource: LocalTokenDataSource
) : AuthRepository {
    private var token = ""

    init {
        val t = localTokenDataSource.loadToken() ?: ""
        token = "Bearer $t"
        Timber.d("Token: $token")
    }

    override suspend fun signIn(data: SignInData) {
        withContext(Dispatchers.IO) {
            try {
                val response = retrofitService.signIn(data.email, data.password)
                handleNewToken(response.token)
            } catch (ex: HttpException) {
                if (ex.code() == 400) {
                    throw Exception("Такой пользователь уже зарегистрирован")
                } else {
                    throw Exception("Неизвестная ошибка (${ex.code()})")
                }
            }
        }
    }

    override suspend fun signUp(data: SignUpData) {
        withContext(Dispatchers.IO) {
            try {
                val response = retrofitService.signUp(data.username, data.email, data.password)
                handleNewToken(response.token)
            } catch (ex: HttpException) {
                if (ex.code() == 400) {
                    throw Exception("Такой пользователь уже зарегистрирован")
                } else {
                    throw Exception("Неизвестная ошибка (${ex.code()})")
                }
            }
        }
    }

    private fun handleNewToken(token: String) {
        this.token = "Bearer $token"
        localTokenDataSource.saveToken(token)
    }

    override fun isAuthorized() = token.length > 7

    override fun getToken(): String {
        return token
    }
}