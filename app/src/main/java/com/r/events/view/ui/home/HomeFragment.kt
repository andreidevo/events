package com.r.events.view.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_home)
        homeViewModel.setupRecyclerView(view.context, recyclerView)

        val filterBtn = view.findViewById<ImageView>(R.id.filter_btn)
        homeViewModel.setupFilterBtn(filterBtn, view.context, fragmentManager)

        return view
    }
}