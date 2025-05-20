package br.com.victorcs.weatherview.presentation.features.main.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import br.com.victorcs.weatherview.R
import br.com.victorcs.weatherview.presentation.navigation.AppNavigation
import br.com.victorcs.weatherview.presentation.ui.TopAppBarCustom

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBarCustom(title = stringResource(R.string.challenge))
        },
        content = { innerPadding ->
            AppNavigation(innerPadding, navController)
        }
    )
}