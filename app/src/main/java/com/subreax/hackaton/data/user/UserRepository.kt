package com.subreax.hackaton.data.user

interface UserRepository {
    suspend fun getUser(): User
    suspend fun hasAtLeastOneCar(): Boolean
}