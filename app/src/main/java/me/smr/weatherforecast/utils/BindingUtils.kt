package me.smr.weatherforecast.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import me.smr.weatherforecast.R
import me.smr.weatherforecast.models.WeatherData

@BindingAdapter("weatherImage")
fun ImageView.setWeatherImage(item: WeatherData) {
    setImageResource(
        when (item.icon) {
            "01d" -> R.drawable.d1
            "01n" -> R.drawable.n1
            "02d" -> R.drawable.d2
            "02n" -> R.drawable.n2
            "03d", "03n" -> R.drawable.d3
            "04d", "04n" -> R.drawable.d4
            "09d", "09n" -> R.drawable.d9
            "10d" -> R.drawable.d10
            "10n" -> R.drawable.n10
            "11d", "11n" -> R.drawable.d11
            "13d", "13n" -> R.drawable.d13
            "50d", "50n" -> R.drawable.d50
            else -> R.drawable.d10
        }
    )
}