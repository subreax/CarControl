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

private object Screens {
    const val Welcome = "welcome"
    const val SignIn = "sign-in"
    const val SignUp = "sign-up"
    const val Home = "home"
    const val CarPicker = "car-picker"
    const val CarEditor = "car-editor"
}

@Composable
fun MainNavigation(
    locationTrackerServiceController: LocationTrackerServiceController,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screens.Welcome) {
        composable(Screens.Welcome) {
            WelcomeScreen(
                signInClicked = {
                    navController.navigate(Screens.SignIn)
                },
                signUpClicked = {
                    navController.navigate(Screens.SignUp)
                }
            )
        }

        composable(Screens.SignIn) {
            SignInScreen(
                navBack = {
                    navController.popBackStack()
                },
                navHome = {
                    navController.navigate(Screens.Home) {
                        popUpTo(Screens.Welcome) {
                            inclusive = true
                        }
                    }
                },
                navToCarPicker = {
                    navController.navigate(Screens.CarPicker) {
                        popUpTo(Screens.Welcome) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screens.SignUp) {
            SignUpScreen(
                navBack = {
                    navController.popBackStack()
                },
                navHome = {
                    navController.navigate(Screens.CarPicker) {
                        popUpTo(Screens.Welcome) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screens.Home) {
            HomeScreen(locationTrackerServiceController)
        }

        composable(Screens.CarPicker) {
            val hasParentScreen = navController.previousBackStackEntry != null
            CarPickerScreen(
                showButtonBack = hasParentScreen,
                navBack = {
                    navController.popBackStack()
                },
                navToCarEditor = {
                    navController.navigate("${Screens.CarEditor}?carId=${it.id}")
                },
                navToCarBuilder = {
                    navController.navigate(Screens.CarEditor)
                }
            )
        }

        composable(
            route = "${Screens.CarEditor}?carId={carId}",
            arguments = listOf(navArgument("carId") { defaultValue = "" })
        ) {
            CarEditorScreen(
                navBack = {
                    navController.popBackStack()
                },
                onSave = {
                    navController.navigate(Screens.Home) {
                        popUpTo(Screens.CarPicker) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
