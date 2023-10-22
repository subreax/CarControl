package com.subreax.hackaton.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import java.util.Date
import java.util.UUID

data class Part2(
    val id: UUID,
    val name: String,
    val type: CarPartGroup,
    val mileageFrom: Int,
    val mileageSize: Int,
    val installationDate: Date,
    val expiresAt: Date
) {
    var health by mutableFloatStateOf(1f)

    fun calcHealth(carMileage: Float) {
        val partMileage = carMileage - mileageFrom
        val mileageK = clamp(1 - partMileage.toFloat() / mileageSize, 0f, 1f)

        val totalTimeOfService = expiresAt.time - installationDate.time
        val remainingTimeOfService = expiresAt.time - System.currentTimeMillis()

        val timeK = clamp(remainingTimeOfService.toFloat() / totalTimeOfService, 0f, 1f)

        health = mileageK * timeK
    }

    private fun clamp(value: Float, minValue: Float, maxValue: Float): Float {
        return if (value < minValue) {
            minValue
        } else if (value > maxValue) {
            maxValue
        } else {
            value
        }
    }

    fun getNew(carMileage: Float): Part2 {
        val totalTimeOfService = expiresAt.time - installationDate.time

        return copy(
            mileageFrom = carMileage.toInt(),
            installationDate = Date(),
            expiresAt = Date(System.currentTimeMillis() + totalTimeOfService)
        )
    }
}
