package me.smr.weatherforecast.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.smr.weatherforecast.api.WeatherAPI
import me.smr.weatherforecast.data.WeatherDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAPI() = WeatherAPI()

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context) = WeatherDatabase(context)

    @Provides
    fun provideCityDao(db: WeatherDatabase) = db.getCityDao()

}