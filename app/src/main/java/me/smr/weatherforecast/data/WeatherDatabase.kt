package me.smr.weatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CityDAO::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getCityDao(): CityDAO
}