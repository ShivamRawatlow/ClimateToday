package com.thelegendofawizard.climatetoday

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.thelegendofawizard.climatetoday.data.WeatherStackApiService
import com.thelegendofawizard.climatetoday.data.db.ClimateTodayDatabase
import com.thelegendofawizard.climatetoday.data.network.ConnectivityInterceptor
import com.thelegendofawizard.climatetoday.data.network.WeatherNetworkDataSource
import com.thelegendofawizard.climatetoday.data.network.WeatherNetworkDataSourceImpl
import com.thelegendofawizard.climatetoday.data.network.response.ConnectivityInterceptorImpl
import com.thelegendofawizard.climatetoday.data.repository.ClimateTodayRepository
import com.thelegendofawizard.climatetoday.data.repository.ClimateTodayRepositoryImpl
import com.thelegendofawizard.climatetoday.ui.weather.current.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ClimateTodayApplication:Application(),KodeinAware{

    override val kodein = Kodein.lazy {
        import(androidXModule(this@ClimateTodayApplication))

        bind() from singleton { ClimateTodayDatabase.invoke(instance()) }
        bind() from singleton { instance<ClimateTodayDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherStackApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ClimateTodayRepository>() with singleton { ClimateTodayRepositoryImpl(instance(),instance()) }
        bind() from provider {CurrentWeatherViewModelFactory(instance())}

    }

    override fun onCreate(){
        super.onCreate()
        AndroidThreeTen.init(this)
    }

}