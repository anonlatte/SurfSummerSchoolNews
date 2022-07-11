package com.anonlatte.natgeo

import android.app.Application
import com.anonlatte.natgeo.di.appModule
import com.anonlatte.natgeo.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class NatGeoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@NatGeoApplication)
            modules(listOf(appModule, networkModule))
        }

        Timber.plant(Timber.DebugTree())
    }
}