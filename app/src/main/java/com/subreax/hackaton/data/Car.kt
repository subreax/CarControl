package com.subreax.hackaton.data

import java.util.UUID

data class Car(
    val id: UUID,
    val name: String,
    val parts: List<Part2>,
    var mileage: Float,
    val type: Type
) {
    enum class Type {
        Passenger, Truck
    }

    fun getNewCar(): Car {
        return copy(
            parts = parts.map { it.getNew(mileage) }
        )
    }

    fun updateMileage(newMileage: Float) {
        mileage = newMileage
        parts.forEach {
            it.calcHealth(mileage)
        }
    }
}

data class CarTemplate(
    val id: UUID,
    val name: String,
    val parts: List<UUID>,
    val type: Car.Type
)