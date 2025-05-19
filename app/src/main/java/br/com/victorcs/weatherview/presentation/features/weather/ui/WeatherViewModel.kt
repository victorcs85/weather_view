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
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

private val SaoPauloCoordinates = -23.5505 to -46.6333
private val AmazonasCoordinates = -3.117034 to -60.025780

//private enum class SaoPauloCoordinates(val value: Double) {
//    LATITUDE(-23.5505),
//    LONGITUDE(-46.6333)
//}
//private enum class AmazonasCoordinates(val value: Double) {
//    LATITUDE(-3.117034),
//    LONGITUDE(-60.025780)
//}

//private object AmazonasCoordinates {
//    const val LATITUDE = -3.117034
//    const val LONGITUDE = -60.025780
//}

class WeatherViewModel(
    private val repository: IWeatherRepository,
    private val dispatchers: IDispatchersProvider,
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state.asStateFlow()

    fun dispatch(intent: WeatherIntent) {
        when (intent) {
            is WeatherIntent.FetchConcurrently -> fetchConcurrently()
        }
    }

    private fun fetchConcurrently() {
        viewModelScope.launch(dispatchers.io) {
            runCatching {
                _state.value = WeatherState(isLoading = true)

                val cities = listOf(
                    SaoPauloCoordinates,
                    AmazonasCoordinates
                )

                val results = coroutineScope {

                    cities.map { coordinates ->
                        async {
                            repository.getWeatherData(
                                coordinates.first,
                                coordinates.second
                            )
                        }
                    }.awaitAll()
                }

                if (results.all { it is Response.Success }) {
                    _state.value = WeatherState(
                        weathers =
                            results.map { (it as Response.Success<Weather>).data }
                    )
                    return@launch
                }

                if (results.any { it is Response.Error }) {
                    _state.value = WeatherState(
                        error = (results.first { it is Response.Error } as Response.Error).errorMessage
                    )
                    return@launch
                }

            }.onFailure { error ->
                _state.value = WeatherState(error = error.localizedMessage ?: GENERIC_MESSAGE_ERROR)
                Timber.e(error)
            }
        }
    }
}