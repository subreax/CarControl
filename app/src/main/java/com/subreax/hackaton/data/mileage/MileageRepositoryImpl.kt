package com.subreax.hackaton.data.mileage

import com.subreax.hackaton.data.Car
import com.subreax.hackaton.data.car.CarRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MileageRepositoryImpl @Inject constructor(
    //private val carRepository: CarRepository
    private val localMileageDataSource: LocalMileageDataSource
) : MileageRepository {
    private var car: Car? = null

    init {

    }

    override fun selectCar(car: Car) {
        this.car = car
    }

    override fun getSelectedCar(): Car? {
        return car
    }

    override fun increaseMileage(deltaMileageMeters: Float): Float {
        val car1 = car ?: return 0f
        val m = car1.mileage + deltaMileageMeters
        car1.mileage = m
        return m
    }

    override fun trackMileage(car: Car): Flow<Float> {
        return flow {
            while (true) {
                val mileage = localMileageDataSource.loadMileage(car.id)
                emit(mileage)
                delay(1000)
            }
        }
    }
}