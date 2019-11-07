package com.r.events.view.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.r.events.R
import com.r.events.model.PagesParse
import com.r.events.model.list


class HomeFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = layoutInflater.inflate(R.layout.fragment_home, container, false)

        val btn = view.findViewById(R.id.get) as Button
        var viewmodel = HomeViewModel()
        btn.setOnClickListener{
            viewmodel.getContext(view.context)
            viewmodel.getDataFromPage()
        }

        //тут можно теперь принять list с мероприятиями и закинуть его в recycler
        val liste = list

        return view

    }
}