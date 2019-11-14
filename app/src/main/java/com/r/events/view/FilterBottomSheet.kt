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
import com.r.events.R
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


        val homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val filters = homeViewModel.getFilters()

        type_live.setEventListener(object : SparkEventListener {
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }
            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
            }
            override fun onEvent(button: ImageView, buttonState: Boolean) {
                if (buttonState) {
                    // Button is active
                    filters.setOnline(true)
                    type_live.isChecked = true
                } else {
                    // Button is inactive
                    filters.setOnline(false)
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

                    if (!filters.getType().contains(2)) {
                        val list = filters.getType()
                        list.add(2)
                        filters.setType(list)
                    }
                } else {

                    if(filters.getType().contains(2))
                    {
                        val list = filters.getType()
                        list.remove(2)
                        filters.setType(list)
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
                    type_conf.isChecked = true

                    if (!filters.getType().contains(0)) {
                        val list = filters.getSector()
                        list.add(0)
                        filters.setSector(list)
                    }
                } else {

                    if(filters.getType().contains(0))
                    {
                        val list = filters.getSector()
                        list.remove(0)
                        filters.setSector(list)
                    }
                    type_conf.isChecked = false

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
                    type_conf.isChecked = true

                    if (!filters.getType().contains(1)) {
                        val list = filters.getSector()
                        list.add(1)
                        filters.setSector(list)
                    }
                } else {

                    if(filters.getType().contains(1))
                    {
                        val list = filters.getSector()
                        list.remove(1)
                        filters.setSector(list)
                    }
                    type_conf.isChecked = false

                }
            }
        })


        return view

    }

}