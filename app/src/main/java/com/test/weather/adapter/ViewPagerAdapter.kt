package com.test.weather.adapter

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.test.weather.PageOne
import com.test.weather.PageTwo

class ViewPagerAdapter(val context: Context, fragment: FragmentManager, var totalTabs: Int) :
    FragmentPagerAdapter(fragment) {
    override fun getItem(position: Int): Fragment {

        when (position) {
            0 -> {
                return PageOne()
            }
            1 -> {
                return PageTwo()
            }
            else -> return PageOne()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}