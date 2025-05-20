package br.com.victorcs.weatherview.core

import kotlinx.coroutines.Dispatchers

class IDispatchersProviderImpl : IDispatchersProvider {

    override val io = Dispatchers.IO

    override val main = Dispatchers.Main

    override val default = Dispatchers.Default
}
