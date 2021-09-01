package me.smr.weatherforecast.data

import android.content.Context
import androidx.preference.PreferenceManager
import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import me.smr.weatherforecast.models.CityData
import me.smr.weatherforecast.utils.Parser
import org.json.JSONException
import javax.inject.Inject

class WeatherCache @Inject constructor(@ApplicationContext private val context: Context) {

    private val pref = FlowSharedPreferences(PreferenceManager.getDefaultSharedPreferences(context)).getString(KEY_WEATHER_DATA)

    fun getWeatherData(): Flow<List<CityData>> = flow {
        pref.asFlow().onEach {
            emit(Parser.parseWeatherData(it))
        }
    }

    fun getSavedWeatherData(): List<CityData> {
        val json =
            PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_WEATHER_DATA, "")
        return try {
            Parser.parseWeatherData(json)
        } catch (e: JSONException) {
            emptyList()
        }
    }

    fun saveWeatherData(json: String) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(KEY_WEATHER_DATA, json).apply()
    }

    companion object {
        private const val KEY_WEATHER_DATA = "weather_data"
    }

}