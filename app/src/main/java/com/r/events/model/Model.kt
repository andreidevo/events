package com.r.events

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.view.ViewStructure
import android.widget.TextView
import androidx.core.app.NotificationCompat
import java.net.URL

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


    fun pushNotification(name : String, date : String, url: URL?){

        val builder : NotificationCompat.Builder = NotificationCompat.Builder(context)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(date)
            .setContentText(name)
        val notification : Notification = builder.build()
        val notificationManager : NotificationManager =  context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)

    }
}
