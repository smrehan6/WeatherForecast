package me.smr.weatherforecast.viewmodels

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.smr.weatherforecast.data.Repository
import me.smr.weatherforecast.fragments.ForecastFragment
import me.smr.weatherforecast.models.Forecast
import me.smr.weatherforecast.utils.Result
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {

    private val _data = MutableLiveData<Result<List<Forecast>>>()
    val data: LiveData<Result<List<Forecast>>> = _data

    val isLoading: LiveData<Boolean> = Transformations.map(_data) { result ->
        result is Result.Loading
    }

    init {
        _data.postValue(Result.Loading)
        val cityId: String? = savedStateHandle[ForecastFragment.ARG_CITY_ID]
        cityId?.let {
            viewModelScope.launch {
                try {
                    val response = repository.fetchForecast(cityId)
                    val list = mutableListOf<Forecast>()
                    response.list.forEach {
                        list.add(Forecast().apply {
                            this.date = it.dt
                            this.temp = "${it.temp.min} - ${it.temp.max}"
                            this.img = it.weather[0].icon
                            this.weather = it.weather[0].description
                        })
                    }
                    _data.postValue(Result.Value(list))
                } catch (e: Exception) {
                    e.printStackTrace()
                    _data.postValue(Result.Error(e))
                }
            }
        } ?: run {
            _data.postValue(Result.Error(IllegalArgumentException("${ForecastFragment.ARG_CITY_ID} not found")))
        }

    }

    companion object {
        const val TAG = "ForecastViewModel"
    }

}