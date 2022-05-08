package me.smr.weatherforecast.api

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("find?type=like&APPID=$APP_ID")
    suspend fun searchCity(@Query("q") name: String): SearchResponse

    @GET("group?units=metric&APPID=$APP_ID")
    suspend fun getWeatherData(@Query("id") id: String): WeatherResponse

    @GET("forecast/daily?cnt=16&units=metric&APPID=$APP_ID")
    suspend fun fetchForecast(@Query("id") id: String): ForecastResponse

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
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherAPI::class.java)
        }
    }
}