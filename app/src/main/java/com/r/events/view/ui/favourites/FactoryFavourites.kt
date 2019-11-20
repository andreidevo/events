package com.r.events.view.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.r.events.Database.EventObjectDAO
import com.r.events.view.ui.favourites.FavouritesViewModel

class FactoryFavourites(
    private val dataSource: EventObjectDAO,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouritesViewModel::class.java)) {
            return FavouritesViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}