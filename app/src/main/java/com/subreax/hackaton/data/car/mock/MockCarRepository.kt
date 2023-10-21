package com.subreax.hackaton.data.car.mock

import com.subreax.hackaton.data.Car
import com.subreax.hackaton.data.car.CarRepository
import java.util.UUID
import javax.inject.Inject

class MockCarRepository @Inject constructor(
) : CarRepository {
    override suspend fun getCarTemplates(): List<Car> {
        return listOf(
            Car(UUID.randomUUID(), "Kia Rio", emptyList(), 0.0, Car.Type.Passenger),
            Car(UUID.randomUUID(), "Hyundai Solaris", emptyList(), 0.0, Car.Type.Passenger),
            Car(UUID.randomUUID(), "Mazda RX-8", emptyList(), 0.0, Car.Type.Passenger),
            Car(UUID.randomUUID(), "Lada X-Ray", emptyList(), 0.0, Car.Type.Truck),
        )
    }
}