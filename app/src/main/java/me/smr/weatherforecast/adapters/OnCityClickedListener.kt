package me.smr.weatherforecast.adapters

import me.smr.weatherforecast.models.CityData

interface OnCityClickedListener {
    fun onCityClicked(city: CityData)
}