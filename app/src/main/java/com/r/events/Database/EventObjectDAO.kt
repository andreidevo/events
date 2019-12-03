package com.r.events.Database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EventObjectDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: EventObject) : Long

    @Update
    fun update(event: EventObject)

    @Query("SELECT * FROM favourite_events_table ORDER BY eventId DESC")
    fun getAllEvents(): LiveData<List<EventObject>>

    @Query("SELECT * FROM favourite_events_table WHERE event_favourite = :idx")
    fun getAllFavourites(idx : Boolean): LiveData<List<EventObject>>

    @Query("DELETE FROM favourite_events_table")
    fun clear()

    @Query("SELECT * FROM favourite_events_table WHERE event_favourite = :idx")
    fun getAllFavouritesList(idx : Boolean): List<EventObject>

    @Query("SELECT * from favourite_events_table WHERE event_name = :id")
    fun getItemById(id : String?): EventObject?
}
