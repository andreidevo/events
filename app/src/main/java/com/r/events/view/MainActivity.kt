package com.r.events.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener
//import com.fxn.BubbleTabBar
//import com.fxn.OnBubbleClickListener
import com.r.events.R
import com.r.events.ScreenSlidePageFragment
import com.r.events.ScreenSlidePagerAdapter
import com.r.events.ViewModel
import com.r.events.model.PagerAdapt
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //создаем связь
        val viewModel = ViewModel()

        //передаем контекст в модель
        viewModel.getContext(this)

        //меняем текст
        val text = findViewById<TextView>(R.id.text)

        //отправим рандомное уведомление
        //viewModel.pushNotification("1 окт 2019", "Программирование", null)
        viewModel.getDataFromPage()

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
