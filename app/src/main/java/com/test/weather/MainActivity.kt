package com.test.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.test.weather.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.nav_view.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.*
import androidx.core.view.size
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {
    override fun onTabReselected(p0: TabLayout.Tab?) {

    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {

    }

    override fun onTabSelected(p0: TabLayout.Tab?) {
        viewPager!!.currentItem = p0!!.position
    }

    lateinit var btn_menu: ImageView
    lateinit var tabLayout: TabLayout
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: View
    lateinit var viewPager: ViewPager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindView()
        setActionClick()

        val pageAdapter = ViewPagerAdapter(this, supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = pageAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
    }


    fun bindView() {
        btn_menu = findViewById(R.id.btn_menu)
        tabLayout = findViewById(R.id.tab_layout)
        tabLayout.setOnTabSelectedListener(this)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        viewPager = findViewById(R.id.view_pager)


    }

    fun setActionClick() {
        btn_menu.setOnClickListener(View.OnClickListener {
            drawerLayout.openDrawer(navigationView)
        })

        lv_page_one.setOnClickListener(View.OnClickListener {
            viewPager!!.currentItem = 0
            drawerLayout.closeDrawer(navigationView)
        })

        lv_page_two.setOnClickListener(View.OnClickListener {
            viewPager!!.currentItem = 1
            drawerLayout.closeDrawer(navigationView)
        })


    }


}


