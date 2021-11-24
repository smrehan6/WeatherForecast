package me.smr.weatherforecast.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.smr.weatherforecast.databinding.ForecastListItemBinding
import me.smr.weatherforecast.models.Forecast

class ForecastAdapter : ListAdapter<Forecast, ForecastAdapter.ViewHolder>(ForecastDiffCallback()) {

    class ViewHolder(private val binding: ForecastListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(forecast: Forecast) {
            binding.forecast = forecast
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding = ForecastListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}