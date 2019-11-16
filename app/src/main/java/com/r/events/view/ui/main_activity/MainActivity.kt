package com.r.events.view.ui.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener
import com.r.events.MainViewModel
import com.r.events.R
import com.r.events.adapter.ScreenSlidePagerAdapter
import com.r.events.model.PagesParse
import com.r.events.model.ProgressLoad
import com.r.events.model.filters
import com.r.events.view.FilterBottomSheet
import com.r.events.view.ui.Settings.SettingsFragment
import com.r.events.view.ui.favourites.FavouritesFragment
import com.r.events.view.ui.home.HomeFragment
import com.r.events.view.ui.home.HomeViewModel
import com.r.events.view.ui.user_info.UserInfoFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()

        val viewmodel = MainViewModel()
        if(viewmodel.checkInternet(this@MainActivity)) {
            //запускаем

            val dialog : DialogFragment = ProgressLoad()

            GlobalScope.async {

                //открываем progressBar
                runOnUiThread {
                    dialog.show(supportFragmentManager.beginTransaction(), "dialog1")
                }

                //запускаем парсинг сайтов
                val x = GlobalScope.async{
                    val page =  PagesParse()
                    page.getDataFromPage()
                }

                launch(Dispatchers.Main)
                {
                    x.await() //ждем обновления данных
                    dialog.dismiss()
                    val fragList = ArrayList<Fragment>()
                    viewmodel.setFragments(fragList)
                    viewmodel.viewGroupSetAdapter(fragList, view_pager, supportFragmentManager, bottom_navigation_view_linear)

                    bottom_navigation_view_linear.setNavigationChangeListener(BubbleNavigationChangeListener { view, position ->
                        view_pager.setCurrentItem(position, true)
                    })


                }
            }
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
