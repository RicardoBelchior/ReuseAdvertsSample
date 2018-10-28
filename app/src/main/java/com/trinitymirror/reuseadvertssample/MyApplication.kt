package com.trinitymirror.reuseadvertssample

import android.app.Application
import timber.log.Timber

class MyApplication : Application() {

    companion object {
        var adUnitId = "/6499/example/native-backfill"
        //val cachedAdverts = mutableMapOf<String, UnifiedNativeAd?>()
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}