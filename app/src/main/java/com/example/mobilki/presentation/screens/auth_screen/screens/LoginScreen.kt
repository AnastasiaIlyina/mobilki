package com.example.mobilki.presentation.screens.auth_screen.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.mobilki.R
import com.example.mobilki.data.UserViewModel.UserViewModel
import com.example.mobilki.presentation.components.PasswordInput
import com.example.mobilki.presentation.components.PhoneInput
import com.example.mobilki.presentation.dim.Dimens
import com.example.mobilki.presentation.screens.auth_screen.NavHostRoutes
import com.example.mobilki.token.SharedPreferences.SessionManagerUtil
import com.example.mobilki.token.SharedPreferences.SessionManagerUtil.printToken
import com.example.mobilki.ui.theme.typography
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

@SuppressLint("CheckResult")
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: UserViewModel,
) {
    val code = remember { mutableStateOf("") }
    val number = remember { mutableStateOf("") }
    val pass = remember { mutableStateOf("") }

    val applicationContext = LocalContext.current


    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.basePadding),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.Paddings.basePadding)
    ) {
        PhoneInput(phoneCodeState = code, phoneNumberState = number)

        PasswordInput(passState = pass, stringResource(R.string.password))

        var isRememberMe by remember { mutableStateOf(false) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.Paddings.halfPadding),
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(checked = isRememberMe, onCheckedChange = { isRememberMe = !isRememberMe })

            Text(
                text = stringResource(id = R.string.keep_me_logged_in),
                style = typography.body1
            )

            TextButton(
                onClick = {
                    Toast.makeText(applicationContext, "Forgot", Toast.LENGTH_SHORT).show()
                },
            ) {
                Text(
                    text = stringResource(id = R.string.forgotPassword),
                    style = typography.body1,
                    color = Color.Blue
                )
            }
        }

        Button(
            onClick = {
                viewModel.getUserByNumber(number.value)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ user ->
                        if (user.pass == pass.value) {
                            SessionManagerUtil.startUserSession(applicationContext, 60)
                            printToken(applicationContext)
                            navController.navigate(NavHostRoutes.Hello.name + "/${user.uid}")
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Неверный пароль",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }, {
                        Toast.makeText(
                            applicationContext,
                            "Пользователь не найден",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
            },
            shape = RoundedCornerShape(Dimens.Shapes.baseBorderShape),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.Paddings.halfPadding)
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = typography.body1,
                color = Color.White
            )
        }
    }
}


//        Button(
//            onClick = {
//                viewModel.getUserByNumber(number.value)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe({ user ->
//                        if (user.pass == pass.value) {
//                            navController.navigate(NavHostRoutes.Hello.name + "/${user.uid}")
//                        } else {
//                            Toast.makeText(applicationContext, "Неверный пароль", Toast.LENGTH_SHORT).show()
//                        }
//                    }, {
//                        Toast.makeText(applicationContext, "Пользователь не найден", Toast.LENGTH_SHORT).show()
//                    })
//            },
//
//        shape = RoundedCornerShape(Dimens.Shapes.baseBorderShape),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = Dimens.Paddings.halfPadding)
//        ) {
//            Text(
//                text = stringResource(id = R.string.login),
//                style = typography.body1,
//                color = Color.White
//            )
//        }
//    }
//}
