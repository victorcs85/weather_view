package br.com.victorcs.weatherview

import android.app.Application
import br.com.victorcs.weatherview.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class WeatherViewApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setUpKoin()
        setUpTimber()
    }

    private fun setUpKoin() =
        startKoin {
            androidLogger()
            androidContext(this@WeatherViewApplication)
            modules(
                AppModules().init(),
            )
        }

    private fun setUpTimber() =
        Timber.plant(Timber.DebugTree())
}