package com.subreax.hackaton

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.subreax.hackaton.service.LocationTrackerServiceController
import com.subreax.hackaton.ui.WelcomeScreen
import com.subreax.hackaton.ui.careditor.CarEditorScreen
import com.subreax.hackaton.ui.carpicker.CarPickerScreen
import com.subreax.hackaton.ui.home.HomeScreen
import com.subreax.hackaton.ui.signin.SignInScreen
import com.subreax.hackaton.ui.signup.SignUpScreen
import timber.log.Timber

private object Screens {
    const val welcome = "welcome"
    const val signIn = "sign-in"
    const val signUp = "sign-up"
    const val home = "home"
    const val car_picker = "car-picker"
    const val car_editor = "car-editor"
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
                },
                navToCarPicker = {
                    navController.navigate(Screens.car_picker) {
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
                    navController.navigate(Screens.car_picker) {
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

        composable(Screens.car_picker) {
            val hasParentScreen = navController.previousBackStackEntry != null
            Timber.d("$hasParentScreen")
            CarPickerScreen(
                showButtonBack = hasParentScreen,
                navBack = {
                    navController.popBackStack()
                },
                navToCarEditor = {
                    navController.navigate("${Screens.car_editor}?carId=${it.id}")
                },
                navToCarBuilder = {
                    navController.navigate(Screens.car_editor)
                }
            )
        }

        composable(
            route = "${Screens.car_editor}?carId={carId}",
            arguments = listOf(navArgument("carId") { defaultValue = "" })
        ) {
            CarEditorScreen(
                navBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
