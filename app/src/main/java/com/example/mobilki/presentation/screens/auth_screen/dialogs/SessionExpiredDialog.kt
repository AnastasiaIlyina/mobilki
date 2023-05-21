package com.example.mobilki.presentation.screens.auth_screen.dialogs

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.mobilki.presentation.screens.auth_screen.NavHostRoutes

@Composable
fun SessionExpiredDialog(navController: NavController) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = "Сессия истекла") },
        text = { Text(text = "Время жизни сессии истекло, пожалуйста авторизуйтесь.") },
        confirmButton = {
            Button(
                onClick = {
                    navController.navigate(NavHostRoutes.Auth.name)
                }
            ) {
                Text(text = "OK")
            }
        }
    )
}
