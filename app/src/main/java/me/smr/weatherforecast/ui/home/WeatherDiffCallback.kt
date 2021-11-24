package me.smr.weatherforecast.ui.home

import androidx.recyclerview.widget.DiffUtil
import me.smr.weatherforecast.models.WeatherData

class WeatherDiffCallback : DiffUtil.ItemCallback<WeatherData>() {
    override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
        return oldItem == newItem
    }
}