package me.smr.weatherforecast.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CityDAO::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getCityDao(): CityDAO

    companion object {

        operator fun invoke(context: Context): WeatherDatabase {
            return Room.databaseBuilder(context, WeatherDatabase::class.java, "weather.db").build()
        }
    }
}