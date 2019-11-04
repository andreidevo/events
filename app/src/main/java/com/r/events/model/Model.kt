package com.r.events

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
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException
import java.lang.Exception
import java.net.URL
import java.util.ArrayList
import kotlin.concurrent.thread
import android.R
import android.app.NotificationChannel
import com.r.events.view.MainActivity







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

    fun pushNotification(name : String, date : String, url: URL?){

        //то, что откроется после нажатия
        val resultIntent = Intent(context, MainActivity::class.java)
        var pendingIntent : PendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationManager : NotificationManager =  context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val id = "0"
        var builder : NotificationCompat.Builder

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
            builder.setContentTitle(date)                            // required
                .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
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
            builder.setContentTitle(date)                            // required
                .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
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
        notificationManager.notify(1, notification)
    }

    fun deleteNotificationById( id : Int)
    {
        val notificationManager : NotificationManager =  context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        try {
            notificationManager.cancel(id)
        }catch (e : Exception){}
    }
    var titleList : ArrayList<String>? = null
    fun getDataFromPage()
    {
        thread {
            val doc: Document
            try {
                // определяем откуда будем воровать данные
                doc = Jsoup.connect("https://habr.com/ru/").get()
                // задаем с какого места, я выбрал заголовке статей
                var title: Elements = doc.getElementsByTag("span")
                // чистим наш аррей лист для того что бы заполнить
                titleList?.clear()

                pushNotification(title[0].text(), "OPA", null)


            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }

}
