package com.thelegendofawizard.climatetoday.data.network.response

import com.google.gson.annotations.SerializedName
import com.thelegendofawizard.climatetoday.data.entity.CurrentWeatherEntry
import com.thelegendofawizard.climatetoday.data.entity.Location
import com.thelegendofawizard.climatetoday.data.entity.Request


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,

    val location: Location,

    val request: Request
)