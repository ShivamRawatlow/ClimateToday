package com.thelegendofawizard.climatetoday.data.network

import androidx.lifecycle.LiveData
import com.thelegendofawizard.climatetoday.data.entity.CurrentWeatherEntry
import com.thelegendofawizard.climatetoday.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather:LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location:String,
        languageCode:String,
        format:String
    )

}