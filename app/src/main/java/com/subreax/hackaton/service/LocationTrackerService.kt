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
import com.subreax.hackaton.data.mileage.LocalMileageDataSource
import com.subreax.hackaton.data.mileage.MileageRepository
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class LocationTrackerService : Service() {
    enum class Actions { START, STOP }

    /*@Inject
    lateinit var mileageRepository: MileageRepository*/

    @Inject
    lateinit var localMileageDataSource: LocalMileageDataSource

    private val locationClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            onLocationUpdated(result)
        }
    }
    private var lastLocation: Location? = null
    private var mileage = 0f
    private var carId: UUID? = null

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start(intent)
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("MissingPermission")
    private fun start(intent: Intent) {
        updateNotification()

        intent.getStringExtra(EXTRA_CAR_ID)?.let {
            carId = UUID.fromString(it)
        }

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            //.setMinUpdateDistanceMeters(1f)
            .setMinUpdateIntervalMillis(500)
            .setWaitForAccurateLocation(true)
            .build()

        locationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    override fun onCreate() {
        super.onCreate()

        carId?.let {
            mileage = localMileageDataSource.loadMileage(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        locationClient.removeLocationUpdates(locationCallback)
    }

    private fun updateNotification() {
        var contentText = "Пробег: ${mileage.roundToInt()} м"

        val notification = NotificationCompat.Builder(this, "mileage-channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Отслеживание пробега включено")
            .setContentText(contentText)
            .build()

        startForeground(1, notification)
    }

    private fun onLocationUpdated(location: LocationResult) {
        lastLocation?.let {
            if (location.lastLocation!!.speed > 1f) {
                mileage += location.distanceTo(it)
                carId?.let { id ->
                    localMileageDataSource.saveMileage(id, mileage)
                }
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

    companion object {
        const val EXTRA_CAR_ID = "extra_car_id"
    }
}