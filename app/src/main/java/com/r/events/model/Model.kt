package com.r.events.model

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.widget.TextView
import androidx.core.app.NotificationCompat
import java.net.URL
import android.app.NotificationChannel
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.r.events.view.ui.main_activity.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
import androidx.viewpager.widget.ViewPager
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener
import com.google.android.material.navigation.NavigationView
import com.r.events.adapter.ScreenSlidePagerAdapter
import com.r.events.view.ui.Settings.SettingsFragment
import com.r.events.view.ui.favourites.FavouritesFragment
import com.r.events.view.ui.home.HomeFragment
import com.r.events.view.ui.user_info.UserInfoFragment
import java.lang.Exception


class Model {


    var context : Context? = null
    fun getContext(context : Context)
    {
        this.context = context
    }
    fun editText(textView : TextView, text : String )
    {
        textView.text = text
    }

    var vibrateNotification = true
    var lightNotification = true

    private var idG = 0

    fun pushNotification(name : String?, date : String?, url: URL?){

        //то, что откроется после нажатия
        val resultIntent = Intent(context, MainActivity::class.java)
        val pendingIntent : PendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManager : NotificationManager =  context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val id = "0"
        val builder : NotificationCompat.Builder



        //тут настройка уведомления
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val importantHigh = NotificationManager.IMPORTANCE_HIGH
            var mChannel = notificationManager.getNotificationChannel(id)
            if (mChannel == null) {
                mChannel = NotificationChannel(id, name, importantHigh)
                mChannel.enableVibration(true)
                mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                notificationManager.createNotificationChannel(mChannel)
            }

            builder = NotificationCompat.Builder(context!!, id)
            builder.setContentTitle(date)
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setTicker(name)
                .setStyle(NotificationCompat.BigTextStyle().bigText(name))


            if(lightNotification)
                builder.setLights(Color.BLUE, 3000, 3000)
            //добавляем вибрацию
            if(vibrateNotification)
                builder.setVibrate(longArrayOf(1000, 1000))
        }
        else
        {

            builder = NotificationCompat.Builder(context!!, id)
            builder.setContentTitle(date)
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setContentText(name) // required
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setTicker(name)

            if(lightNotification)
                builder.setLights(Color.BLUE, 3000, 3000)
            //добавляем вибрацию
            if(vibrateNotification)
                builder.setVibrate(longArrayOf(1000, 1000))
        }


        val notification : Notification = builder.build()

        //проверяем на null
        if( checkNotification(name, date))
            notificationManager.notify(++idG, notification)
    }

    private fun checkNotification(name : String?, date : String?) : Boolean
    {
        if( name == null || date == null)
            return false
        return true
    }

    fun viewGroupMainActivity(list : ArrayList<Fragment>,
                              view : ViewPager,
                              support : FragmentManager,
                              bottom_navigation_view_linear : com.gauravk.bubblenavigation.BubbleNavigationLinearView)
    {
        val pagerAdapter = ScreenSlidePagerAdapter(list, support)
        view.setAdapter(pagerAdapter)

        view.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {
            }

            override fun onPageSelected(i: Int) {
                bottom_navigation_view_linear.setCurrentActiveItem(i)
            }

            override fun onPageScrollStateChanged(i: Int) {
            }

        })

    }

    fun checkInternet(context: Context) : Boolean{

        val  connectivityManager : ConnectivityManager =
         context.getSystemService(Context.CONNECTIVITY_SERVICE) as (ConnectivityManager)
        var  activeNetworkInfo : NetworkInfo?

        var checkl = false
        try {
            activeNetworkInfo =  connectivityManager.getActiveNetworkInfo()
            checkl = activeNetworkInfo != null
            checkl = activeNetworkInfo.isConnected
            return checkl
        }catch (e : Exception){
            return false
        }

    }
    fun setFragments(fragList : ArrayList<Fragment>)
    {
        fragList.add(HomeFragment())
        fragList.add(FavouritesFragment())
        fragList.add(SettingsFragment())
        fragList.add(UserInfoFragment())
    }
}
