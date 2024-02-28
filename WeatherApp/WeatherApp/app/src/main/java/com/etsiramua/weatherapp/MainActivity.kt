package com.etsiramua.weatherapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentContainerView
import androidx.viewpager2.widget.ViewPager2
import com.etsiramua.weatherapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)

        val todayWeather = TodayWeather()
        val hourlyWeather = HourlyWeather()


        viewPager.adapter = PagerAdapter(todayWeather, hourlyWeather,this)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.today)
                    tab.view.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_blue))
                }
                1 -> {
                    tab.setIcon(R.drawable.hourly)
                    tab.view.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_blue))
                }
            }
        }.attach()

    }
}