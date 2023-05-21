package com.example.mobilki.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.mobilki.R
import com.example.mobilki.presentation.dim.Dimens

@Composable
fun PhoneInput(
    phoneCodeState: MutableState<String>,
    phoneNumberState: MutableState<String>
) {
    // Создание ConstraintLayout, который растягивается на всю доступную ширину
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        // Создание двух "якорей" (refs), на которые будут привязаны поля ввода кода и номера телефона
        val (codeEt, numberEt) = createRefs()

        // Поле ввода кода телефона
        TextField(
            // Значение, которое будет отображаться в поле ввода кода телефона
            value = phoneCodeState.value,
            // Функция-обработчик изменения значения в поле ввода кода телефона
            onValueChange = { phoneCodeState.value = it },
            // Текст-заполнитель поля ввода кода телефона
            placeholder = { Text(text = stringResource(R.string.phone_code_placeholder)) },
            // Модификатор, который задает расположение поля ввода кода телефона
            modifier = Modifier
                .constrainAs(codeEt) {
                    // Привязка верхней границы поля ввода кода телефона к верхней границе родительского элемента
                    top.linkTo(parent.top)
                    // Привязка левой границы поля ввода кода телефона к левой границе родительского элемента
                    start.linkTo(parent.start)
                }
                // Задание ширины поля ввода кода телефона
                .width(Dimens.Sizes.countryCodeFieldWidth)
                // Применение общих модификаторов (границы и скругления)
                .then(Dimens.Modifiers.commonModifier)
        )

        // Поле ввода номера телефона
        TextField(
            // Значение, которое будет отображаться в поле ввода номера телефона
            value = phoneNumberState.value,
            // Функция-обработчик изменения значения в поле ввода номера телефона
            onValueChange = { phoneNumberState.value = it },
            // Текст-заполнитель поля ввода номера телефона
            placeholder = { Text(text = stringResource(R.string.phone_placeholder)) },
            // Модификатор, который задает расположение поля ввода номера телефона
            modifier = Modifier
                .constrainAs(numberEt) {
                    // Привязка верхней границы поля ввода номера телефона к верхней границе родительского элемента
                    top.linkTo(parent.top)
                    // Привязка левой границы поля ввода номера телефона к правой границе поля ввода кода телефона
                    start.linkTo(codeEt.end, margin = Dimens.Paddings.halfPadding)
                    // Привязка правой границы поля ввода номера телефона к правой границе родительского элемента
                    end.linkTo(parent.end)
                    // задаем ширину TextField так, чтобы она заполняла доступное свободное пространство между start и end ограничениями (constraints),
                    width = Dimension.fillToConstraints
                }
                .then(Dimens.Modifiers.commonModifier)
        )
    }
}
