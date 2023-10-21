package com.subreax.hackaton.data.mileage

import com.subreax.hackaton.data.Car
import kotlinx.coroutines.flow.Flow

interface MileageRepository {
    fun selectCar(car: Car)
    fun getSelectedCar(): Car?

    fun increaseMileage(deltaMileageMeters: Float)
    fun trackMileage(car: Car): Flow<Float>
}