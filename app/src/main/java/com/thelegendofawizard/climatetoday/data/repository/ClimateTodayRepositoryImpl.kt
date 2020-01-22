package com.thelegendofawizard.climatetoday.data.repository

import androidx.lifecycle.LiveData
import com.thelegendofawizard.climatetoday.data.entity.CurrentWeatherDao
import com.thelegendofawizard.climatetoday.data.entity.CurrentWeatherEntry
import com.thelegendofawizard.climatetoday.data.network.WeatherNetworkDataSource
import com.thelegendofawizard.climatetoday.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*


class ClimateTodayRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ClimateTodayRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }

    }

    override suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            return@withContext currentWeatherDao.getWeather()
            //with context returns a value
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather:CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
        }
    }

    private suspend fun initWeatherData(){
       if(isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
           fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            "Dehradun",
            Locale.getDefault().language,
            "m"
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime):Boolean{

        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}