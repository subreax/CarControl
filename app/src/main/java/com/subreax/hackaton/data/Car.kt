package com.subreax.hackaton.data

import java.util.UUID

data class Car(
    val id: UUID,
    val name: String,
    val parts: List<CarPart>,
    val mileage: Double,
    val type: Type
) {
    enum class Type {
        Passenger, Truck
    }
}
