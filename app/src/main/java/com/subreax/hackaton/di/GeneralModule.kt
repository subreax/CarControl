package com.subreax.hackaton.di

import com.subreax.hackaton.data.car.CarRepository
import com.subreax.hackaton.data.car.mock.MockCarRepository
import com.subreax.hackaton.data.user.UserRepository
import com.subreax.hackaton.data.user.auth.AuthRepository
import com.subreax.hackaton.data.user.auth.mock.MockAuthRepository
import com.subreax.hackaton.data.user.impl.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GeneralModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: MockAuthRepository): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindCarRepository(impl: MockCarRepository): CarRepository
}