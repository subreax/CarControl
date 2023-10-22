package com.subreax.hackaton.data.car.mock

import com.subreax.hackaton.data.Car
import com.subreax.hackaton.data.CarPartGroup
import com.subreax.hackaton.data.CarTemplate
import com.subreax.hackaton.data.Part2
import com.subreax.hackaton.data.car.CarRepository
import com.subreax.hackaton.data.mileage.LocalMileageDataSource
import java.net.URL
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class MockCarRepository @Inject constructor(
    private val localMileageDataSource: LocalMileageDataSource
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
                    Part2(
                        id = UUID.fromString("8f0ea524-214c-414b-bab2-d0c465f272b5"),
                        name = "Масло 7к",
                        type = oil,
                        0,
                        7000 * 1000,
                        Date(),
                        Date(System.currentTimeMillis() + 86400000)
                    ),
                    Part2(
                        id = UUID.randomUUID(),
                        name = "Свечи 10к",
                        type = candles,
                        0,
                        10000 * 1000,
                        Date(),
                        Date(System.currentTimeMillis() + 86400000)
                    )
                ), 0f, Car.Type.Passenger
            ),
            Car(UUID.randomUUID(), "Hyundai Solaris", emptyList(), 0f, Car.Type.Passenger),
            Car(UUID.randomUUID(), "Mazda RX-8", emptyList(), 0f, Car.Type.Passenger),
            Car(UUID.randomUUID(), "Lada X-Ray", emptyList(), 0f, Car.Type.Passenger),
        )
    }

    private val cars = mutableListOf<Car>()

    override suspend fun getCarTemplates(): List<CarTemplate> {
        return templateCars.map { CarTemplate(it.id, it.name, emptyList(), it.type) }
    }

    override suspend fun getCarById(id: UUID): Car? {
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

    override suspend fun hasAtLeastOneCar(): Boolean {
        return false
    }

    override suspend fun createCarFromTemplateById(id: UUID): Car {
        val car = templateCars.find { it.id == id }!!.getNewCar()
        car.mileage = localMileageDataSource.loadMileage(id)
        addCar(car)
        return car
    }
}