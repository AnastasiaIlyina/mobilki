package com.example.mobilki.presentation.screens.auth_screen.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.mobilki.R
import com.example.mobilki.data.enity.User
import com.example.mobilki.presentation.components.PasswordInput
import com.example.mobilki.presentation.dim.Dimens
import com.example.mobilki.presentation.screens.auth_screen.NavHostRoutes
import com.example.mobilki.ui.theme.typography
import kotlinx.coroutines.launch
import androidx.compose.material.TextField
import com.example.mobilki.data.UserViewModel.UserViewModel
import com.example.mobilki.presentation.components.PhoneInput
import com.example.mobilki.presentation.screens.auth_screen.dialogs.SessionExpiredDialog
import com.example.mobilki.token.SharedPreferences.SessionManagerUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

@Composable
fun ChangeScreen(
    arg: String,
    navController: NavController,
    viewModel: UserViewModel
) {

    val pass = remember { mutableStateOf("") }
    val confirmPass = remember { mutableStateOf("") }



    var user by remember { mutableStateOf<User?>(null) }

    val rememberCoroutineScope = rememberCoroutineScope()

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
//    LaunchedEffect(Unit) {
//        val sessionActive = withContext(Dispatchers.IO) {
//            SessionManagerUtil.isSessionActive(Calendar.getInstance().time, context)
//        }
//        if (!sessionActive) {
//            withContext(Dispatchers.Main) {
//                navController.navigate(NavHostRoutes.Auth.name)
//            }
//        }
//    }

    LaunchedEffect(key1 = arg) {
        viewModel.getUserById(arg)
            .subscribe { fetchedUser ->
                user = fetchedUser
            }
    }

    var name by remember { mutableStateOf(user?.name ?: "") }
    val phoneCodeState = remember { mutableStateOf(user?.phoneCode ?: "") }
    val phoneNumberState = remember { mutableStateOf(user?.phoneNumber ?: "") }

    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.basePadding),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.Paddings.basePadding)
    ) {
        Text(
            text = "Change Profile",
            style = typography.h4,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        user?.let {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimens.Paddings.halfPadding),
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = phoneCodeState.value,
                    onValueChange = { },
                    placeholder = { it.phoneCode?.let { it1 -> Text(text = it1) } },
                    readOnly = true,
                    modifier = Modifier
                        .weight(1f)
                        .then(Dimens.Modifiers.commonModifier)
                )
                TextField(
                    value = phoneNumberState.value,
                    placeholder = { it.phoneNumber?.let { it1 -> Text(text = it1) } },
                    onValueChange = { },
                    readOnly = true,
                    modifier = Modifier
                        .weight(3f)
                        .then(Dimens.Modifiers.commonModifier)
                )
            }

            TextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { it.name?.let { it1 -> Text(text = it1) } },
                modifier = Modifier
                    .fillMaxWidth()
                    .then(Dimens.Modifiers.commonModifier)
            )
        }

        PasswordInput(passState = pass, stringResource(R.string.password))
        PasswordInput(passState = confirmPass, stringResource(R.string.password_confirmation))

        if (pass.value != confirmPass.value) {
            Text(
                text = "Пароли не совпадают",
                style = typography.body1,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(Dimens.Shapes.baseBorderShape)
                    )
                    .padding(Dimens.Paddings.basePadding)
            )
        }


        Button(
            onClick = {
                rememberCoroutineScope.launch {
                    user?.let { userOnClick ->
                        userOnClick.name = name
                        userOnClick.pass = pass.value
                        viewModel.updateUser(userOnClick)
                    }

                    navController.navigate(
                        route = NavHostRoutes.Hello.name + "/$arg",
                    )
                }
            },
            shape = RoundedCornerShape(Dimens.Shapes.baseBorderShape),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.Paddings.halfPadding)
        ) {
            Text(
                text = "Save",
                style = typography.body1,
                color = Color.White
            )
        }
    }
}

