package com.thelegendofawizard.climatetoday.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.thelegendofawizard.climatetoday.data.network.ConnectivityInterceptor
import com.thelegendofawizard.climatetoday.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "957f3d020ec63ccd5dfe8e4dfe2999bb"
//http://api.weatherstack.com/current?access_key=957f3d020ec63ccd5dfe8e4dfe2999bb&query=New%20York

interface WeatherStackApiService {

    @GET("current")
    fun getCurrentWeather(
        @Query("query") location: String,
        @Query("lang") languageCode: String = "en",
        @Query("unit") format:String = "m"
    ): Deferred<CurrentWeatherResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): WeatherStackApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.weatherstack.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherStackApiService::class.java)

        }
    }
}
