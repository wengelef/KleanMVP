package com.wengelef.kleanmvp

import android.app.Application
import com.wengelef.kleanmvp.di.AppComponent
import com.wengelef.kleanmvp.di.AppModule
import com.wengelef.kleanmvp.di.DaggerAppComponent
import timber.log.Timber

class MainApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}