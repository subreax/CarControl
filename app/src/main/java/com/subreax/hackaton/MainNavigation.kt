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
import com.subreax.hackaton.ui.signup.SignUpScreen

private object Screens {
    const val welcome = "welcome"
    const val signIn = "sign-in"
    const val signUp = "sign-up"
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
                    navController.navigate(Screens.signUp)
                }
            )
        }

        composable(Screens.signIn) {
            SignInScreen(
                navBack = {
                    navController.popBackStack()
                },
                navHome = {
                    navController.navigate(Screens.home) {
                        popUpTo(Screens.welcome) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screens.signUp) {
            SignUpScreen(
                navBack = {
                    navController.popBackStack()
                },
                navHome = {
                    navController.navigate(Screens.home) {
                        popUpTo(Screens.welcome) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screens.home) {
            HomeScreen(locationTrackerServiceController)
        }
    }
}
