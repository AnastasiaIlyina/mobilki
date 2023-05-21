package com.example.mobilki.weatherApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Этот класс WeatherApiClient является клиентским классом,
// который обеспечивает доступ к функциональности Open Weather Map API.
// Он использует WeatherApiService для выполнения запросов к API и обработки ответов.
class WeatherApiClient(private val apiKey: String) {
    private val apiService: WeatherApiService

    //создаем экземпляр Retrofit и настраиваем его для взаимодействия с Open Weather Map API.
    //устанавливаем базовый URL, добавляем конвертер GsonConverterFactory,
    // который преобразует ответы в формате JSON в объекты Kotlin, и создаем экземпляр WeatherApiService.
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create()) //конвертер в json
            .build()

        apiService = retrofit.create(WeatherApiService::class.java)
    }
//Объекты WeatherResponse и ForecastResponse содержат данные о погоде, полученные в ответ на запрос к API.
 //запрос на получение текущей погоды для заданного города
    suspend fun getCurrentWeather(city: String): Result<WeatherResponse> {
        return try {
            val weatherResponse = apiService.getCurrentWeather(city, apiKey)
            Result.Success(weatherResponse)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }
//запрос на получение текущей погоды для заданных координат
    suspend fun getCurrentWeatherByCoordinates(latitude: Double, longitude: Double): Result<WeatherResponse> {
        return try {
            val response = apiService.getCurrentWeatherByCoordinates(latitude, longitude, apiKey)
            if (response.isSuccessful) {
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                    Result.Success(weatherResponse)
                } else {
                    Result.Error("Empty response body")
                }
            } else {
                Result.Error("Request failed: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error occurred")
        }
    }
//запрос на получение прогноза погоды на ближайшие часы для заданного города
    suspend fun getHourlyForecast(city: String): Result<ForecastResponse> {
        return try {
            val response = apiService.getHourlyForecast(city, apiKey)
            if (response.isSuccessful) {
                val forecastResponse = response.body()
                if (forecastResponse != null) {
                    Result.Success(forecastResponse)
                } else {
                    Result.Error("Empty response body")
                }
            } else {
                Result.Error("Request failed: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }
//запрос на получение прогноза погоды на ближайшие часы для заданных координат
    suspend fun getHourlyForecastByCoordinates(
        latitude: Double,
        longitude: Double
    ): Result<ForecastResponse> {
        return try {
            val response = apiService.getHourlyForecastByCoordinates(latitude, longitude, apiKey)
            if (response.isSuccessful) {
                val forecastResponse = response.body()
                if (forecastResponse != null) {
                    Result.Success(forecastResponse)
                } else {
                    Result.Error("Empty response body")
                }
            } else {
                Result.Error("Request failed: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }
}
