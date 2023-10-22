package com.subreax.hackaton.data.retrofit

import java.util.UUID

data class NetworkPartTemplate(
    val uniqueId: UUID,
    val name: String,
    val typeKey: String,
    val strengthInKilometers: Int,
    val strengthInDays: Int
)
