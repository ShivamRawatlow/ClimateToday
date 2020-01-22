package com.thelegendofawizard.climatetoday.data.repository

import androidx.lifecycle.LiveData
import com.thelegendofawizard.climatetoday.data.entity.CurrentWeatherEntry


interface ClimateTodayRepository {
    suspend fun getCurrentWeather():LiveData<CurrentWeatherEntry>
}