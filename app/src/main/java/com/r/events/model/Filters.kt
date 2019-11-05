package com.r.events.model

class Filters ( private var interests : ArrayList<Int>?= null,
                    private var date : ArrayList<ArrayList<Int>>?= null,
                    private var type : String?= null,
                    private var location : String? = null,
                    private var online : Boolean?  = null) {

    fun setName(interests : ArrayList<Int>)
    {
        this.interests = interests
    }
    //day, month, year
    fun setDate( date : ArrayList<ArrayList<Int>>)
    {
        this.date = date
    }
    fun setType( type : String)
    {
        this.type = type
    }
    fun setLocation ( location: String)
    {
        this.location = location
    }
    fun setOnline ( online : Boolean)
    {
        this.online = online
    }
    fun getInterests() : ArrayList<Int>
    {
        return this.interests!!
    }
    fun getType() : String
    {
        return this.type!!;
    }
    fun getDate() : ArrayList<ArrayList<Int>>
    {
        return this.date!!;
    }

}