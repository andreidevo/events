package com.r.events.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener
import com.r.events.R
import com.r.events.ScreenSlidePageFragment
import com.r.events.ScreenSlidePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragList = ArrayList<ScreenSlidePageFragment>()
        fragList.add(
            ScreenSlidePageFragment.newInstance(
                getString(R.string.main_page),
                R.color.white
            )
        )
        fragList.add(
            ScreenSlidePageFragment.newInstance(
                getString(R.string.mapped_list),
                R.color.colorOrange
            )
        )
        fragList.add(
            ScreenSlidePageFragment.newInstance(
                getString(R.string.options),
                R.color.white
            )
        )
        fragList.add(
            ScreenSlidePageFragment.newInstance(
                getString(R.string.user),
                R.color.colorOrange
            )
        )

        val pagerAdapter = ScreenSlidePagerAdapter(fragList, supportFragmentManager)




        view_pager.setAdapter(pagerAdapter)
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {

            }

            override fun onPageSelected(i: Int) {
                bottom_navigation_view_linear.setCurrentActiveItem(i)
            }

            override fun onPageScrollStateChanged(i: Int) {


            }

        })

        bottom_navigation_view_linear.setNavigationChangeListener(BubbleNavigationChangeListener { view, position ->
            view_pager.setCurrentItem(
                position,
                true
            )
        })

    }
}
