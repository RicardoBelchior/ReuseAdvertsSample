package com.trinitymirror.reuseadvertssample.adverts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import com.trinitymirror.reuseadvertssample.R
import kotlinx.android.synthetic.main.other_fragment_layout.*

/**
 * Just another fragment from AdvertsContainerActivity, to ensure no other AdvertFragment is in memory.
 */
class OtherFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.other_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView.setOnClickListener {
            imageView
                .animate()
                .rotationBy(360.0f)
                .setInterpolator(OvershootInterpolator())
                .setDuration(500)
                .start()
        }
    }
}