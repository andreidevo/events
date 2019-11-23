package com.r.events.Database

import android.util.Log
import androidx.room.*
import com.r.events.model.Day
import com.r.events.model.Utils
import com.r.events.model.list
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "favourite_events_table")
@TypeConverters(Converters::class)
class EventObject {

    @PrimaryKey(autoGenerate = true)
    var eventId: Long = 0L

    @ColumnInfo(name = "event_name")
    var name: String? = null

    @ColumnInfo(name = "event_date")
    var date: Day? = null

    @ColumnInfo(name = "event_type")
    var type: String? = null

    @ColumnInfo(name = "event_sector")
    var sector: String? = null

    @ColumnInfo(name = "event_location")
    var location: String? = null

    @ColumnInfo(name = "event_online")
    var online: Boolean? = null

    @ColumnInfo(name = "event_href")
    var href: String? = null

    @ColumnInfo(name = "event_photoHref")
    var photoHref: String? = null

    @ColumnInfo(name = "event_description")
    var description: String? = null

    @ColumnInfo(name = "event_favourite")
    var favourite: Boolean = false

    override fun equals(other: Any?): Boolean {
        if (other == null)
            return false // null check
        if (javaClass != other.javaClass)
            return false // type check

        val mEvent = other as EventObject
        return eventId == mEvent.eventId
                && name == mEvent.name
                && date == mEvent.date
                && type == mEvent.type
                && sector == mEvent.sector
                && location == mEvent.location
                && online == mEvent.online
                && href == mEvent.href
                && photoHref == mEvent.photoHref
                && description == mEvent.description
                && favourite == mEvent.favourite
    }

    override fun hashCode(): Int {
        return Objects.hash(eventId, name, date, type, sector, location, online, href, photoHref, description, favourite)
    }

}

//Class to convert MutableList <-> String
class Converters {

    var utils = Utils()

    @TypeConverter
    fun listToSting(value: Day): String
    {
        return value.getSimpleDate(0,0)
    }



    fun parseStringToDay(string : String) : Day
    {
        lateinit var Day : Day

        var count = string.split('-').size
        // 14-01-2019 - 15-01-2019
        // 14 нояб 2019 - 15 нояб 2019
        if( string.contains("-") && ( count == 2 || count > 4))
        {

            lateinit var leftDay : List<String>
            lateinit var rightDay : List<String>
            val spliter = string.split(" - ")

            //две даты
            if( count == 2)
            {
                leftDay = spliter[0].split(' ')
                rightDay = spliter[1].split(' ')
            }
            else if( count > 4)
            {
                leftDay = spliter[0].split('-')
                rightDay = spliter[1].split('-')
            }

            Day = Day(leftDay[0].toInt(), com.r.events.model.utils.convertMonth(leftDay[1]), leftDay[2].toInt())
            Day.addInterval(rightDay[0].toInt(),  com.r.events.model.utils.convertMonth(rightDay[1]), rightDay[2].toInt())

        }
        else
        {
            lateinit var day : List<String>
            //одна дата
            if( count > 1)
            {
                //15-01-2019
                day = string.split('-')
                Day = Day(day[0].toInt(), com.r.events.model.utils.convertMonth(day[1]), day[2].toInt() )
            }
            else
            {
                //15 нояб 2019
                day = string.split(' ')
                Day = Day(day[0].toInt(), utils.convertMonth(day[1]), day[2].toInt())

            }
        }
        return Day
    }
    @TypeConverter
    fun stringToList(value: String): Day {

        return parseStringToDay(value)
    }
}