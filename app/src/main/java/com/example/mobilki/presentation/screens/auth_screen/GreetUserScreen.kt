package com.example.mobilki.presentation.screens.auth_screen

import android.app.AlertDialog
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobilki.data.UserViewModel.UserViewModel
import com.example.mobilki.data.enity.User
import com.example.mobilki.presentation.dim.Dimens
import com.example.mobilki.presentation.screens.auth_screen.adminScreens.UserRow
import com.example.mobilki.token.SharedPreferences.SessionManagerUtil
import com.example.mobilki.ui.theme.typography
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import com.example.mobilki.presentation.screens.auth_screen.dialogs.SessionExpiredDialog
import com.example.mobilki.presentation.screens.auth_screen.weatherScreens.WeatherAppUserScreen
import com.example.mobilki.ui.theme.Teal200

@Composable
fun GreetUserScreen(arg: String, navController: NavController, viewModel: UserViewModel) {
    val rememberCoroutineScope = rememberCoroutineScope()
    var user: User? by remember { mutableStateOf(null) }
    var userList: List<User> by remember { mutableStateOf(emptyList()) }


    val context = LocalContext.current
    val sessionActive = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val isActive = withContext(Dispatchers.IO) {
            SessionManagerUtil.isSessionActive(Calendar.getInstance().time, context)
        }
        sessionActive.value = isActive
    }

    if (!sessionActive.value) {
        SessionExpiredDialog(navController)
    }
    LaunchedEffect(key1 = true) {
        viewModel.getUserById(arg)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                user = it
            }, {
            })
    }

    LaunchedEffect(key1 = true) {
        viewModel.loadAllUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ loadedUserList ->
                userList = loadedUserList.filter { user ->
                    user.uid != arg.toInt()
                }
            }, {
            })
    }


    Column(
        modifier = Modifier.fillMaxSize()
            .background(Teal200)


    ) {

        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Button(
                onClick = {
                    rememberCoroutineScope.launch {
                        navController.navigate(
                            route = NavHostRoutes.Change.name + "/${arg}"
                        )
                    }
                },
                shape = RoundedCornerShape(Dimens.Shapes.baseBorderShape),
            ) {
                Text(
                    text = "Hello ${user?.name}",
                    style = typography.body1,
                    color = Color.White,
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    rememberCoroutineScope.launch {
                        SessionManagerUtil.endUserSession(context)
                        navController.navigate(NavHostRoutes.Auth.name)
                    }
                },
                shape = RoundedCornerShape(Dimens.Shapes.baseBorderShape),
            ) {
                Text(
                    text = "Выйти",
                    style = typography.body1,
                    color = Color.White
                )
            }
        }



//        Text(
//            text = "WELCOME PAGE",
//            style = typography.h6,
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        )

        // Блок для панели администратора
        if (user?.isAdmin == true) {
            Text(
                text = "Список пользователей:",
                style = typography.subtitle1,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(userList) { item ->
                    UserRow(
                        user = item,
                        onMakeAdminClicked = {
                            rememberCoroutineScope.launch {
                                viewModel.updateUser(item.copy(isAdmin = true))
                            }
                        },
                        onRemoveAdminClicked = {
                            rememberCoroutineScope.launch {
                                viewModel.updateUser(item.copy(isAdmin = false))
                            }
                        }
                    )
                }
            }
        } else {
            WeatherAppUserScreen();
        }
    }
}









