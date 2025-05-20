package br.com.victorcs.weatherview.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.victorcs.weatherview.presentation.features.weather.ui.WeatherScreen

@Composable
fun AppNavigation(innerPadding: PaddingValues, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = ScreenRouter.WeatherScreen.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(ScreenRouter.WeatherScreen.route) {
            WeatherScreen()
        }
    }
}
