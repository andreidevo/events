package com.r.events

import android.content.Context
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.r.events.model.Model
import java.net.URL

open class MainViewModel : ViewModel(){
    var Model: Model = Model()

    fun getContext(context: Context) {
        Model.getContext(context)
    }

    fun editText(textView: TextView, text: String) {
        Model.editText(textView, text)
    }

    public fun pushNotification(name: String, date: String, url: URL?) {
        Model.pushNotification(name, date, url)
    }

    fun getDataFromPage()
    {
        Model.getDataFromPage()
    }
}