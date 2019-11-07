package com.r.events.model

class CheckWithFilters {

    fun check(obj : EventObject) : Boolean
    {
        val date = filters.getDate()[0]
        if( obj.getDate()!![0][0].toInt() == date[0] &&
            obj.getDate()!![0][1].toInt() == date[1] &&
            obj.getDate()!![0][2].toInt() == date[2] &&
            obj.getOnline() == filters.getOnline())
            return true
        return false
    }

}