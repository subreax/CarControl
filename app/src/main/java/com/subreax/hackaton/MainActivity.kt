package com.subreax.hackaton

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.subreax.hackaton.data.auth.AuthRepository
import com.subreax.hackaton.data.car.CarRepository
import com.subreax.hackaton.service.LocationTrackerService
import com.subreax.hackaton.service.LocationTrackerServiceController
import com.subreax.hackaton.ui.theme.HackatonTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity(), LocationTrackerServiceController {
    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var carRepository: CarRepository

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

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
                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    if (authRepository.isAuthorized()) {
                        if (carRepository.hasAtLeastOneCar()) {
                            navController.navigate(Screens.Home) {
                                popUpTo(Screens.Blank) { inclusive = true }
                            }
                        }
                        else {
                            navController.navigate(Screens.CarPicker) {
                                popUpTo(Screens.Blank) { inclusive = true }
                            }
                        }
                    }
                    else {
                        navController.navigate(Screens.Welcome) {
                            popUpTo(Screens.Blank) { inclusive = true }
                        }
                    }
                }

                MainNavigation(this, navController)
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
