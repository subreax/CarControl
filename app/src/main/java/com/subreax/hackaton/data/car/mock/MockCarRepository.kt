package com.subreax.hackaton.data.car.mock

import com.subreax.hackaton.data.Car
import com.subreax.hackaton.data.CarPart
import com.subreax.hackaton.data.CarPartGroup
import com.subreax.hackaton.data.car.CarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class MockCarRepository @Inject constructor(
) : CarRepository {
    private val settingsIcon =
        URL("https://upload.wikimedia.org/wikipedia/commons/3/39/Icons8_flat_settings.svg")

    private val candles = CarPartGroup(
        id = UUID.randomUUID(),
        name = "Свечи",
        description = "Зажги свой двигатель",
        iconUrl = settingsIcon
    )

    private val oil = CarPartGroup(
        id = UUID.randomUUID(),
        name = "Масло",
        description = "Подготовь мотор к интенсивному использованию",
        iconUrl = settingsIcon
    )

    private val templateCars by lazy {
        listOf(
            Car(
                UUID.randomUUID(), "Kia Rio", listOf(
                    CarPart(
                        id = UUID.randomUUID(),
                        name = "Масло",
                        type = oil,
                        500,
                        230,
                        1000000,
                        Date(),
                        Date(System.currentTimeMillis() + 1000000)
                    ),
                    CarPart(
                        id = UUID.randomUUID(),
                        name = "Свечи",
                        type = candles,
                        500,
                        500,
                        1000000,
                        Date(),
                        Date(System.currentTimeMillis() + 1000000)
                    )
                ), 0f, Car.Type.Passenger
            ),
            Car(UUID.randomUUID(), "Hyundai Solaris", emptyList(), 0f, Car.Type.Passenger),
            Car(UUID.randomUUID(), "Mazda RX-8", emptyList(), 0f, Car.Type.Passenger),
            Car(UUID.randomUUID(), "Lada X-Ray", emptyList(), 0f, Car.Type.Passenger),
        )
    }

    private val cars = mutableListOf(templateCars[0])

    override suspend fun getCarTemplates(): List<Car> {
        return withContext(Dispatchers.IO) {
            delay(1000)
            templateCars
        }
    }

    override suspend fun getCarTemplateById(id: UUID): Car? {
        return templateCars.find { it.id == id }?.getNewCar()
    }

    override suspend fun getCars(): List<Car> {
        return cars
    }

    override suspend fun updateCarMileage(car: Car, mileage: Float) {

    }

    override suspend fun addCar(car: Car) {
        cars.add(car)
    }
}