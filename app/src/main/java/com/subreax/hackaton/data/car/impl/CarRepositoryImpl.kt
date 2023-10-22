package com.subreax.hackaton.data.car.impl

import com.subreax.hackaton.data.Car
import com.subreax.hackaton.data.CarPart
import com.subreax.hackaton.data.CarPartGroup
import com.subreax.hackaton.data.CarTemplate
import com.subreax.hackaton.data.auth.AuthRepository
import com.subreax.hackaton.data.car.CarRepository
import com.subreax.hackaton.data.retrofit.NetworkPart
import com.subreax.hackaton.data.retrofit.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.URL
import java.util.UUID
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(
    private val retrofitService: RetrofitService,
    private val authRepository: AuthRepository
) : CarRepository {
    private var templates = listOf<CarTemplate>()

    private val settingsIcon =
        URL("https://upload.wikimedia.org/wikipedia/commons/3/39/Icons8_flat_settings.svg")

    private val candlesGroup = CarPartGroup(
        id = UUID.randomUUID(),
        name = "Свечи",
        description = "Зажги свой двигатель",
        iconUrl = settingsIcon
    )

    override suspend fun getCarTemplates(): List<CarTemplate> = withContext(Dispatchers.IO) {
        if (templates.isEmpty()) {
            val networkTemplates = retrofitService.getCarTemplates(authRepository.getToken())
            templates = networkTemplates.map {
                CarTemplate(it.uniqueId, it.name, it.parts, it.typeKey.toCarType())
            }
        }
        templates
    }

    override suspend fun createCarFromTemplateById(id: UUID): Car {
        val template = templates.find { it.id == id }!!
        val car = createAndAddCar(template.id)
        return car
    }

    override suspend fun getCarById(id: UUID): Car? {
        return null
    }

    override suspend fun getCars(): List<Car> {
        TODO("Not yet implemented")
    }

    override suspend fun addCar(car: Car) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCarMileage(car: Car, mileage: Float) {
        TODO("Not yet implemented")
    }

    override suspend fun hasAtLeastOneCar(): Boolean {
        return false // todo
    }

    private suspend fun createAndAddCar(id: UUID): Car {
        val networkCar = retrofitService.createCarFromTemplate(authRepository.getToken(), id)

        val networkParts = networkCar.parts.map {
            retrofitService.findPartById(authRepository.getToken(), it)
        }

        return Car(
            networkCar.uniqueId,
            networkCar.name,
            //networkParts.map { it.toPart() },
            emptyList(),
            networkCar.mileageInKilometers * 1000.0f,
            networkCar.typeKey.toCarType()
        ).also {
            Timber.d("Car created. Id: ${it.id}")
        }
    }

    private fun String.toCarType(): Car.Type {
        return if (this == "passenger_car") {
            Car.Type.Passenger
        } else {
            Car.Type.Truck
        }
    }

    private suspend fun NetworkPart.toPart(): CarPart {
        val initialTimeOfService = strengthExpireDate.time - installationDate.time

        return CarPart(
            uniqueId,
            name,
            candlesGroup,
            originalStrengthInKm * 1000,
            leftStrengthInKm * 1000,
            initialTimeOfService,
            installationDate,
            strengthExpireDate
        )
    }
}