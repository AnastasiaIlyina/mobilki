package com.example.mobilki.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.mobilki.R
import com.example.mobilki.presentation.dim.Dimens

@Composable
fun PasswordInput(
    passState: MutableState<String>, //состояние пароля в виде изменяемого значения (MutableState).
    placeholder_text: String,//текст подсказки в поле ввода.
) {
    var isPassVisible by remember { mutableStateOf(false) }
    TextField(
        value = passState.value,
        onValueChange = { passState.value = it },
        placeholder = { Text(text = placeholder_text) },
        visualTransformation = if (isPassVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
        trailingIcon = { EyeIcon { isPassVisible = !isPassVisible } },
        modifier = Modifier
            .fillMaxWidth()
            .then(Dimens.Modifiers.commonModifier)
    )
}

@Composable
private fun EyeIcon(onClick: () -> Unit) {
    Icon(
        painter = painterResource(id = R.drawable.ic_eye),
        contentDescription = null,
        modifier = Modifier.clickable { onClick() }
    )
}