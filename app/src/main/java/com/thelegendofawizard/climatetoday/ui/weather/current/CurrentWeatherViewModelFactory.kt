package com.thelegendofawizard.climatetoday.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thelegendofawizard.climatetoday.data.repository.ClimateTodayRepository

@Suppress("UNCHECKED_CAST")
class CurrentWeatherViewModelFactory(
    private val climateTodayRepository: ClimateTodayRepository
):ViewModelProvider.NewInstanceFactory() {

    override fun<T:ViewModel?> create(modelClass:Class<T>):T{

        return CurrentWeatherViewModel(climateTodayRepository) as T
    }
}