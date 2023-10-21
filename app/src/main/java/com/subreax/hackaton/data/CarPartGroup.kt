package com.subreax.hackaton.data

import java.net.URL
import java.util.UUID

data class CarPartGroup(
    val id: UUID,
    val name: String,
    val description: String,
    val iconUrl: URL
)
