package com.subreax.hackaton.service

import java.util.UUID

interface LocationTrackerServiceController {
    fun startLocationTracker(carId: UUID)
    fun stopLocationTracker()
}