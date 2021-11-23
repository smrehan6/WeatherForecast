package me.smr.weatherforecast.data

import me.smr.weatherforecast.api.SearchResponse
import me.smr.weatherforecast.api.WeatherAPI
import javax.inject.Inject

class Repository @Inject constructor(
    private val cityDAO: CityDAO,
    private val api: WeatherAPI,
) {

    suspend fun getCityIDs(): List<String> = cityDAO.getAllCityIDs()

    suspend fun searchCity(query: String): SearchResponse {
        // TODO error handling
        return api.searchCity(query)
    }

    suspend fun saveCity(city: CityEntity) = cityDAO.insertCity(city)

    companion object {
        const val TAG = "Repository"
    }
}