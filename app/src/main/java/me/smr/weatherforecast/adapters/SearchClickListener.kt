package me.smr.weatherforecast.adapters

import me.smr.weatherforecast.models.CitySearchResult

interface SearchClickListener {
    fun onSearchItemClicked(item: CitySearchResult)
}