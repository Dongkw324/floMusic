package com.kdw.flomusic.di

import android.app.Application
import com.kdw.flomusic.network.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModules)
        }
    }
}