package br.com.victorcs.weatherview.di

import org.koin.core.module.Module
import org.koin.dsl.module

class AppModules : ModuleInitialization() {

    //region Data
    private val dataModule = module {
    }
    //endregion

    //region Domain
    private val domainModule = module {
    }
    //endregion

    //region Presentation
    private val presentationModule = module {
    }
    //endregion

    override fun init(): List<Module> = listOf(
        dataModule,
        domainModule,
        presentationModule
    )
}