package com.subreax.hackaton.data

import java.util.Date
import java.util.UUID

data class CarPart(
    val id: UUID,
    val name: String,
    val type: CarPartGroup,
    val mileage: Int,
    val mileageLeft: Int,
    val initialTimeOfService: Long,
    val installationDate: Date,
    val expiresAt: Date
) {
    val health: Float
        get() {
            return (mileageLeft.toFloat() / mileage) * timeCoeff
        }

    val timeOfService: Long
        get() = expiresAt.time - installationDate.time

    val remainingTime: Long
        get() = expiresAt.time - System.currentTimeMillis()

    private val timeCoeff: Float
        get() = clamp(remainingTime.toFloat() / timeOfService, 0f, 1f)

    private fun clamp(value: Float, minValue: Float, maxValue: Float): Float {
        return if (value < minValue) {
            minValue
        } else if (value > maxValue) {
            maxValue
        } else {
            value
        }
    }

    fun getNew(): CarPart {
        return copy(
            mileageLeft = mileage,
            installationDate = Date(),
            expiresAt = Date(System.currentTimeMillis() + initialTimeOfService)
        )
    }
}