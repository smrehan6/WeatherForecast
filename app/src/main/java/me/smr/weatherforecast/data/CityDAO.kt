package me.smr.weatherforecast.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: CityEntity)

    @Query("select * from cities")
    fun getAllCities() : Flow<List<CityEntity>>

    @Query("select id from cities")
    fun getAllCityIDs() : Flow<List<String>>

}