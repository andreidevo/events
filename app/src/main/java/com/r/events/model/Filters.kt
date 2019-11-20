package com.r.events.model

data class Filters (var name : String?= null,
                    var date : ArrayList<ArrayList<Int>>?= null,
                    var type : ArrayList<Int>? = null,
                    var location : String? = null,
                    var online : Boolean?  = null,
                    var sector : ArrayList<Int>? = null)