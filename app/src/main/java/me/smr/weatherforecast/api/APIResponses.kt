package me.smr.weatherforecast.api

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    val message: String,
    val cod: String,
    val count: Int,
    val list: List<NetworkCity>
)

data class NetworkCity(
    val id: String,
    val name: String,
    val coord: Coord,
    val dt: Long,
    val sys: Sys,
    val main:Main,
    val weather: List<NetworkWeather>
)

data class Sys(val country: String)

data class Main (
    @SerializedName("temp"       ) var temp      : Double,
    @SerializedName("feels_like" ) var feelsLike : Double,
    @SerializedName("temp_min"   ) var tempMin   : Double,
    @SerializedName("temp_max"   ) var tempMax   : Double,
    @SerializedName("pressure"   ) var pressure  : Int,
    @SerializedName("sea_level"  ) var seaLevel  : Int,
    @SerializedName("grnd_level" ) var grndLevel : Int,
    @SerializedName("humidity"   ) var humidity  : Int
)

data class Coord(val lat: Double, val lon: Double)

data class NetworkWeather(
    val id: String,
    val main: String,
    val description: String,
    val icon: String
)

data class WeatherResponse(
    @SerializedName("cnt") val count: Int,
    val list: List<NetworkCity>
)
