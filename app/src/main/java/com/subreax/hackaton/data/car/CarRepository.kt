package com.subreax.hackaton.data.car

import com.subreax.hackaton.data.Car
import java.util.UUID

interface CarRepository {
    suspend fun getCarTemplates(): List<Car>
    suspend fun getCarTemplateById(id: UUID): Car?

    suspend fun getCars(): List<Car>
    suspend fun addCar(car: Car)
    suspend fun updateCarMileage(car: Car, mileage: Float)
}