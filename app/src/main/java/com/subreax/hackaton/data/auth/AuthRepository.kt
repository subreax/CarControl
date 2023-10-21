package com.subreax.hackaton.data.auth

interface AuthRepository {
    suspend fun signIn(data: SignInData)
    suspend fun signUp(data: SignUpData)

    suspend fun isAuthorized(): Boolean
    suspend fun getToken(): String
}
