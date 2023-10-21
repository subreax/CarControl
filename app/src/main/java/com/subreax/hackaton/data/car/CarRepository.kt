package com.subreax.hackaton.data.car

import com.subreax.hackaton.data.Car
import java.util.UUID

interface CarRepository {
    suspend fun getCarTemplates(): List<Car>
    suspend fun getTemplateCarById(id: UUID): Car?
}