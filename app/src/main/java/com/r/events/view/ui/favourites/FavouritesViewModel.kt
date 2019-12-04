package com.r.events.view.ui.favourites

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.r.events.Database.EventObject
import com.r.events.Database.EventObjectDAO
import kotlinx.coroutines.*

open class FavouritesViewModel(val database: EventObjectDAO,
                               application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var events : LiveData<List<EventObject>> = database.getAllFavourites(true)

    fun getAllData() : LiveData<List<EventObject>> {
        return events
    }

    fun Modify(event: EventObject) {
        uiScope.launch {
            Log.d("TAG", "Modify: " + event.name + event.favourite + " " + event.type)
            event.favourite = false
            modify(event)
        }
    }

    private suspend fun modify(event: EventObject) {
        withContext(Dispatchers.IO) {
            database.update(event)
        }
    }
}