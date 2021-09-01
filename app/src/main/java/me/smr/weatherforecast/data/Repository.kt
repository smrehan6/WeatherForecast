package me.smr.weatherforecast.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import me.smr.weatherforecast.api.WeatherAPI
import me.smr.weatherforecast.models.CityData
import javax.inject.Inject

class Repository @Inject constructor(
    private val db: WeatherDatabase,
    private val api: WeatherAPI,
    private val cache: WeatherCache
) {
    suspend fun getWeatherData(): Flow<List<CityData>> {
        // might be further simplified
        val data = cache.getWeatherData()
        if(data.count() == 0){
            val ids = db.getCityDao().getAllCityIDs()
            val jsonString = api.getWeatherData(ids.joinToString(","))
            cache.saveWeatherData(jsonString)
        }
        return data
    }

    suspend fun getCityIDs(): List<String> = db.getCityDao().getAllCityIDs()
}