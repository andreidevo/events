package com.r.events.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
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


    lateinit var type_live : SparkButton
    lateinit var type_conf : SparkButton
    lateinit var theme_programming : SparkButton
    lateinit var theme_design : SparkButton
    lateinit var type_hackaton : SparkButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_dialog_filter, container, false)

        type_live = view.findViewById(R.id.type_live)
        type_conf = view.findViewById(R.id.type_conf)
        type_hackaton = view.findViewById(R.id.type_hackaton)
        theme_programming = view.findViewById(R.id.theme_programming)
        theme_design = view.findViewById(R.id.theme_design)


        val application = requireNotNull(this.activity).application

        //homeViewModel имеет конструктор и получает на входе контекст(application) и обьект ДБ(dataSource(смотри внизу)). Для удобсва есть Factory
        val dataSource = EventDatabase.getInstance(application).eventDatabaseDao
        val viewModelFactory = Factory(dataSource, application)

        val homeViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(HomeViewModel::class.java)

        val filters = homeViewModel.getFilters()

        val buttonsfilter = homeViewModel.getButtonsfilters()
        //проверить какие кнопки были нажаты и поставить чекеры

        if( buttonsfilter.theme_programming == 1)
            theme_programming.isChecked = true
        if( buttonsfilter.type_hackton == 1)
            type_hackaton.isChecked = true


        type_live.setEventListener(object : SparkEventListener {

            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }
            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
                vibrate(view.context, 40)
            }
            override fun onEvent(button: ImageView, buttonState: Boolean) {


                if (buttonState) {
                    filters.online = true
                    type_live.isChecked = true
                } else {
                    filters.online = false
                    type_live.isChecked = false

                }

            }
        })
        type_conf.setEventListener(object : SparkEventListener {
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {

            }
            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
                vibrate(view.context, 40 )
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
        type_hackaton.setEventListener(object : SparkEventListener {
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {

            }
            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
                vibrate(view.context, 40 )
            }
            override fun onEvent(button: ImageView, buttonState: Boolean) {

                if (buttonState) {
                    type_hackaton.isChecked = true

                    if (!filters.type?.contains(4)!!) {
                        val list = filters.type
                        list?.add(4)
                        filters.type = list
                        homeViewModel.filter()
                    }
                    buttonsfilter.type_hackton = 1
                } else {

                    if(filters.type?.contains(4)!!)
                    {
                        val list = filters.type
                        list?.remove(4)
                        filters.type = list
                        homeViewModel.filter()

                    }
                    type_hackaton.isChecked = false
                    buttonsfilter.type_hackton = 0

                }
            }
        })

        theme_programming.setEventListener(object : SparkEventListener {
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }
            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
                vibrate(view.context, 40 )
            }
            override fun onEvent(button: ImageView, buttonState: Boolean) {
                if (buttonState) {
                    theme_programming.isChecked = true
                    buttonsfilter.theme_programming = 1

                    if (!filters.sector?.contains(0)!!) {
                        val list = filters.sector
                        list?.add(0)
                        filters.sector = list
                    }
                } else {

                    if(filters.sector?.contains(0)!!)
                    {
                        val list = filters.sector
                        list?.remove(0)
                        filters.sector = list
                    }
                    buttonsfilter.theme_programming = 0
                    theme_programming.isChecked = false

                }
                homeViewModel.filter()

            }
        })
        theme_design.setEventListener(object : SparkEventListener {
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }
            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
                vibrate(view.context, 40 )
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

    private fun vibrate (context : Context, miliseconds : Long)
    {
        if (Build.VERSION.SDK_INT >= 26) {
            (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(VibrationEffect.createOneShot(miliseconds, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(miliseconds)
        }

    }


}