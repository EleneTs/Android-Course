package com.etsiramua.weatherapp.service

import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class WeatherApiClient(apiKey: String, unitMetric: String, baseUrl: String) {

    private val apiKey: String = apiKey

    private val unitMetric: String = unitMetric

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherService: WeatherService = retrofit.create(WeatherService::class.java)

    fun getCurrentWeather(cityName: String, callback: (CurrentWeatherResponse?) -> Unit) {
        val call = weatherService.getCurrentWeather(cityName, apiKey, unitMetric)
        call.enqueue(object : Callback<CurrentWeatherResponse> {
            override fun onResponse(
                call: Call<CurrentWeatherResponse>,
                response: Response<CurrentWeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val currentWeatherResponse = response.body()
                    callback(currentWeatherResponse)
                }
            }

            override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
                callback(null)
            }
        })

    }

    fun getForecastWeather(cityName: String, callback: (HourlyWeatherResponse?) -> Unit) {
        val call = weatherService.getHourlyWeather(cityName, apiKey, unitMetric)
        call.enqueue(object : Callback<HourlyWeatherResponse> {
            override fun onResponse(
                call: Call<HourlyWeatherResponse>,
                response: Response<HourlyWeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val hourlyWeatherResponse = response.body()
                    callback(hourlyWeatherResponse)
                }
            }

            override fun onFailure(call: Call<HourlyWeatherResponse>, t: Throwable) {
                callback(null)
            }
        })

    }


}

interface WeatherService {
    @GET("weather")
    fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("units") unit: String,
    ): Call<CurrentWeatherResponse>

    @GET("forecast")
    fun getHourlyWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("units") unit: String,
    ): Call<HourlyWeatherResponse>
}

data class CurrentWeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val rain: Rain,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val id: Long,
    val name: String,
    val cod: Int
)

data class Forecast(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val sys: Sys,
    val dt_txt: String,
    val snow: Snow?,
    val rain: Rain?
)

data class HourlyWeatherResponse(
    val cod: Int,
    val message: Int,
    val cnt: Int,
    val list: List<Forecast>,
    val city: City
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
    val humidity: Int,
    val sea_level: Int,
    val grnd_level: Int
)

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double
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

data class Rain(
    val threeHour: Double
)

data class Snow(
    val threeHour: Double
)

data class City(
    val id: Long,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)
