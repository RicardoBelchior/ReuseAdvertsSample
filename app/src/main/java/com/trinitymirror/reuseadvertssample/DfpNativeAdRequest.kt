package com.trinitymirror.reuseadvertssample

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.doubleclick.PublisherAdRequest
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import io.reactivex.Single
import timber.log.Timber

class DfpNativeAdRequest(
    private val context: Context,
    private val adUnitId: String,
    private val adRequest: PublisherAdRequest
) {

    fun loadAdvert(): Single<UnifiedNativeAd> {

        return Single.create<UnifiedNativeAd> { emitter ->
            Timber.d("Loading advert...")
            buildAdLoader(
                context, adUnitId,
                UnifiedNativeAd.OnUnifiedNativeAdLoadedListener { nativeAd ->
                    Timber.d("Loaded native ad: $nativeAd")
                    if (!emitter.isDisposed) {
                        emitter.onSuccess(nativeAd)
                    }
                },
                object : AdListener() {
                    override fun onAdFailedToLoad(errorCode: Int) {
                        if (!emitter.isDisposed) {
                            emitter.onError(Exception("Ad failed to load with error: $errorCode"))
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