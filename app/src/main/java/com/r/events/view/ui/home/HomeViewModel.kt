package com.r.events.view.ui.home

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Filter
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.r.events.Database.EventObject
import com.r.events.Database.EventObjectDAO
import com.r.events.MainViewModel
import com.r.events.adapter.EventAdapter
import com.r.events.model.Filters
import com.r.events.model.filters
import com.r.events.model.list
import com.r.events.view.FilterBottomSheet
import kotlinx.coroutines.*

//ToDo сделать так, чтобы context не нужно было постоянно ложить в каждый метод
open class HomeViewModel(val database: EventObjectDAO,
                         application: Application) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var events : LiveData<List<EventObject>> = database.getAllEvents()
    lateinit var favourites : List<EventObject>
    fun getAllData() : LiveData<List<EventObject>> {
        return events
    }
    private suspend fun getAllFavouritesList() {
        withContext(Dispatchers.IO) {
            favourites = database.getAllFavouritesList(true)
        }
    }
    fun getData() {
        uiScope.launch {
            getAllFavouritesList()
            clear()

            for(event in list) {
                insert(event)
            }
            for(event in favourites) {
                insert(event)
            }
        }
    }

    fun Modify(event: EventObject) {
        uiScope.launch {
            event.favourite = true
            modify(event)
        }
    }

    private suspend fun modify(event: EventObject) {
        withContext(Dispatchers.IO) {
            database.update(event)
        }
    }

    private suspend fun insert(event: EventObject) {
        withContext(Dispatchers.IO) {
            if (database.getItemById(event.name) == null)
                database.insert(event)
            val eventDB = database.getItemById(event.name)!!
            if (event.favourite) {
                Modify(eventDB)
            }
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    fun getFilters() : Filters{
        return filters
    }
}
