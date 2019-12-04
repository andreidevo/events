package com.r.events.view.ui.favourites

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import com.r.events.view.FilterBottomSheet
import com.r.events.view.ui.home.FactoryFavourites
import java.util.Comparator

class FavouritesFragment : Fragment(), FavouritesAdapter.OnItemClickListener{

    private lateinit var favouritesViewModel : FavouritesViewModel
    private lateinit var application : Application

    override fun onItemClick(eventObj : EventObject) {
        Log.d("TAG", "Deleted " + eventObj.name)
        Toast.makeText(application, eventObj.name + " deleted", Toast.LENGTH_SHORT).show()
        favouritesViewModel.Modify(eventObj)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        application = requireNotNull(this.activity).application

        val dataSource = EventDatabase.getInstance(application).eventDatabaseDao

        val viewModelFactory = FactoryFavourites(dataSource, application)

        favouritesViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(FavouritesViewModel::class.java)

        val view = layoutInflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_home)

        //homeViewModel.setupRecyclerView(view.context, recyclerView)

        val eventAdapter = FavouritesAdapter(context, this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = eventAdapter

        favouritesViewModel.getAllData().observe(this, object : Observer<List<EventObject>> {
            override fun onChanged(dataItems: List<EventObject>?) {
                if (dataItems != null) {
                    Log.d("TAG", "PASSED" + dataItems.size.toString())
                    eventAdapter.setEvents(getList(dataItems))
                }
            }
        })

        val filterBtn = view.findViewById<ImageView>(R.id.filter_btn)

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
            for(event in value) {
                val eventObj = EventItem(event)
                eventList.add(eventObj)
            }
        }

        eventList.sortWith(object: Comparator<ListItem> {
            override fun compare(p11: ListItem, p22: ListItem): Int {
                val p1 = p11 as EventItem
                val p2 = p22 as EventItem
                val e1 = p1.event
                val e2 = p2.event

                return e1.date!!.compareTo(e2.date!!)
            }
        })

        return eventList
    }
}