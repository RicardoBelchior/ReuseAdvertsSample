package com.trinitymirror.reuseadvertssample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.trinitymirror.reuseadvertssample.adverts.AdvertsContainerActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("#onCreate")

        button.setOnClickListener { startActivity(Intent(this, AdvertsContainerActivity::class.java)) }
        adUnitTextView.setText(MyApplication.adUnitId)
        adUnitTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                MyApplication.adUnitId = s.toString()
            }

        })
    }

    override fun onDestroy() {
        Timber.d("#onDestroy")
        super.onDestroy()
    }
}
