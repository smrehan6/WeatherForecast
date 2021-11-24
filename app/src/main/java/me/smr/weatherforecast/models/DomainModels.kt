package me.smr.weatherforecast.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
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

@Parcelize
data class WeatherData(
    val id: String,
    val name: String,
    val temps : String,
    val dt: Long,
    val description: String,
    val icon: String
) : Parcelable