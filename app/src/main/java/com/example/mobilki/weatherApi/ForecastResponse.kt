package com.example.mobilki.weatherApi

import com.google.gson.annotations.SerializedName
//класс для хранения данных о прогнозе погоды на ближайшие часы, полученных в ответ на запрос к Open Weather Map API.
//аннотациz @SerializedName для указания соответствия между именами свойств класса и полями в JSON-ответе. data class, что означает, что он автоматически создает функции equals(), hashCode(), toString(), а также функцию componentN() для каждого свойства класса. Это упрощает работу с объектами класса и сравнение их значений.

data class ForecastResponse(
    @SerializedName("cod")
    val cod: String,
    @SerializedName("message")
    val message: Double,
    @SerializedName("cnt")
    val count: Int,
    @SerializedName("list")
    val hourlyForecasts: List<WeatherResponse>,
)


