package br.com.victorcs.weatherview.data.source.remote.repository

import androidx.test.filters.SmallTest
import br.com.victorcs.weatherview.base.CoroutineTestRule
import br.com.victorcs.weatherview.data.entity.WeatherResponse
import br.com.victorcs.weatherview.data.source.remote.WeatherAPI
import br.com.victorcs.weatherview.domain.mapper.DomainMapper
import br.com.victorcs.weatherview.domain.model.Response
import br.com.victorcs.weatherview.domain.model.Weather
import br.com.victorcs.weatherview.shared.test.DataMockTest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class WeatherRepositoryImplTest {

    private val remoteDataSource = mockk<WeatherAPI>(relaxed = true)
    private val mapper = mockk<DomainMapper<WeatherResponse, Weather>>(relaxed = true)

    private lateinit var repository: WeatherRepositoryImpl

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @Before
    fun setup() {
        repository = WeatherRepositoryImpl(remoteDataSource, mapper)
    }

    @Test
    fun givenLatLon_whenGetWeatherData_thenReturnWeatherSuccessfully() = runTest {
        val mockResponse = mockk<WeatherResponse>()
        val mockDomain = mockk<Weather>()

        coEvery { remoteDataSource.getWeather(DataMockTest.SP_LAT, DataMockTest.SP_LON) } returns mockResponse
        every { mapper.toDomain(mockResponse) } returns mockDomain

        val result = repository.getWeatherData(DataMockTest.SP_LAT, DataMockTest.SP_LON)

        assert(result is Response.Success && result.data == mockDomain)
    }

    @Test
    fun givenDataWithError_whenGetWeatherData_thenReturnError() = runTest {
        coEvery { remoteDataSource.getWeather(DataMockTest.SP_LAT, DataMockTest.SP_LON) } throws RuntimeException(DataMockTest.ERROR_MESSAGE)

        val result = repository.getWeatherData(DataMockTest.SP_LAT, DataMockTest.SP_LON)

        assert(result is Response.Error && result.errorMessage.contains(DataMockTest.ERROR_MESSAGE))
    }
}
