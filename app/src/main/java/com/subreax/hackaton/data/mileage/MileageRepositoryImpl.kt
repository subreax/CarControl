package com.subreax.hackaton.data.mileage

import com.subreax.hackaton.data.Car
import com.subreax.hackaton.data.car.CarRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MileageRepositoryImpl @Inject constructor(
    private val carRepository: CarRepository
) : MileageRepository {
    override fun selectCar(car: Car) {
        TODO("Not yet implemented")
    }

    override fun getSelectedCar(): Car? {
        TODO("Not yet implemented")
    }

    override fun increaseMileage(deltaMileageMeters: Float) {
        TODO("Not yet implemented")
    }

    override fun trackMileage(car: Car): Flow<Float> {
        return flow {
            while (true) {
                emit(car.mileage)
                delay(1000)
            }
        }
    }
}