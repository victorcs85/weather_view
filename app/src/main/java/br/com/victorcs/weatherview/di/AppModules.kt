package br.com.victorcs.weatherview.di

import br.com.victorcs.weatherview.core.IDispatchersProvider
import br.com.victorcs.weatherview.core.IDispatchersProviderImpl
import br.com.victorcs.weatherview.core.services.ConnectivityInterceptor
import br.com.victorcs.weatherview.core.services.WifiService
import br.com.victorcs.weatherview.data.entity.WeatherResponse
import br.com.victorcs.weatherview.data.mapper.WeatherResponseMapper
import br.com.victorcs.weatherview.data.source.remote.WeatherAPI
import br.com.victorcs.weatherview.data.source.remote.WeatherRemoteDataSource
import br.com.victorcs.weatherview.data.source.remote.repository.WeatherRepositoryImpl
import br.com.victorcs.weatherview.domain.mapper.DomainMapper
import br.com.victorcs.weatherview.domain.model.Weather
import br.com.victorcs.weatherview.domain.repository.IWeatherRepository
import br.com.victorcs.weatherview.presentation.features.weather.ui.WeatherViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class AppModules : ModuleInitialization() {

    //region Data
    @OptIn(ExperimentalSerializationApi::class)
    private val dataModule = module {
        factory<WeatherAPI> { WeatherRemoteDataSource(client = get()) }

        single<IWeatherRepository> { WeatherRepositoryImpl(
            remoteDataSource = get(),
            mapper = get()
        ) }

        single { Dispatchers.IO }

        single<DomainMapper<WeatherResponse, Weather>> { WeatherResponseMapper() }

        single {
            HttpClient(Android) {
                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                            explicitNulls = false
                        }
                    )
                }
                install(Logging){
                    logger = Logger.DEFAULT
                    level = LogLevel.HEADERS
                }
            }

        }
    }
    //endregion

    //region Core
    private val coreModule = module {
        single { WifiService(context = androidContext()) }
        single { ConnectivityInterceptor(wifiService = get()) }
        single<IDispatchersProvider> { IDispatchersProviderImpl() }
    }
    //endregion

    //region Presentation
    private val presentationModule = module {
        viewModel {
            WeatherViewModel(
                repository = get(),
                dispatchers = get(),
            )
        }
    }
    //endregion

    override fun init(): List<Module> = listOf(
        coreModule,
        dataModule,
        presentationModule
    )
}