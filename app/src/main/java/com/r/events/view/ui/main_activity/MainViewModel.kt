package com.r.events

import android.content.Context
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.r.events.model.Model
import java.net.URL
import java.util.ArrayList

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

    fun viewGroupSetAdapter(list : ArrayList<Fragment>,
                            view : androidx.viewpager.widget.ViewPager,
                            support : FragmentManager,
                            bottom : com.gauravk.bubblenavigation.BubbleNavigationLinearView)
    {
        Model.viewGroupMainActivity(list, view, support, bottom )
    }
    fun setFragments(list : ArrayList<Fragment>)
    {
        Model.setFragments(list);
    }
    fun checkInternet(context: Context) : Boolean
    {
        return Model.checkInternet(context )
    }
}