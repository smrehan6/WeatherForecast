package me.smr.weatherforecast.api

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
    val weather: List<NetworkWeather>
)

data class Sys(var country: String)

data class Coord(val lat: Double, val lon: Double)

data class NetworkWeather(
    val id: String,
    val main: String,
    val description: String,
    val icon: String
)
