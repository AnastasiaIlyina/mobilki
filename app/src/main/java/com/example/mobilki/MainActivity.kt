package com.example.mobilki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobilki.data.UserViewModel.UserViewModel
import com.example.mobilki.presentation.screens.auth_screen.AuthPageScreen
import com.example.mobilki.presentation.screens.auth_screen.GreetUserScreen
import com.example.mobilki.presentation.screens.auth_screen.NavHostRoutes
import com.example.mobilki.presentation.screens.auth_screen.screens.ChangeScreen
import com.example.mobilki.token.SharedPreferences.SessionManagerUtil
import com.example.mobilki.ui.theme.Mobile3Theme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentTime = Calendar.getInstance().time
        val sessionActive = SessionManagerUtil.isSessionActive(currentTime, applicationContext)

        setContent {
            Mobile3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val viewModel by viewModels<UserViewModel>()

                    NavHost(
                        navController = navController,
                        startDestination = NavHostRoutes.Auth.name,
                    ) {
                        composable(route = NavHostRoutes.Auth.name) {
                            AuthPageScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(
                            route = NavHostRoutes.Hello.name + "/{arg}",
                            arguments = listOf(navArgument("arg") { type = NavType.StringType })
                        ) {
                            GreetUserScreen(
                                arg = it.arguments?.getString("arg") ?: "", navController, viewModel
                            )
                        }
                        composable(
                            route = NavHostRoutes.Change.name + "/{arg}" ,
                            arguments = listOf(navArgument("arg") { type = NavType.StringType }),
                        ){
                            ChangeScreen(
                                arg = it.arguments?.getString("arg") ?: "", navController, viewModel
                            )
                        }
                    }
                    if (!sessionActive) {
                        navController.navigate(NavHostRoutes.Auth.name)
                    } else {
                        navController.navigate(NavHostRoutes.Hello.name)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SessionManagerUtil.endUserSession(applicationContext)
    }

}
