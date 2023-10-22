package com.subreax.hackaton.di

import com.subreax.hackaton.data.retrofit.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofitService(): RetrofitService {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)
                if (response.isSuccessful) {
                    response.body?.contentType()?.let {
                        if (it.type == "text" && it.subtype == "plain") {
                            val token = response.body!!.string()
                            val body = """ {"token":"$token"} """.toResponseBody()
                            return@addInterceptor response.newBuilder()
                                .body(body)
                                .header("Content-Type", "application/json")
                                .build()
                        }
                    }
                }
                response
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(RetrofitService.baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }
}