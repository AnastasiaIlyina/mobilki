package com.example.mobilki.domain.models.auth

import androidx.annotation.StringRes
import com.example.mobilki.R


//Это перечисление AuthScreenPages содержит два варианта: REGISTRATION и LOGIN.
// Метод toStringRes() возвращает ресурс строки, соответствующий каждому варианту перечисления.
// В случае REGISTRATION метод вернет ресурс строки R.string.registration, а в случае LOGIN
// метод вернет ресурс строки R.string.login. Таким образом, этот метод используется для установки
// текста на экранах авторизации в зависимости от того, какой экран отображается.
enum class AuthScreenPages {
    REGISTRATION, LOGIN;

    @StringRes
    fun toStringRes() = when (this) {
        REGISTRATION -> R.string.registration
        LOGIN -> R.string.login
    }
}
