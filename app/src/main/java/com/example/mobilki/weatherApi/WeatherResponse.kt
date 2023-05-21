package com.example.mobilki.weatherApi

//Это класс который представляет собой модель данных для парсинга JSON ответа
data class WeatherResponse(
    val coord: Coord, //координаты местоположения
    val weather: List<Weather>, //список погодных условий
    val main: Main, //информация о температуре и влажности
    val visibility: Int, // видимость
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long, //дата и время
    val sys: Sys,
    val timezone: Int,
    val id: Long,
    val name: String,
    val cod: Int
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Double,
    val deg: Int
)

data class Clouds(
    val all: Int
)

data class Sys(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

