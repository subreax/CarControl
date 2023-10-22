package com.subreax.hackaton.data.retrofit

import com.subreax.hackaton.data.Car
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.UUID

interface RetrofitService {
    companion object {
        const val baseUrl = "https://sup.worldm.ru/api/v1/"
    }

    @GET("auth/login")
    suspend fun signIn(
        @Query("email") email: String,
        @Query("password") password: String
    ): AuthToken

    @GET("auth/register")
    suspend fun signUp(
        @Query("username") username: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): AuthToken

    @GET("car/list")
    suspend fun getCarTemplates(
        @Header("Authorization") token: String
    ): List<NetworkCarTemplate>

    @GET("custom_car/create_from_car")
    suspend fun createCarFromTemplate(
        @Header("Authorization") token: String,
        @Query("uniqueId") id: UUID
    ): NetworkCar

    @GET("custom_car_part/find_by_id")
    suspend fun findPartById(
        @Header("Authorization") token: String,
        @Query("uniqueId") id: UUID
    ): NetworkPart

    @GET("custom_car/find_by_id")
    suspend fun findCarById(
        @Header("Authorization") token: String,
        @Query("uniqueId") id: UUID
    ): NetworkCar?
}
