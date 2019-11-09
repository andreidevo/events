package com.r.events.view.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.r.events.R



class HomeFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val view = layoutInflater.inflate(R.layout.fragment_home, container, false)

        val btn = view.findViewById(R.id.get) as Button

        val recyclerView = view.findViewById(R.id.recycler_home) as RecyclerView
        recyclerView.setHasFixedSize(true)

        homeViewModel.setupRecyclerView(view.context, recyclerView)
        //homeViewModel.setupButton(btn, this, view.context)
        homeViewModel.getData(this, view.context)
        return view

    }
}