package me.smr.weatherforecast.ui.home

import me.smr.weatherforecast.models.CitySearchResult

interface SearchClickListener {
    fun onSearchItemClicked(item: CitySearchResult)
}