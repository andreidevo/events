package com.r.events.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.r.events.Database.EventDatabase
import com.r.events.R
import com.r.events.view.ui.home.Factory
import com.r.events.view.ui.home.HomeViewModel
import com.varunest.sparkbutton.SparkButton
import com.varunest.sparkbutton.SparkEventListener

class FilterBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.bottom_sheet_dialog_filter, container, false)


        val type_live = view.findViewById<SparkButton>(R.id.type_live)
        val type_conf = view.findViewById<SparkButton>(R.id.type_conf)
        val theme_programming = view.findViewById<SparkButton>(R.id.theme_programming)
        val theme_design = view.findViewById<SparkButton>(R.id.theme_design)


        val application = requireNotNull(this.activity).application

        //homeViewModel имеет конструктор и получает на входе контекст(application) и обьект ДБ(dataSource(смотри внизу)). Для удобсва есть Factory
        val dataSource = EventDatabase.getInstance(application).eventDatabaseDao
        val viewModelFactory = Factory(dataSource, application)

        val homeViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(HomeViewModel::class.java)

        val filters = homeViewModel.getFilters()

        type_live.setEventListener(object : SparkEventListener {
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }
            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
            }
            override fun onEvent(button: ImageView, buttonState: Boolean) {
                if (buttonState) {
                    // Button is active
                    filters.online = true
                    type_live.isChecked = true
                    //какая-то функция update всех данных
                } else {
                    // Button is inactive
                    filters.online = false
                    type_live.isChecked = false
                }
            }
        })
        type_conf.setEventListener(object : SparkEventListener {
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }
            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
            }
            override fun onEvent(button: ImageView, buttonState: Boolean) {
                if (buttonState) {
                    type_conf.isChecked = true

                    if (filters.type?.contains(2)!!) {
                        val list = filters.type
                        list?.add(2)
                        filters.type = list
                    }
                } else {

                    if(filters.type?.contains(2)!!)
                    {
                        val list = filters.type
                        list?.remove(2)
                        filters.type = list
                    }
                    type_conf.isChecked = false

                }
            }
        })
        theme_programming.setEventListener(object : SparkEventListener {
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }
            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
            }
            override fun onEvent(button: ImageView, buttonState: Boolean) {
                if (buttonState) {
                    theme_programming.isChecked = true

                    if (filters.type?.contains(0)!!) {
                        val list = filters.sector
                        list?.add(0)
                        filters.sector = list
                    }
                } else {

                    if(filters.type?.contains(0)!!)
                    {
                        val list = filters.sector
                        list?.remove(0)
                        filters.sector = list
                    }
                    theme_programming.isChecked = false

                }
            }
        })
        theme_design.setEventListener(object : SparkEventListener {
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }
            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
            }
            override fun onEvent(button: ImageView, buttonState: Boolean) {
                if (buttonState) {
                    theme_design.isChecked = true

                    if (filters.type?.contains(1)!!) {
                        val list = filters.sector
                        list?.add(1)
                        filters.sector = list
                    }
                } else {

                    if(filters.type?.contains(1)!!)
                    {
                        val list = filters.sector
                        list?.remove(1)
                        filters.sector = list
                    }
                    theme_design.isChecked = false

                }
            }
        })

        return view

    }

}