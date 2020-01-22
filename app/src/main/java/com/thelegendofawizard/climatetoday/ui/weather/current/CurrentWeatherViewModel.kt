package com.thelegendofawizard.climatetoday.ui.weather.current

import androidx.lifecycle.ViewModel
import com.thelegendofawizard.climatetoday.data.repository.ClimateTodayRepository
import com.thelegendofawizard.climatetoday.internal.UnitSystem
import com.thelegendofawizard.climatetoday.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val climateTodayRepository: ClimateTodayRepository
) : ViewModel() {

    private val unitSystem  = UnitSystem.METRIC //TODO get from settings

    val isMetric:Boolean
    get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        climateTodayRepository.getCurrentWeather()
    }
}
