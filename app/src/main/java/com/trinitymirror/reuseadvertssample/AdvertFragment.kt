package com.trinitymirror.reuseadvertssample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.doubleclick.PublisherAdRequest
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.mirror.news.ui.adapter.teaser_list.holder.TeaserAppInstallAdViewHolder
import com.mirror.news.ui.adapter.teaser_list.holder.TeaserContentAdViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_layout.*
import timber.log.Timber

class AdvertFragment : Fragment() {

    companion object {
        private const val KEY_TITLE = "getTitle"

        fun newInstance(title: String): AdvertFragment {
            return AdvertFragment()
                .apply {
                    arguments = Bundle()
                        .apply { putString(KEY_TITLE, title) }
                }
        }
    }

    private val title by lazy { arguments?.getString(KEY_TITLE)!! }
    private val adView by lazy { view?.findViewById(R.id.teaser_list_dfp_native_ad) as UnifiedNativeAdView? }

    private var localAdvert: UnifiedNativeAd? = null
    private var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTextView.text = title

        loadAdvert()
    }

    override fun onDestroyView() {
        Timber.d("[$title]: Cleared advert")
        disposable?.dispose()
        adView?.destroy()
        localAdvert?.destroy()
        localAdvert = null

        super.onDestroyView()
    }

    private fun loadAdvert() {

        disposable = DfpNativeAdRequest(context!!, getAdUnitId(), buildAdRequest())
            .loadAdvert()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { displayAdvert(it) },
                { displayError(it) }
            )
    }

    private fun displayAdvert(advert: UnifiedNativeAd) {
        localAdvert = advert

        if (advert.isAppInstallAdvert()) {
            Timber.d("[$title]: Displayed app install advert")
            TeaserAppInstallAdViewHolder(DfpViewFactory.createNativeAppInstallAdvert(advertContainer)
                .apply { advertContainer.addView(this) })
                .bind(advert)
        } else {
            Timber.d("[$title]: Displayed content advert")
            TeaserContentAdViewHolder(DfpViewFactory.createNativeContentAdvert(advertContainer)
                .apply { advertContainer.addView(this) })
                .bind(advert)
        }
    }

    private fun displayError(throwable: Throwable) {
        throwable.printStackTrace()
        Toast.makeText(context!!, "Error loading advert", Toast.LENGTH_LONG).show()
    }

    private fun UnifiedNativeAd.isAppInstallAdvert(): Boolean {
        return this.price != null || this.store != null || this.starRating != null
    }

    private fun getAdUnitId() = MyApplication.adUnitId

    private fun buildAdRequest() = PublisherAdRequest.Builder().build()

}