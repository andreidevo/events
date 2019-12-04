package com.r.events.model

import com.r.events.Database.EventObject
import com.varunest.sparkbutton.SparkButton

var buttonsfilters : buttonsFilter  = buttonsFilter()
val utils = Utils()
var listfilters : ArrayList<EventObject> = arrayListOf()
var list : ArrayList<EventObject> = arrayListOf()
var filters : Filters = Filters("all", arrayListOf(arrayListOf()), arrayListOf(),"all", false, arrayListOf() )
var parseChecker : Boolean = false //проверка для того, чтобы не парсить сайты 2 раза
