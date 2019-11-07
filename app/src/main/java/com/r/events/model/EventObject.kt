package com.r.events.model

class EventObject ( private var name : String?= null,
                    private var date : String?= null,
                    private var type : String?= null,
                    private var location : String? = null,
                    private var online : Boolean?  = null,
                    private var href : String? = null,
                    private var photoHref : String? = null,
                    private var desctiption : String? = null

) {

    fun setdesctiption( des : String)
    {
        this.desctiption = des;
    }
    fun getdesctiption() : String
    {
        return this.desctiption!!
    }
    fun setPhotoHref(href : String)
    {
        this.photoHref = href;
    }
    fun getPhotoHref() : String
    {
        return this.photoHref!!
    }
    fun setName(name : String)
    {
        this.name = name;
    }
    fun setDate( date : String)
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
    fun setHref( href : String)
    {
        this.href = href;
    }
    fun getName() : String
    {
        return this.name!!;
    }
    fun getType() : String
    {
        return this.type!!;
    }
    fun getDate() : String
    {
        return this.date!!;
    }
    fun getHref():String
    {
        return this.href!!
    }

}