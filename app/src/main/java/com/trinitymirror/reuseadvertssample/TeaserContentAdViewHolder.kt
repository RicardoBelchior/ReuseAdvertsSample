package com.mirror.news.ui.adapter.teaser_list.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.ads.formats.MediaView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.trinitymirror.reuseadvertssample.R
import io.reactivex.subjects.PublishSubject

class TeaserContentAdViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val mediaView by lazy { view.findViewById<MediaView>(R.id.dfp_content_main_image) }
    private val headlineView by lazy { view.findViewById<TextView>(R.id.dfp_content_title) }
    private val bodyView by lazy { view.findViewById<TextView>(R.id.dfp_content_body) }
    private val advertiserNameView by lazy { view.findViewById<TextView>(R.id.dfp_content_advertiser_name) }
    private val callToActionView by lazy { view.findViewById<Button>(R.id.dfp_content_call_to_action) }

    private val nativeContentAdView by lazy {
        view.findViewById<UnifiedNativeAdView>(R.id.teaser_list_dfp_native_ad)
                .also {
                    // Media content will be automatically populated in the media view once
                    // adView.setNativeAd() is called.
                    it.mediaView = mediaView

                    it.headlineView = headlineView
                    it.bodyView = bodyView
                    it.callToActionView = callToActionView
                    it.advertiserView = advertiserNameView
                }
    }

    fun bind(contentAd: UnifiedNativeAd) {

        headlineView.text = contentAd.headline
        bodyView.text = contentAd.body
        advertiserNameView.text = contentAd.advertiser
        callToActionView.text = contentAd.callToAction

        nativeContentAdView.setNativeAd(contentAd)
    }
}