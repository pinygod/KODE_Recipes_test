package com.piny.kode_recipes_test

import android.app.Application
import com.piny.kode_recipes_test.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            koin.loadModules(listOf(networkModule, viewModelsModule, repositoryModule, databaseModule, utilsModule))
        }
    }
}