package com.trinitymirror.reuseadvertssample

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.gms.ads.formats.UnifiedNativeAdView

object DfpViewFactory {

    fun createNativeContentAdvert(parent: ViewGroup): View {
        return createNativeAdView(parent, R.layout.dfp_native_content_ad)
    }

    fun createNativeAppInstallAdvert(parent: ViewGroup): View {
        return createNativeAdView(parent, R.layout.dfp_native_app_install_ad)
    }

    private fun createNativeAdView(parent: ViewGroup, @LayoutRes layoutResId: Int): View {
        val context = parent.context
        val adView = LayoutInflater.from(context).inflate(layoutResId, parent, false) as ConstraintLayout

        val nativeAppInstallAdView = UnifiedNativeAdView(context)
        nativeAppInstallAdView.id = R.id.teaser_list_dfp_native_ad
        nativeAppInstallAdView.addView(adView)

        val cardView = createAdvertCard(context)
        cardView.addView(nativeAppInstallAdView)

        return cardView
    }

    private fun createAdvertCard(context: Context): CardView {
        val cardView = CardView(context)
        val lp = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val verticalMargin = context.resources.getDimensionPixelSize(R.dimen.teaser_list_dfp_native_vertical_margin)
        val horizontalMargin = context.resources.getDimensionPixelSize(R.dimen.teaser_list_dfp_native_horizontal_margin)
        lp.setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin)
        cardView.layoutParams = lp
        return cardView
    }
}