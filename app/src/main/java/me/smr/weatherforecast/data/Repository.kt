package me.smr.weatherforecast.data

import me.smr.weatherforecast.api.WeatherAPI
import javax.inject.Inject

class Repository @Inject constructor(
    private val cityDAO: CityDAO,
    private val api: WeatherAPI,
) {

    suspend fun getCityIDs(): List<String> = cityDAO.getAllCityIDs()

    suspend fun searchCity(query: String) = api.searchCity(query)

    suspend fun fetchWeatherData(ids: String) = api.getWeatherData(ids)

    suspend fun saveCity(city: CityEntity) = cityDAO.insertCity(city)

    suspend fun fetchForecast(id: String) = api.fetchForecast(id)

}