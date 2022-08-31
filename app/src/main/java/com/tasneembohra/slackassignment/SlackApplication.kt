package com.tasneembohra.slackassignment

import android.app.Application
import com.tasneembohra.slackassignment.di.KoinLogger
import com.tasneembohra.slackassignment.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class SlackApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        configureTimber()
        configureDependencyInjection()
    }

    private fun configureTimber() {
        Timber.uprootAll()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun configureDependencyInjection() {
        startKoin {
            logger(KoinLogger())
            androidContext(this@SlackApplication)
            modules(appModules)
        }
    }
}
