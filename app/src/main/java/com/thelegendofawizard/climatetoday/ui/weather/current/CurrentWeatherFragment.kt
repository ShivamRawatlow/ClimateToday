package com.thelegendofawizard.climatetoday.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.storage.StorageVolume
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.thelegendofawizard.climatetoday.R
import com.thelegendofawizard.climatetoday.data.WeatherStackApiService
import com.thelegendofawizard.climatetoday.data.network.WeatherNetworkDataSource
import com.thelegendofawizard.climatetoday.data.network.WeatherNetworkDataSourceImpl
import com.thelegendofawizard.climatetoday.data.network.response.ConnectivityInterceptorImpl
import com.thelegendofawizard.climatetoday.internal.glide.GlideApp
import com.thelegendofawizard.climatetoday.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(),KodeinAware {

    override val kodein by kodein()


    private val viewModelFactory :CurrentWeatherViewModelFactory by instance()



    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        bindUI()

    }


    // YAha pe choda hai
    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(this@CurrentWeatherFragment, Observer {

            if(it == null)return@Observer

            group_loading.visibility = View.GONE
            updateLocation("Delhi")
            updateDateToToday()
            updateTemperatures(it.temperature,it.feelslike)
            updatePrecipitation(it.precip)
            updateWind(it.windDir,it.windSpeed)
            updateVisibility(it.visibility)

            GlideApp.with(this@CurrentWeatherFragment)
                .load(it.weatherIcons.first())
                .into(imageView_condition_icon)
        })
    }

    private fun updateLocation(location:String){
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if (viewModel.isMetric) metric else imperial
    }


    private fun updateTemperatures(temperature: Int, feelsLike: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_feels_like_temperature.text = "Feels like $feelsLike$unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        textView_precipitation.text = "Preciptiation: $precipitationVolume $unitAbbreviation"
    }

    private fun updateWind(windDirection: String, windSpeed: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        textView_wind.text = "Wind: $windDirection, $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi.")
        textView_visibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }

}
