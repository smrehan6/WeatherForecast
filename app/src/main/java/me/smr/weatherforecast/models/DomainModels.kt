package me.smr.weatherforecast.models

data class CitySearchResult(
    val id: String,
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)