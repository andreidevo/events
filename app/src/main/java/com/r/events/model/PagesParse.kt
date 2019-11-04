package com.r.events.model

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException
import java.lang.Exception
import java.net.HttpRetryException
import kotlin.concurrent.thread
private var filters : Filters = Filters("all", arrayListOf(arrayListOf(5,10,2019), arrayListOf(6,10,2019)), "all","all" )
class PagesParse(model: Model) {

    var model : Model? = null

    init {
        this.model =model;
    }


    fun convertMonth(month : String) : Int
    {
        var mon = month.toLowerCase().substring(0,3)

        if( mon == "янв" || mon == "jan")
            return 0
        else if( mon == "фев" || mon == "feb")
            return 1;
        else if( mon == "мар" || mon == "mar")
            return 2;
        else if( mon == "апр" || mon == "apr")
            return 3;
        else if( mon == "май" || mon == "may")
            return 4;
        else if( mon == "июн" || mon == "jun")
            return 5;
        else if( mon == "июл" || mon == "jul")
            return 6;
        else if( mon == "авг" || mon == "aug")
            return 7;
        else if( mon == "сеп" || mon == "sep")
            return 8;
        else if( mon == "окт" || mon == "oct")
            return 9;
        else if( mon == "ноя" || mon == "nov")
            return 10;
        else if( mon == "дек" || mon == "dec")
            return 11

        return -1;
    }
    fun it_events()
    {
        thread {
            val doc: Document
            //создаем объект мероприятия
            val eventObject = EventObject()
            //заполняем его
            try {
                doc = Jsoup.connect("https://it-events.com/").get()

                val div: Elements = doc.getElementsByClass("col-10")
                for(z in  1 until  div.size)
                {
                    val i = div[z]
                    try {
                        eventObject.setType(i.getElementsByClass("event-list-item__type").text())
                    }catch (e : Exception){}

                    try {
                        var Element : Element = i.getElementsByClass("event-list-item__title")[0];
                        var href = Element.attr("href")

                        eventObject.setName(Element.text())
                        eventObject.setHref(href)
                        var ff = 5;
                    }catch (e : Exception){}

                    try {
                        eventObject.setDate(i.getElementsByClass("event-list-item__info")[0].text())
                        var arr = eventObject.getDate().split(' ')


                        //фильтруем
                        var key = 1
                        if( eventObject.getDate().contains('-'))
                        {
                            val days =  arr[0].split('-')
                            val dayMin = arr[0].toInt()
                            val dayMax = arr[2].toInt()
                            val month = convertMonth(arr[3])
                            val year = arr[4].toInt()
                            for(p in filters.getDate())
                                if( p[0] >= dayMin && p[0] <= dayMax && p[1] == month && p[2] == year)
                                {
                                    key = 0;
                                    break;
                                }
                        }
                        else
                        {
                            val days = arr[0].toInt()
                            var month = convertMonth(arr[1])
                            var year = arr[2].toInt()
                            for(p in filters.getDate())
                                if( p[0] == days && p[1] == month && p[2] == year)
                                {
                                    key = 0;
                                    break;
                                }
                        }

                        //отправляем уведомление
                        if( key == 0)
                            model!!.pushNotification(eventObject.getName(), eventObject.getDate(), null)
                    }catch (e : Exception){}

                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}