package me.smr.weatherforecast.ui.home

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.smr.weatherforecast.data.Repository
import me.smr.weatherforecast.models.CitySearchResult
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

    init {
        viewModelScope.launch {
            if (repository.getCityIDs().isNotEmpty()) {
                Log.i(TAG, "have IDs")
                // TODO fetch weather data

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

    fun showSearch(){
        _showSearchResults.value = true
        _showWeather.value = false
    }

    fun hideSearch(){
        _showSearchResults.value = false
        _showWeather.value = true
    }

    companion object {
        const val TAG = "HomeViewModel"
    }

}