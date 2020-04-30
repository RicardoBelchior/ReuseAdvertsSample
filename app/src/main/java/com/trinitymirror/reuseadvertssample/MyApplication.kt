package com.trinitymirror.reuseadvertssample

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.google.android.gms.ads.formats.UnifiedNativeAd
import timber.log.Timber

class MyApplication : Application() {

    companion object {
        var adUnitId = "/6499/example/native"

        // List of cached adverts, one per AdvertFragment, identified by the Fragment title.
        val cachedAdverts = mutableMapOf<String, UnifiedNativeAd?>()
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}