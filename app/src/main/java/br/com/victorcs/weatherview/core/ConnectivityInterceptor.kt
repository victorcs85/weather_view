package br.com.victorcs.weatherview.core

import br.com.victorcs.weatherview.data.source.remote.WithoutNetworkException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(private val wifiService: WifiService) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (wifiService.isOnline().not()) {
            throw WithoutNetworkException()
        }

        val request = chain.request()

        return chain.proceed(request)
    }
}