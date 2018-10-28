package com.trinitymirror.reuseadvertssample

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_adverts.*

class AdvertsContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adverts)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_home
    }

    override fun onDestroy() {
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
