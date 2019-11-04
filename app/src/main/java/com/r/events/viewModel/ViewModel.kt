package com.r.events

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.RadialGradient
import android.util.DisplayMetrics
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.r.events.model.Parser
import java.net.URL

class ViewModel {
    var Model: Model = Model()
    var Parser : Parser = Parser()
    fun getContext(context: Context) {
        Model.getContext(context)
    }

    fun editText(textView: TextView, text: String) {
        Model.editText(textView, text)
    }

    fun pushNotification(name: String, date: String, url: URL?) {

        Model.pushNotification(name, date, url)
    }
    fun getDataFromPage()
    {
        Model.getDataFromPage()
    }
}