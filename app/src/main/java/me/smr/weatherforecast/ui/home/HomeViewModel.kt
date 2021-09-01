package me.smr.weatherforecast.ui.home

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import me.smr.weatherforecast.data.Repository
import me.smr.weatherforecast.models.CityData
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    //private val savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {

    lateinit var data: Flow<List<CityData>>
    var hasData : Boolean = false
    var buttonVisiblity = if(hasData) View.GONE else View.VISIBLE

    init {
        viewModelScope.launch {
            if (repository.getCityIDs().isNotEmpty()) {
                hasData = true
                data = repository.getWeatherData()
            } else{
                hasData = false
            }
        }
    }

}