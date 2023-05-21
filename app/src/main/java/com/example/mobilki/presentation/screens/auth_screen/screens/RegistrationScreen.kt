package com.example.mobilki.presentation.screens.auth_screen.screens

import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.room.Room
import com.example.mobilki.R
import com.example.mobilki.data.AppDatabase
import com.example.mobilki.data.UserViewModel.UserViewModel
import com.example.mobilki.data.enity.User
import com.example.mobilki.presentation.components.PasswordInput
import com.example.mobilki.presentation.components.PhoneInput
import com.example.mobilki.presentation.dim.Dimens
import com.example.mobilki.ui.theme.typography
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(viewModel: UserViewModel) {
    val code = remember { mutableStateOf("") }
    val number = remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    val pass = remember { mutableStateOf("") }
    val confirmPass = remember { mutableStateOf("") }

    val applicationContext = LocalContext.current

    val rememberCoroutineScope = rememberCoroutineScope();
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.basePadding),
        modifier = Modifier.fillMaxSize()
    ) {
        PhoneInput(phoneCodeState = code, phoneNumberState = number)

        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text(text = stringResource(R.string.name)) },
            modifier = Modifier.fillMaxWidth()
                .then(Dimens.Modifiers.commonModifier)
        )

        PasswordInput(passState = pass, stringResource(R.string.password))

        PasswordInput(passState = confirmPass, stringResource(R.string.password_confirmation))

        var job: Job? by remember {
            mutableStateOf(null)
        }
        Button(
            onClick = {
                job = rememberCoroutineScope.launch {
                    if (code.value == "" || number.value == "" || name == "" || pass.value == "" || confirmPass.value == "") {
                        Toast.makeText(
                            applicationContext,
                            "Все поля должны быть заполнены",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    }
                    if (pass.value != confirmPass.value) {
                        Toast.makeText(
                            applicationContext,
                            "Поле Pass и ConfirmPass имеют разные значения",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    }
                    val userExists  = viewModel.checkIfUserExistsByNumber(number.value)
                    if (userExists ) {
                        Toast.makeText(
                            applicationContext,
                            "Пользователь с таким номером уже существует",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    }

                    viewModel.insertUsers(
                        User(
                            uid = 0,
                            phoneCode = code.value,
                            phoneNumber = number.value,
                            name = name,
                            pass = pass.value
                        )
                    )

                    Toast.makeText(
                        applicationContext,
                        "Теперь перейдите на страницу входа в систему для авторизации.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            },
            shape = RoundedCornerShape(Dimens.Shapes.baseBorderShape),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.Paddings.halfPadding)
        ) {
            Text(
                text = stringResource(id = R.string.continue_string),
                style = typography.body1,
                color = Color.White,
            )
        }
    }
}
