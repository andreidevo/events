package com.r.events.model

import com.r.events.Database.EventObject

val utils = Utils()
var list : ArrayList<EventObject> = arrayListOf()
var filters : Filters = Filters("all", arrayListOf(arrayListOf()), arrayListOf(),"all", false, arrayListOf() )
var parseChecker : Boolean = false //проверка для того, чтобы не парсить сайты 2 раза
class GlobalVariables {

}