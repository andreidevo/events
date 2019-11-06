package com.r.events.model

import android.app.Activity
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
import android.net.Uri
import com.r.events.view.MainActivity
import androidx.core.content.ContextCompat.startActivity



class Model {


    var context : Context? = null
    var activity : Activity? = null
    fun getContext(context : Context)
    {
        this.context = context
    }
    fun getActivity(Activity : Activity)
    {
        this.activity = Activity
    }
    fun editText(textView : TextView, text : String )
    {
        textView.text = text
    }

    var vibrateNotification = true
    var lightNotification = true

    private var idG = 0;
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
                .setContentText(name)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setTicker(name)


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
    //функция парсинга сайтов
    fun getDataFromPage()
    {
        //ссылкаемся на класс с парсингами
        val PagesParse = PagesParse(this)
        PagesParse.it_events()

    }

}
