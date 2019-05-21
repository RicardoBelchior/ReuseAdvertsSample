package com.trinitymirror.reuseadvertssample.adverts

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.trinitymirror.reuseadvertssample.R
import kotlinx.android.synthetic.main.activity_adverts.*
import timber.log.Timber

class AdvertsContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adverts)
        Timber.d("#onCreate")

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_home
    }

    override fun onDestroy() {
        Timber.d("#onDestroy")
        navigation.setOnNavigationItemSelectedListener(null)
        super.onDestroy()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                openFragment(getString(R.string.title_home))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                openFragment(getString(R.string.title_dashboard))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                openFragment(getString(R.string.title_notifications))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_cake -> {
                openOtherFragment()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(title: String) {
        openFragment(AdvertFragment.newInstance(title))
    }

    private fun openOtherFragment() {
        openFragment(OtherFragment())
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitNow()
    }
}
