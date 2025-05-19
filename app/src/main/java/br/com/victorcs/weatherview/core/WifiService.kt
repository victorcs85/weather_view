package br.com.victorcs.weatherview.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class WifiService(context: Context) {

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isOnline(): Boolean {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR).orFalse() ||
                capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI).orFalse() ||
                capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET).orFalse()
    }
}