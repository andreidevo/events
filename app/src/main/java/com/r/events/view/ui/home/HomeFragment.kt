package com.r.events.view.ui.home

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.r.events.Database.EventDatabase
import com.r.events.Database.EventObject
import com.r.events.R
import com.r.events.model.ClassesForRecyclerView.EventItem
import com.r.events.model.ClassesForRecyclerView.HeaderItem
import com.r.events.model.ClassesForRecyclerView.ListItem
import com.r.events.model.list
import com.r.events.view.FilterBottomSheet


class HomeFragment : Fragment(), HomeAdapter.OnItemClickListener{

    private lateinit var homeViewModel : HomeViewModel
    private lateinit var application : Application

    override fun onItemClick(eventObg : EventObject) {
        Log.d("TAG", "Added " + eventObg.name)
        Toast.makeText(application, eventObg.name + " added to favourites", Toast.LENGTH_SHORT).show()
        homeViewModel.Modify(eventObg)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        application = requireNotNull(this.activity).application

        val dataSource = EventDatabase.getInstance(application).eventDatabaseDao

        val viewModelFactory = Factory(dataSource, application)

        homeViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(HomeViewModel::class.java)

        val view = layoutInflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_home)

        //homeViewModel.setupRecyclerView(view.context, recyclerView)

        val eventAdapter = HomeAdapter(context, this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = eventAdapter

        homeViewModel.getData()

        homeViewModel.getAllData().observe(this, object : Observer<List<EventObject>> {
            override fun onChanged(dataItems: List<EventObject>?) {
                if (dataItems != null) {
                    Log.d("TAG", "dataItem.size: " + dataItems.size.toString() + " list.size: " + list.size.toString())
                    eventAdapter.setEvents(getList(dataItems))
                }
            }
        })

        val filterBtn = view.findViewById<ImageView>(R.id.filter_btn)

        //почему-то setupFilterBtn не работает
        filterBtn.setOnClickListener{
            val dialog = FilterBottomSheet()
            dialog.show(activity?.supportFragmentManager, "ada")
        }

        return view
    }

    fun getMap(list : List<EventObject>) : HashMap<String, ArrayList<EventObject>> {
        val eventMap = HashMap<String, ArrayList<EventObject>>()

        for (obj in list) {
            val eventSector = obj.sector!!
            if (eventMap[eventSector] == null) {
                eventMap[eventSector] = ArrayList()
            }
            eventMap[eventSector]!!.add(obj)
        }
        return eventMap
    }

    fun getList(list : List<EventObject>?) : java.util.ArrayList<ListItem>? {
        if(list == null)
            return ArrayList<ListItem>()

        val map = getMap(list)

        val eventList = ArrayList<ListItem>()

        map.forEach { (key, value) ->
            val header = HeaderItem(key)
            eventList.add(header)
            for(event in value) {
                val eventObj = EventItem(event)
                eventList.add(eventObj)
            }
        }

        return eventList
    }
}