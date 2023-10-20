package com.subreax.hackaton

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.subreax.hackaton.service.LocationTrackerServiceController
import com.subreax.hackaton.ui.home.HomeScreen

private object Screens {
    const val home = "home"
}

@Composable
fun MainNavigation(
    locationTrackerServiceController: LocationTrackerServiceController,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screens.home) {
        composable(Screens.home) {
            HomeScreen(locationTrackerServiceController)
        }
    }
}
