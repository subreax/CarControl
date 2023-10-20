package com.subreax.hackaton.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.subreax.hackaton.R
import kotlin.math.roundToInt

class LocationTrackerService : Service() {
    enum class Actions { START, STOP }

    val locationClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            onLocationUpdated(result)
        }
    }

    var lastLocation: Location? = null

    private var distanceTraveled = 0f

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("MissingPermission")
    private fun start() {
        updateNotification()

        val locationRequest = LocationRequest.Builder(2000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        locationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    override fun onDestroy() {
        super.onDestroy()

        locationClient.removeLocationUpdates(locationCallback)
    }

    private fun updateNotification() {
        var contentText = "Пройдено: ${distanceTraveled.roundToInt()} м"

        val notification = NotificationCompat.Builder(this, "running-channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Location tracker is active")
            .setContentText(contentText)
            .build()

        startForeground(1, notification)
    }

    private fun onLocationUpdated(location: LocationResult) {
        lastLocation?.let {
            if (location.lastLocation!!.speed > 1) {
                distanceTraveled += location.distanceTo(it)
                updateNotification()
            }
        }

        lastLocation = location.lastLocation
    }

    private fun LocationResult.distanceTo(location: Location): Float {
        return lastLocation?.let {
            location.distanceTo(it)
        } ?: 0f
    }
}