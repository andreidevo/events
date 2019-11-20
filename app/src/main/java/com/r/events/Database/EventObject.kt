package com.r.events.Database

import androidx.room.*
import com.r.events.model.Utils

@Entity(tableName = "favourite_events_table")
data class EventObject (

    @PrimaryKey(autoGenerate = true)
    var eventId: Long = 0L,

    @ColumnInfo(name = "event_name")
    var name : String? = null,

    @ColumnInfo(name = "event_date")
    var date : String? = null,

    @ColumnInfo(name = "event_type")
    var type : String? = null,

    @ColumnInfo(name = "event_sector")
    var sector : String? = null,

    @ColumnInfo(name = "event_location")
    var location : String? = null,

    @ColumnInfo(name = "event_online")
    var online : Boolean? = null,

    @ColumnInfo(name = "event_href")
    var href : String? = null,

    @ColumnInfo(name = "event_photoHref")
    var photoHref : String? = null,

    @ColumnInfo(name = "event_description")
    var description : String? = null,

    @ColumnInfo(name = "event_favourite")
    var favourite : Boolean = false
)