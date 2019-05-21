package com.trinitymirror.reuseadvertssample

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

class MyApplication : Application() {

    companion object {
        var adUnitId = "/6499/example/native"
        //val cachedAdverts = mutableMapOf<String, UnifiedNativeAd?>()
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}