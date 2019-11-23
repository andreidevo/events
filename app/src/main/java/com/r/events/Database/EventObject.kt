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
    var date: ArrayList<Day>? = null

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

    fun getDataNormal(len: Int, format: Int): String {

        val RUS: Int = 0
        val ENG: Int = 1

        val NUM_FORMAT: Int = 0 // 1-15-2019
        val TEXT_FORMAN: Int = 1 // 1 Nov 2019

        lateinit var str : String

        var utils = Utils()

        val value = this.date

        if(value!!.size == 1) {
            str = value[0].day.toString() + ' ' + utils.convertNumToMonth(value[0].month, 0) + ' ' + value[0].year.toString()
        } else {
            str = value[0].day.toString() + ' ' + utils.convertNumToMonth(value[0].month, 0) + ' ' +  value[0].year.toString() + '-' +
                    value[1].day.toString() + ' ' +  utils.convertNumToMonth(value[1].month, 0) + ' ' +  value[1].year.toString()
        }

        Log.d("TAG1", value[0].month.toString() + " " + value[0].year.toString() + " " + value[0].day.toString())

        return str

        //var data = this.date!!

        /*if (len == RUS) {
        if (data.size == 2) {
            //значит промежуток
            if (format == NUM_FORMAT)
                return "от ${data[0][0]}-${data[0][1]}-${data[0][2]} до ${data[1][0]}-${data[1][1]}-${data[1][2]}"
            else if (format == TEXT_FORMAN) {
                val firstMonth = Utils.convertNumToMonth(data[0][1], len)
                val secMonth = Utils.convertNumToMonth(data[1][1], len)
                val string = "от ${data[0][0]} ${firstMonth} ${data[0][2]} до ${data[1][0]} ${secMonth} ${data[1][2]}"
                Log.d("TAG", string)
                return string
            }
        }
        else {
            if (format == NUM_FORMAT)
                return "${data[0][0]}-${data[0][1]}-${data[0][2]}"
            else if (format == TEXT_FORMAN) {
                val FirstMonth = Utils.convertNumToMonth(data[0][1], len)
                val string = "${data[0][0]} ${FirstMonth} ${data[0][2]}"
                Log.d("TAG", string)
                return string
            }
        }
    }
    else if (len == ENG) {
        if (data.size == 2) {
            //значит промежуток
            if (format == NUM_FORMAT)
                return "${data[0][0]}-${data[0][1]}-${data[0][2]} for ${data[1][0]}-${data[1][1]}-${data[1][2]}"
            else if (format == TEXT_FORMAN) {
                val firstMonth = Utils.convertNumToMonth(data[0][1], len)
                val secMonth = Utils.convertNumToMonth(data[1][1], len)
                val string = "${data[0][0]} ${firstMonth} ${data[0][2]} for ${data[1][0]} ${secMonth} ${data[1][2]}"
                return string
            }
            else
                return ""
        }
        else {
            if (format == NUM_FORMAT)
                return "${data[0][0]}-${data[0][1]}-${data[0][2]}"
            else if (format == TEXT_FORMAN) {
                val FirstMonth = Utils.convertNumToMonth(data[0][1], len) //ToDo тут заменить на английский перевод
                val string = "${data[0][0]} ${FirstMonth} ${data[0][2]}"
                return string
            }
        }
        return ""
    }*/
    }
}

//Class to convert MutableList <-> String
class Converters {

    var utils = Utils()

    @TypeConverter
    fun listToSting(value: MutableList<Day>?): String {

        lateinit var str : String

        //Log.d("TAG1", value!![0].day.toString() + value[0].month.toString() + value[0].year.toString())

        if(value!!.size == 1) {
            str = value[0].day.toString() + ' ' + utils.convertNumToMonth(value[0].month, 0) + ' ' + value[0].year.toString()
        } else {
            str = value[0].day.toString() + ' ' + utils.convertNumToMonth(value[0].month, 0) + ' ' +  value[0].year.toString() + '-' +
                    value[1].day.toString() + ' ' +  utils.convertNumToMonth(value[1].month, 0) + ' ' +  value[1].year.toString()
        }

        return str
    }

    @TypeConverter
    fun stringToList(value: String): ArrayList<Day>? {

        lateinit var list : MutableList<Day>

        if(value.contains('-')) {
            //ToDo Доделать для промежутков
            list = arrayListOf(Day(1, 1, 1), Day(1, 1, 1))
        } else {
            if(value.split(' ').size < 3) {
                list = arrayListOf(Day(1, 1, 1))
                return list
            }
            val day = value.split(' ')[0].toInt()
            val month = utils.convertMonth(value.split(' ')[1])
            val year = value.split(' ')[2].toInt()
            //Log.d("TAG1", value.split(' ')[0] + value.split(' ')[1] + value.split(' ')[2])
            list = arrayListOf(Day(day, month, year))
        }

        return list
    }
}