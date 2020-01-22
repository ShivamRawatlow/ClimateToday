package com.thelegendofawizard.climatetoday.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thelegendofawizard.climatetoday.data.WeatherStackApiService
import com.thelegendofawizard.climatetoday.internal.NoConnectivityException
import com.thelegendofawizard.climatetoday.data.network.response.CurrentWeatherResponse

class WeatherNetworkDataSourceImpl(
    private val weatherStackApiService: WeatherStackApiService
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String,
        format: String
    ) {
        try {
            val fetcherCurrentWeather = weatherStackApiService
                .getCurrentWeather(location,languageCode,format)
                .await()
            _downloadedCurrentWeather.postValue(fetcherCurrentWeather)
        }
        catch (e: NoConnectivityException){
            Log.e("Connectivity","No Internet Connection", e)
        }
    }
}