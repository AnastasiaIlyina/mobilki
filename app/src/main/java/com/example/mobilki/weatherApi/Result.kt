package com.example.mobilki.weatherApi
//класс для возврата результата запроса к API
//механизм селедования (sealed) классов- его подклассы могут быть определены только в файле, где определен Result.
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}

