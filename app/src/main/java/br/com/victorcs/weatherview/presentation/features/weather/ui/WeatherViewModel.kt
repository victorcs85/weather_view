package br.com.victorcs.weatherview.presentation.features.weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.victorcs.weatherview.GENERIC_MESSAGE_ERROR
import br.com.victorcs.weatherview.core.IDispatchersProvider
import br.com.victorcs.weatherview.domain.model.Response
import br.com.victorcs.weatherview.domain.model.Weather
import br.com.victorcs.weatherview.domain.repository.IWeatherRepository
import br.com.victorcs.weatherview.presentation.features.weather.intent.WeatherIntent
import br.com.victorcs.weatherview.presentation.features.weather.state.WeatherState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import timber.log.Timber

private val saoPauloCoordinates = -23.5505 to -46.6333
private val amazonasCoordinates = -3.117034 to -60.025780
private const val SP_NAME = "São Paulo"
private const val AM_NAME = "Amazonas"

class WeatherViewModel(
    private val repository: IWeatherRepository,
    private val dispatchers: IDispatchersProvider
) : ViewModel(), KoinComponent {

    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state.asStateFlow()

    fun dispatch(intent: WeatherIntent) {
        when (intent) {
            is WeatherIntent.FetchConcurrently -> fetchConcurrently()
        }
    }

    private fun fetchConcurrently() {
        val coroutineDispatcher: CoroutineDispatcher = dispatchers.io
        viewModelScope.launch(coroutineDispatcher) {
            runCatching {
                _state.value = WeatherState(isLoading = true)

                val cities = listOf(
                    SP_NAME to saoPauloCoordinates,
                    AM_NAME to amazonasCoordinates
                )

                val results = coroutineScope {
                    cities.map { (cityName, coordinates) ->
                        async {
                            cityName to repository.getWeatherData(
                                coordinates.first,
                                coordinates.second
                            )
                        }
                    }.awaitAll()
                }

                val successResults = results.filter { it.second is Response.Success }
                val errorResult = results.firstOrNull { it.second is Response.Error }

                if (successResults.size == cities.size) {
                    val weatherMap = successResults.associate { (cityName, response) ->
                        cityName to (response as Response.Success<Weather>).data
                    }

                    _state.value = WeatherState(
                        weathers = weatherMap
                    )
                    return@launch
                }

                if (errorResult != null) {
                    val errorMessage = (errorResult.second as Response.Error).errorMessage
                    Timber.e(errorMessage)
                    _state.value = WeatherState(
                        error = errorMessage
                    )
                }

            }.onFailure { error ->
                _state.value = WeatherState(error = error.localizedMessage ?: GENERIC_MESSAGE_ERROR)
                Timber.e(error)
            }
        }
    }
}