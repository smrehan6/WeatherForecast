package me.smr.weatherforecast.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.smr.weatherforecast.databinding.WeatherListItemBinding
import me.smr.weatherforecast.fragments.HomeFragmentDirections
import me.smr.weatherforecast.models.WeatherData

class WeatherAdapter : ListAdapter<WeatherData, WeatherAdapter.ViewHolder>(WeatherDiffCallback()) {

    class ViewHolder private constructor(private val binding: WeatherListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.weather?.let { data ->
                    navigateToForecast(data, it)
                }
            }
        }

        fun bind(item: WeatherData) {
            binding.weather = item
            binding.executePendingBindings()
        }

        private fun navigateToForecast(weatherData: WeatherData, view: View) {
            val direction = HomeFragmentDirections.actionShowForecast(weatherData.id, weatherData.name)
            view.findNavController().navigate(direction)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WeatherListItemBinding.inflate(layoutInflater, parent, false)
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