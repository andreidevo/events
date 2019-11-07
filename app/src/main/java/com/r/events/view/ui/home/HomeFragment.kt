package com.r.events.view.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.r.events.MainViewModel
import com.r.events.R
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = layoutInflater.inflate(R.layout.fragment_home, container, false)
        val btn = view.findViewById(R.id.get) as Button
        val mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        btn.setOnClickListener{
            mainViewModel.getContext(view.context)
            mainViewModel.getDataFromPage()
        }
        return view

    }
}