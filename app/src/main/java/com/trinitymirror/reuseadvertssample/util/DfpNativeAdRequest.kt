package com.trinitymirror.reuseadvertssample.util

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.doubleclick.PublisherAdRequest
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.trinitymirror.reuseadvertssample.MyApplication
import io.reactivex.Single
import timber.log.Timber

class DfpNativeAdRequest(
    private val context: Context,
    private val adUnitId: String,
    private val adRequest: PublisherAdRequest
) {

    fun loadAdvert(id: String): Single<UnifiedNativeAd> {

        // If there is a cached advert, return immediately
        if (MyApplication.cachedAdverts.containsKey(id)) {
            return Single.just(MyApplication.cachedAdverts[id])
        }

        return Single.create { emitter ->
            Timber.d("Loading advert...")
            val ts = System.currentTimeMillis()

            buildAdLoader(
                context, adUnitId,
                UnifiedNativeAd.OnUnifiedNativeAdLoadedListener { nativeAd ->
                    Timber.d("Loaded native ad: $nativeAd (took ${System.currentTimeMillis() - ts}ms)")
                    if (!emitter.isDisposed) {
                        MyApplication.cachedAdverts[id] = nativeAd
                        emitter.onSuccess(nativeAd)
                    }
                },
                object : AdListener() {
                    override fun onAdFailedToLoad(errorCode: Int) {
                        if (!emitter.isDisposed) {
                            emitter.onError(Exception("Ad failed to load with error: $errorCode (took ${System.currentTimeMillis() - ts}ms)"))
                        }
                    }
                })
                .loadAd(adRequest)
        }
    }

    private fun buildAdLoader(
        context: Context,
        adUnitId: String,
        unifiedAdLoadedListener: UnifiedNativeAd.OnUnifiedNativeAdLoadedListener,
        adListener: AdListener
    ): AdLoader {

        return AdLoader.Builder(context, adUnitId)
            .forUnifiedNativeAd(unifiedAdLoadedListener)
            .withAdListener(adListener)
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .setRequestMultipleImages(false)
                    .setReturnUrlsForImageAssets(false)
                    .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT)
                    .build()
            )
            .build()
    }
}