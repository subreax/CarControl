package com.subreax.hackaton.data

import java.util.UUID

data class CarPart(
    val id: UUID,
    val name: String,
    val type: CarPartGroup
) {

}