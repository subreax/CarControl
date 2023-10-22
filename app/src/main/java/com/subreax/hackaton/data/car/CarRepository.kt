package com.subreax.hackaton.data.car

import com.subreax.hackaton.data.Car
import com.subreax.hackaton.data.CarTemplate
import java.util.UUID

interface CarRepository {
    suspend fun getCarTemplates(): List<CarTemplate>
    suspend fun getCarById(id: UUID): Car?

    suspend fun createCarFromTemplateById(id: UUID): Car

    suspend fun getCars(): List<Car>
    suspend fun addCar(car: Car)
    suspend fun updateCarMileage(car: Car, mileage: Float)

    suspend fun hasAtLeastOneCar(): Boolean
}