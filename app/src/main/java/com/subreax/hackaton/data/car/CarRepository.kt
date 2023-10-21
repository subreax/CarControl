package com.subreax.hackaton.data.car

import com.subreax.hackaton.data.Car

interface CarRepository {
    suspend fun getCarTemplates(): List<Car>
}