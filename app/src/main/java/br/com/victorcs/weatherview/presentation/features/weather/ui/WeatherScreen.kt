package br.com.victorcs.weatherview.presentation.features.weather.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import br.com.victorcs.weatherview.BuildConfig
import br.com.victorcs.weatherview.R
import br.com.victorcs.weatherview.core.extensions.kelvinToCelsius
import br.com.victorcs.weatherview.presentation.features.weather.intent.WeatherIntent
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import org.koin.androidx.compose.koinViewModel

private const val PREFIX_IMG_URL = "https://openweathermap.org/img/wn/"
private const val SUFFIX_IMG_URL = "@2x.png"

private const val AD_LOADED = "Ad Loaded"
private const val AD_FAILED = "Ad Failed:"

private const val BANNER_WIDTH = 360

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Button(
            onClick = { viewModel.dispatch(WeatherIntent.FetchConcurrently) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.button_label),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val loadingContentDescription = stringResource(R.string.cd_loading)

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.outlineVariant,
                    modifier = Modifier.semantics {
                        contentDescription = loadingContentDescription
                    },
                )
            }
        } else {
            state.weathers.forEach { (cityName, weather) ->
                Spacer(modifier = Modifier.height(12.dp))

                Text(text = cityName, style = MaterialTheme.typography.titleMedium)

                Text(
                    text = "${weather.main.temp.kelvinToCelsius}°C",
                    style = MaterialTheme.typography.titleLarge
                )

                val description =
                    weather.weatherInfo.firstOrNull()?.description?.replaceFirstChar { it.uppercase() }
                val iconCode = weather.weatherInfo.firstOrNull()?.icon

                if (description != null) {
                    Text(text = description)
                }

                if (iconCode != null) {
                    val imageUrl = "${PREFIX_IMG_URL}${iconCode}${SUFFIX_IMG_URL}"
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUrl),
                        contentDescription = description,
                        modifier = Modifier
                            .size(64.dp)
                            .padding(top = 8.dp)
                    )
                }
            }
        }

        val context = LocalContext.current

        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

        Spacer(modifier = Modifier.weight(1f))

        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            factory = {
                AdView(it).apply {
                    setAdSize(
                        AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                            context,
                            BANNER_WIDTH
                        )
                    )
                    adUnitId = BuildConfig.BANNER_ID
                    loadAd(AdRequest.Builder().build())
                    adListener = object : AdListener() {
                        override fun onAdLoaded() {
                            Toast.makeText(it, AD_LOADED, Toast.LENGTH_SHORT).show()
                        }

                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            Toast.makeText(it, "${AD_FAILED}${adError.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        )
    }
}