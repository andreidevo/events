package com.r.events.model

class CheckWithFilters {

    fun check(obj : EventObject) : Boolean
    {
        val date = filters.getDate()[0]
        if( obj.getDate()!![0][0] == date[0] &&
            obj.getDate()!![0][1] == date[1] &&
            obj.getDate()!![0][2] == date[2] &&
            obj.getOnline() == filters.getOnline())

            return true
        return false
    }

}