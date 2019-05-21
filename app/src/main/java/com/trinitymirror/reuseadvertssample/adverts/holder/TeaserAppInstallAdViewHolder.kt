package com.trinitymirror.reuseadvertssample.adverts.holder

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.gms.ads.formats.MediaView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.trinitymirror.reuseadvertssample.R

class TeaserAppInstallAdViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val mediaView by lazy { view.findViewById<MediaView>(R.id.dfp_app_install_main_image) }
    private val headlineView by lazy { view.findViewById<TextView>(R.id.dfp_app_install_title) }
    private val appIconView by lazy { view.findViewById<ImageView>(R.id.dfp_app_install_app_icon) }
    private val ratingBarView by lazy { view.findViewById<RatingBar>(R.id.dfp_app_install_rating_bar) }
    private val storeView by lazy { view.findViewById<TextView>(R.id.dfp_app_install_store_name) }
    private val callToActionButton by lazy { view.findViewById<Button>(R.id.dfp_app_install_call_to_action) }

    private val appInstallAdView by lazy {
        view.findViewById<UnifiedNativeAdView>(R.id.teaser_list_dfp_native_ad)
                .also {
                    // Media content will be automatically populated in the media view once
                    // adView.setNativeAd() is called.
                    it.mediaView = mediaView

                    it.headlineView = headlineView
                    it.iconView = appIconView
                    it.starRatingView = ratingBarView
                    it.storeView = storeView
                    it.callToActionView = callToActionButton
                }
    }

    fun bind(appInstallAd: UnifiedNativeAd) {

        val iconUri = appInstallAd.icon.uri
        if (iconUri != null) {
            Glide.with(itemView.context)
                .load(iconUri)
                .into(appIconView)
        }

        headlineView.text = appInstallAd.headline
        ratingBarView.rating = appInstallAd.starRating.toFloat()
        storeView.text = appInstallAd.store
        callToActionButton.text = appInstallAd.callToAction

        appInstallAdView.setNativeAd(appInstallAd)
    }

}