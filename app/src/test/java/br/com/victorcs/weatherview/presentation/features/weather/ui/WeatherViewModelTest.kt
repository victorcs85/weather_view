package br.com.victorcs.weatherview.presentation.features.weather.ui

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import br.com.victorcs.weatherview.base.BaseViewModelTest
import br.com.victorcs.weatherview.base.CoroutineTestRule
import br.com.victorcs.weatherview.domain.model.Response
import br.com.victorcs.weatherview.domain.model.Weather
import br.com.victorcs.weatherview.domain.repository.IWeatherRepository
import br.com.victorcs.weatherview.presentation.features.weather.intent.WeatherIntent
import br.com.victorcs.weatherview.shared.test.DataMockTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
@SmallTest
class WeatherViewModelTest : BaseViewModelTest() {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private val repository = mockk<IWeatherRepository>()

    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun givenLatLon_whenFetchConcurrently_thenReturnSuccessfullyData() =
        runTest {
            viewModel = WeatherViewModel(repository, testDispatcherProvider)

            val spWeather = mockk<Weather>()
            val amWeather = mockk<Weather>()

            coEvery {
                repository.getWeatherData(DataMockTest.SP_LAT, DataMockTest.SP_LON)
            } returns Response.Success(spWeather)

            coEvery {
                repository.getWeatherData(DataMockTest.AM_LAT, DataMockTest.AM_LON)
            } returns Response.Success(amWeather)

            viewModel.dispatch(WeatherIntent.FetchConcurrently)

            advanceUntilIdle()

            viewModel.state.test {
                val finalState = awaitItem()
                assertEquals(
                    mapOf(
                        DataMockTest.SP_NAME to spWeather,
                        DataMockTest.AM_NAME to amWeather
                    ),
                    finalState.weathers
                )
                assertNull(finalState.error)
                assertFalse(finalState.isLoading)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun givenErrorData_whenFetchConcurrently_thenReturnError() = runTest {
        viewModel = WeatherViewModel(repository, testDispatcherProvider)

        val spWeather = mockk<Weather>()

        coEvery {
            repository.getWeatherData(DataMockTest.SP_LAT, DataMockTest.SP_LON)
        } returns Response.Success(spWeather)

        coEvery {
            repository.getWeatherData(DataMockTest.AM_LAT, DataMockTest.AM_LON)
        } returns Response.Error(DataMockTest.ERROR_MESSAGE)

        viewModel.dispatch(WeatherIntent.FetchConcurrently)

        advanceUntilIdle()

        viewModel.state.test {
            val finalState = awaitItem()
            assertEquals(DataMockTest.ERROR_MESSAGE, finalState.error)
            assertTrue(finalState.weathers.isEmpty())
            assertFalse(finalState.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun givenException_whenFetchConcurrently_thenReturnError() = runTest {
        viewModel = WeatherViewModel(repository, testDispatcherProvider)

        coEvery {
            repository.getWeatherData(any(), any())
        } throws RuntimeException(DataMockTest.ERROR_MESSAGE)

        viewModel.dispatch(WeatherIntent.FetchConcurrently)

        advanceUntilIdle()

        viewModel.state.test {
            val finalState = awaitItem()
            assertEquals(DataMockTest.ERROR_MESSAGE, finalState.error)
            assertTrue(finalState.weathers.isEmpty())
            assertFalse(finalState.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
