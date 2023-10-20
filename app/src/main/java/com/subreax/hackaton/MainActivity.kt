package com.subreax.hackaton

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.subreax.hackaton.service.LocationTrackerService
import com.subreax.hackaton.service.LocationTrackerServiceController
import com.subreax.hackaton.ui.theme.HackatonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), LocationTrackerServiceController {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissions = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
            getNormalPermissions()
        } else {
            getAndroid33Permissions()
        }

        ActivityCompat.requestPermissions(this, permissions, 0)

        setContent {
            HackatonTheme {
                MainNavigation(this)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getAndroid33Permissions(): Array<String> {
        return arrayOf(
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private fun getNormalPermissions(): Array<String> {
        return arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override fun startLocationTracker() {
        Intent(applicationContext, LocationTrackerService::class.java).also {
            it.action = LocationTrackerService.Actions.START.toString()
            startService(it)
        }
    }

    override fun stopLocationTracker() {
        Intent(applicationContext, LocationTrackerService::class.java).also {
            it.action = LocationTrackerService.Actions.STOP.toString()
            startService(it)
        }
    }
}
