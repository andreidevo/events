package com.r.events.model

import java.sql.Date
import java.util.*
import kotlin.collections.ArrayList

class EventObject ( private var name : String?= null,
                    private var date : ArrayList<ArrayList<Int>>?= null,
                    private var type : String?= null,
                    private var sector : Int? = null,
                    private var location : String? = null,
                    private var online : Boolean?  = null,
                    private var href : String? = null,
                    private var photoHref : String? = null,
                    private var description : String? = null

) {
    val RUS: Int = 0
    val ENG: Int = 1

    val NUM_FORMAT: Int = 0 // 1-15-2019
    val TEXT_FORMAN: Int = 1 // 1 Nov 2019


    fun setSector( sector : Int)
    {
        this.sector = sector
    }
    fun getSector() : Int
    {
        return  this.sector!!
    }
    fun setDescription(des: String) {
        this.description = des;
    }

    fun getDescription(): String {
        return this.description!!
    }

    fun setPhotoHref(href: String) {
        this.photoHref = href;
    }

    fun getPhotoHref(): String {
        return this.photoHref!!
    }

    fun setName(name: String) {
        this.name = name;
    }

    fun setDate(date: ArrayList<ArrayList<Int>>?) {
        this.date = date
    }

    fun setType(type: String) {
        this.type = type
    }

    fun setLocation(location: String) {
        this.location = location
    }

    fun setOnline(online: Boolean) {
        this.online = online
    }

    fun setHref(href: String) {
        this.href = href;
    }

    fun getName(): String {
        return this.name!!;
    }

    fun getType(): String {
        return this.type!!;
    }

    fun getDate(): ArrayList<ArrayList<Int>>? {
        return this.date!!
    }

    fun getHref(): String {
        return this.href!!
    }

    fun getOnline(): Boolean {
        return this.online!!
    }


    fun getDataNormal(len: Int, format: Int): String {
        val data = this.date!!
        if (len == RUS) {
            if (data.size == 2) {
                //значит промежуток
                if (format == NUM_FORMAT)
                    return "от ${data[0][0]}-${data[0][1]}-${data[0][2]} до ${data[1][0]}-${data[1][1]}-${data[1][2]}"
                else if (format == TEXT_FORMAN) {
                    val firstMonth = utils.convertNumToMonth(data[0][1], len)
                    val secMonth = utils.convertNumToMonth(data[1][1], len)
                    val string = "от ${data[0][0]} ${firstMonth} ${data[0][2]} до ${data[1][0]} ${secMonth} ${data[1][2]}"
                    return string
                }
            }
            else {
                if (format == NUM_FORMAT)
                    return "${data[0][0]}-${data[0][1]}-${data[0][2]}"
                else if (format == TEXT_FORMAN) {
                    val FirstMonth = utils.convertNumToMonth(data[0][1], len)
                    val string = "${data[0][0]} ${FirstMonth} ${data[0][2]}"
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
                    val firstMonth = utils.convertNumToMonth(data[0][1], len)
                    val secMonth = utils.convertNumToMonth(data[1][1], len)
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
                    val FirstMonth = utils.convertNumToMonth(data[0][1], len) //ToDo тут заменить на английский перевод
                    val string = "${data[0][0]} ${FirstMonth} ${data[0][2]}"
                    return string
                }
            }
        }

        return ""
    }
    fun getDataCode() : Long
    {
        val result = this.date!![0]
        val res = Date.valueOf("${result[2]}-${result[1] - 1}-${result[0]}")

        return res.time
        //17 0 2019  = 2036
        //17 1 2019 = 2136
        //17 11 2019 = 3136
        //17 11 2018 = 3135
        // 17 0 2000
    }

}