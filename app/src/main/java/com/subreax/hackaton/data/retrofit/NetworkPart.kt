package com.subreax.hackaton.data.retrofit

import java.util.Date
import java.util.UUID

data class NetworkPart(
    val uniqueId: UUID,
    val name: String,
    val typeKey: String,
    val originalStrengthInKm: Int,
    val leftStrengthInKm: Int,
    val installationDate: Date,
    val strengthExpireDate: Date
)
