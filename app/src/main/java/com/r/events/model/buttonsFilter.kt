package com.r.events.model

class buttonsFilter (var theme_programming : Int = 0,
                     var theme_design : Int = 0,
                     var type_hackton : Int = 0)
{
    fun setprogramming(value : Int)
    {
        this.theme_programming = value
    }
    fun setTypeHackton(value : Int)
    {
        this.type_hackton = value
    }
}