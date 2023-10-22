package com.subreax.hackaton.di

import com.subreax.hackaton.data.car.CarRepository
import com.subreax.hackaton.data.car.mock.MockCarRepository
import com.subreax.hackaton.data.user.UserRepository
import com.subreax.hackaton.data.auth.AuthRepository
import com.subreax.hackaton.data.auth.impl.AuthRepositoryImpl
import com.subreax.hackaton.data.auth.mock.MockAuthRepository
import com.subreax.hackaton.data.car.impl.CarRepositoryImpl
import com.subreax.hackaton.data.mileage.MileageRepository
import com.subreax.hackaton.data.mileage.MileageRepositoryImpl
import com.subreax.hackaton.data.user.mock.MockUserRepository
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
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: MockUserRepository): UserRepository

    @Binds
    @Singleton
    abstract fun bindCarRepository(impl: MockCarRepository): CarRepository

    @Binds
    @Singleton
    abstract fun bindMileageRepository(impl: MileageRepositoryImpl): MileageRepository
}