package com.r.events.model

/*
    Day - класс для хранения информации о дне проведения мероприятия
    .AddInterval() - Добавление промежутка
    .getInterval() - Возвращает интервал (первый и второй день)
    .getSecondDay() - Возвращает второй день из промежутка
    .getFirstDay() - Возвращает первый день из промежутка
    .getSFirstDay() - Возвращает строковой формат первой строки

*/
class Day(val day : Int? = null, val month : Int? = null, val year: Int? = null)
{
    private var secondDay : Day? = null

    fun addInterval(day : Int, month : Int, year: Int)
    {
        this.secondDay = Day(day, month, year)
    }
    fun getInterval() : ArrayList<Day>?
    {
        if( secondDay != null)
            return arrayListOf(Day(day, month, year), secondDay!!)
        else
            return null
    }
    fun getSecondDay() : Day?
    {
        if( secondDay != null)
            return secondDay
        else
            return null

    }
    fun getFirstDay() : Day
    {
        return Day(day, month, year)
    }
    /*
        Type - Формат возвращаемой строки.
        0 - 15 Янв 2019
        1 - 15-01-2019
    */
    fun getSFirstDay(type : Int, len : Int) : String
    {
            if(type == 0)
                return "$day ${utils.convertNumToMonth((month!!), len)} $year"
            else if( type == 1)
                return "$day-$month-$year"
            else
                return "null"

    }
    fun getSSecondDay(type : Int, len : Int) : String
    {
        if(type == 0)
            return "${secondDay?.day} ${utils.convertNumToMonth(secondDay!!.month!!, len)} ${secondDay?.year}"
        else if( type == 1)
            return "${secondDay?.day}-${secondDay?.month}-${secondDay?.year}"
        else
            return "null"
    }
    /*
        Простой метод для использования, чтобы получить нормальную строковую дату
        Отличие от другие - не надо проверять на наличие промежутка
    */
    fun getSimpleDate(type : Int, len : Int) : String
    {
        if( secondDay == null)
            return getSFirstDay(type, len)
        else
            return getSFirstDay(type, len) + " - " + getSSecondDay(type, len)
    }


}

