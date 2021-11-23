package me.smr.weatherforecast.models

import me.smr.weatherforecast.data.CityEntity

data class CitySearchResult(
    val id: String,
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
) {
    fun toEntity(): CityEntity = CityEntity(id, name, country, latitude, longitude)
}