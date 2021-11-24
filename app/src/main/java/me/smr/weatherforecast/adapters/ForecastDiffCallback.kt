package me.smr.weatherforecast.adapters

import androidx.recyclerview.widget.DiffUtil
import me.smr.weatherforecast.models.Forecast

class ForecastDiffCallback : DiffUtil.ItemCallback<Forecast>() {
    override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
        return oldItem == newItem
    }
}