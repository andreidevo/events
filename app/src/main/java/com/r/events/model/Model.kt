package com.r.events

import android.content.Context
import android.widget.TextView

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
}
