package com.etsiramua.weatherapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(var firstFragment: Fragment,
                   var secondFragment: Fragment,
                   activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> firstFragment
            1 -> secondFragment
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
}