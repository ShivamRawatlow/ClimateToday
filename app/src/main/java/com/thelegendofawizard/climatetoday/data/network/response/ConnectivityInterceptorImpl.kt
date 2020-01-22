package com.thelegendofawizard.climatetoday.data.network.response

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.thelegendofawizard.climatetoday.data.network.ConnectivityInterceptor
import com.thelegendofawizard.climatetoday.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response


class ConnectivityInterceptorImpl(context: Context) :
    ConnectivityInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {

        if(!isOnline())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }



    private fun isOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network)

        if (networkCapabilities == null) {
            Log.d("DataMobile", "No network capabilities found")
            return false
        }
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

    }

    /*private fun isOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        if(!isOnline())
            throw NoConnectivityException()

        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo!=null && networkInfo.isConnected
    }*/
}

