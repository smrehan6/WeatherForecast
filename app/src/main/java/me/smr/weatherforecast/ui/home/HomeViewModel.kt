package me.smr.weatherforecast.ui.home

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.smr.weatherforecast.data.Repository
import me.smr.weatherforecast.models.CitySearchResult
import me.smr.weatherforecast.models.WeatherData
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {

    private val _searchResult = MutableLiveData<List<CitySearchResult>>()
    val searchResult: LiveData<List<CitySearchResult>> = _searchResult

    private val _showSearchResults = MutableLiveData<Boolean>()
    val showSearchResults: LiveData<Boolean> = _showSearchResults

    private val _showWeather = MutableLiveData<Boolean>()
    val showWeather: LiveData<Boolean> = _showWeather

    private val _weatherData = MutableLiveData<List<WeatherData>>()
    val weatherData: LiveData<List<WeatherData>> = _weatherData

    init {
        viewModelScope.launch {
            val cityIDs = repository.getCityIDs()
            if (cityIDs.isNotEmpty()) {
                val weatherDataList = mutableListOf<WeatherData>()
                Log.i(TAG, "ids: $cityIDs")
                val resp = repository.fetchWeatherData(cityIDs.joinToString(","))
                resp.list.forEach {
                    weatherDataList.add(
                        WeatherData(
                            it.id,
                            it.name,
                            it.main.temp.toString(),// TODO
                            it.dt,
                            it.weather[0].description,
                            it.weather[0].icon
                        )
                    )
                }
                _weatherData.postValue(weatherDataList)
                _showWeather.postValue(true)
            } else {
                Log.i(TAG, "NO IDs")
                _showWeather.postValue(false)
            }
        }
    }

    fun searchCity(query: String) {
        viewModelScope.launch {
            val result = mutableListOf<CitySearchResult>()
            val searchResponse = repository.searchCity(query)
            searchResponse.list.forEach {
                // TODO use domain converters
                val city = CitySearchResult(it.id, it.name, it.sys.country, it.coord.lat, it.coord.lon)
                result.add(city)
                // TODO save weather data
            }
            Log.i(TAG, "searchCity: ${result.size}")
            postSearchResults(result)
        }
    }

    private fun postSearchResults(results: List<CitySearchResult>) {
        Log.i(TAG, "postSearchResults: ${results.size}")
        _searchResult.postValue(results)
        showSearch()
    }

    fun showSearch() {
        _showSearchResults.value = true
        _showWeather.value = false
    }

    fun hideSearch() {
        _showSearchResults.value = false
        _showWeather.value = true
    }

    fun onSearchResultClicked(city: CitySearchResult) {
        Log.i(TAG, "onSearchResultClicked: $city")
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveCity(city.toEntity())
        }
    }

    companion object {
        const val TAG = "HomeViewModel"
    }

}