package com.tasneembohra.slackassignment.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(context: Context) : Interceptor {
    private val connectivityManager: ConnectivityManager? = context.getSystemService()

    @Throws(NoInternetException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val capabilities: NetworkCapabilities =
            connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?: throw NoInternetException

        if (!capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            && !capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        ) throw NoInternetException

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}

object NoInternetException: RuntimeException()