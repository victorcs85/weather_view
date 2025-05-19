package br.com.victorcs.weatherview.data.source.remote

import br.com.victorcs.weatherview.NETWORK_MESSAGE_ERROR
import java.io.IOException

class WithoutNetworkException : IOException(NETWORK_MESSAGE_ERROR)