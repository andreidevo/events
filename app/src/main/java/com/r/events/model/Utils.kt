package com.r.events.model

import android.content.Context
import android.widget.ImageView

class Utils {

    fun convertMonth(month : String) : Int
    {
        val mon = month.toLowerCase().substring(0,3)

        if( mon == "янв" || mon == "jan")
            return 1
        else if( mon == "фев" || mon == "feb")
            return 2
        else if( mon == "мар" || mon == "mar")
            return 3
        else if( mon == "апр" || mon == "apr")
            return 4
        else if( mon == "май" || mon == "may")
            return 5
        else if( mon == "июн" || mon == "jun")
            return 6
        else if( mon == "июл" || mon == "jul")
            return 7
        else if( mon == "авг" || mon == "aug")
            return 8
        else if( mon == "сеп" || mon == "sep")
            return 9
        else if( mon == "окт" || mon == "oct")
            return 10
        else if( mon == "ноя" || mon == "nov")
            return 1
        else if( mon == "дек" || mon == "dec")
            return 12

        return -1
    }

    val RUS : Int = 0
    val ENG : Int = 1

    fun convertNumToMonth( month : Int, len : Int) : String
    {
        if( len == RUS)
        {
            if( month == 1)
                return "янв"
            else if( month == 2)
                return "фев"
            else if( month == 3)
                return "марта"
            else if( month ==4)
                return "апр"
            else if( month ==5)
                return "мая"
            else if(month == 6)
                return "июня"
            else if( month == 7)
                return "июля"
            else if( month == 8)
                return "авг"
            else if( month == 9)
                return "сент"
            else if( month == 10)
                return "окт"
            else if( month == 11)
                return "нояб"
            else if( month == 12)
                return "дек"

            else
                return "null"
        }
        return "null_len"
    }

    fun interests( id : Int, len : Int) : String
    {
        if( len == RUS)
        {
            if( id == 0)
                return "программирование"
            else if( id == 1)
                return "дизайн"
            else
                return "null_id"

        }
        return "null_int"
    }
    fun type( id : Int, len : Int) : String
    {
        if( len == RUS)
        {
            if( id == 0)
                return "Курс"
            else if( id == 1)
                return "Вебинар"
            else if( id == 2)
                return "Конференция"
            else if( id == 3)
                return "Митап"
            else
                return "null_type"

        }
        return "null_len"
    }
    fun checkNotification(name : String?, date : String?) : Boolean
    {
        if( name == null || date == null)
            return false
        return true
    }
}