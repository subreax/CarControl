package com.subreax.hackaton

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.subreax.hackaton.service.LocationTrackerServiceController
import com.subreax.hackaton.ui.WelcomeScreen
import com.subreax.hackaton.ui.home.HomeScreen
import com.subreax.hackaton.ui.signin.SignInScreen

private object Screens {
    const val welcome = "welcome"
    const val signIn = "sign_in"
    const val home = "home"
}

@Composable
fun MainNavigation(
    locationTrackerServiceController: LocationTrackerServiceController,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screens.welcome) {
        composable(Screens.welcome) {
            WelcomeScreen(
                signInClicked = {
                    navController.navigate(Screens.signIn)
                },
                signUpClicked = {

                }
            )
        }

        composable(Screens.signIn) {
            SignInScreen(navBack = {
                navController.popBackStack()
            })
        }

        composable(Screens.home) {
            HomeScreen(locationTrackerServiceController)
        }
    }
}
