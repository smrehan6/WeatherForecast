package me.smr.weatherforecast.adapters

import androidx.recyclerview.widget.DiffUtil
import me.smr.weatherforecast.models.CitySearchResult

class SearchDiffCallback : DiffUtil.ItemCallback<CitySearchResult>() {
    override fun areItemsTheSame(oldItem: CitySearchResult, newItem: CitySearchResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CitySearchResult, newItem: CitySearchResult): Boolean {
        return oldItem == newItem
    }
}