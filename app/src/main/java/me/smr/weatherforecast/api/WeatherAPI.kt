package me.smr.weatherforecast.api

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("find?type=like&APPID=$APP_ID")
    fun find(@Query("q") name: String): Call<ResponseBody>

    @GET("group?units=metric&APPID=$APP_ID")
    fun getCurrentWeather(@Query("id") id: String): Call<ResponseBody>

    @GET("group?units=metric&APPID=$APP_ID")
    suspend fun getWeatherData(@Query("id") id: String): String

    @GET("forecast/daily?cnt=16&units=metric&APPID=$APP_ID")
    fun getForecast(@Query("id") id: String): Call<ResponseBody>

    @GET("weather?APPID=$APP_ID")
    fun getWeatherByLoc(@Query("lat") lat: String, @Query("lon") lon: String): Call<ResponseBody>

    companion object {
        private const val APP_ID = "e1ec7985dbbd4af7f73ae7d3bb99453a"
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

        operator fun invoke(): WeatherAPI {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(logger).build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .build()
                .create(WeatherAPI::class.java)
        }
    }
}