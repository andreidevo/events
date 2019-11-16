package com.r.events.view.ui.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener
import com.r.events.MainViewModel
import com.r.events.R
import com.r.events.adapter.ScreenSlidePagerAdapter
import com.r.events.model.PagesParse
import com.r.events.model.filters
import com.r.events.view.ui.Settings.SettingsFragment
import com.r.events.view.ui.favourites.FavouritesFragment
import com.r.events.view.ui.home.HomeFragment
import com.r.events.view.ui.home.HomeViewModel
import com.r.events.view.ui.user_info.UserInfoFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) = runBlocking {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewmodel = MainViewModel()
        if(viewmodel.checkInternet(this@MainActivity)) {
            //запускаем
            GlobalScope.launch {
                val page = PagesParse()
                page.getDataFromPage()

            }.join()//ждем пока выполнятся функции


            val fragList = ArrayList<Fragment>()

            fragList.add(HomeFragment())
            fragList.add(FavouritesFragment())
            fragList.add(SettingsFragment())
            fragList.add(UserInfoFragment())

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
        else
        {
            //ToDo сделать диалговое окно - нет инета
            val toast = Toast.makeText(this@MainActivity, "NOPE", Toast.LENGTH_SHORT)
            toast.show()
        }
        //просто сделаем какие-то фильтры
        //filters.setDate(arrayListOf(arrayListOf(10,10,2019)))
        //filters.setOnline(false)

    }
}
