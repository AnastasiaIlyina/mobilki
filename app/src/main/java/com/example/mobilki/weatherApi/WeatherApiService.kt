package com.example.mobilki.weatherApi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//интерфейс для взаимодействия
//suspend означает, что метод должен быть вызван из корутины
interface WeatherApiService {
    //HTTP запрос get
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): WeatherResponse //мы возвращаем объект WeatherResponse

    @GET("weather")
    suspend fun getCurrentWeatherByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): Response<WeatherResponse>

    @GET("forecast")
    suspend fun getHourlyForecast(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
    ): Response<ForecastResponse>

    @GET("forecast")
    suspend fun getHourlyForecastByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): Response<ForecastResponse>
}

//Интерфейс WeatherApiService является частью библиотеки Retrofit,
// которая предоставляет удобный способ взаимодействия с веб-сервисами RESTful.
// Он облегчает отправку запросов к API и получение ответов с помощью готовых HTTP-клиентов
// и парсинг JSON-данных с использованием библиотеки, такой как Gson.

