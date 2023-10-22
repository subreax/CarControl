package com.subreax.hackaton.data.auth

interface AuthRepository {
    suspend fun signIn(data: SignInData)
    suspend fun signUp(data: SignUpData)

    fun isAuthorized(): Boolean
    fun getToken(): String
}
