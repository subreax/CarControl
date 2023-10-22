package com.subreax.hackaton.data.retrofit

import java.util.UUID

data class NetworkCar(
    val uniqueId: UUID,
    val name: String,
    val typeKey: String,
    val parts: List<UUID>,
    val mileageInKilometers: Int
)