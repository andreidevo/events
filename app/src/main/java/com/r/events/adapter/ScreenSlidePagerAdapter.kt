package com.r.events.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ScreenSlidePagerAdapter(
    private val fragmentList: ArrayList<Fragment>,
    fm:FragmentManager
) : FragmentStatePagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        if (position >= 0 && position < fragmentList.size)
            return fragmentList[position]

        return Fragment()
    }

    override fun getCount(): Int = fragmentList.size

}