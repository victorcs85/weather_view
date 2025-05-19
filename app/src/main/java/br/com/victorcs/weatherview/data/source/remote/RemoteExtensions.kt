package br.com.victorcs.weatherview.data.source.remote

import br.com.victorcs.weatherview.GENERIC_MESSAGE_ERROR
import br.com.victorcs.weatherview.domain.model.ErrorType
import br.com.victorcs.weatherview.domain.model.Response
import timber.log.Timber

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Response<T> {
    return try {
        Response.Success(apiCall.invoke())
    } catch (e: WithoutNetworkException) {
        Timber.e(e)
        Response.Error(e.message ?: GENERIC_MESSAGE_ERROR, ErrorType.NETWORK_ERROR)
    } catch (e: Exception) {
        Timber.e(e)
        Response.Error(GENERIC_MESSAGE_ERROR, ErrorType.GENERIC_ERROR)
    }
}
