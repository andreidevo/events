package com.r.events.model

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException
import java.lang.Exception
import java.net.HttpRetryException
import kotlin.concurrent.thread

private var list : ArrayList<EventObject> = arrayListOf()
private var filters : Filters = Filters(arrayListOf(0, 1), arrayListOf(arrayListOf(5, 10, 2019), arrayListOf(6, 10, 2019)), "all","all" )
class PagesParse(private var model : Model) {

    var Utils = Utils()
    fun it_events()
    {
        thread {
            val doc: Document
            //создаем объект мероприятия
            val eventObject = EventObject()
            //заполняем его
            try
            {
                doc = Jsoup.connect("https://it-events.com/").get()
                val div: Elements = doc.getElementsByClass("col-10")

                for(z in  1 until  div.size)
                {
                    val i = div[z]
                    try
                    {
                        eventObject.setType(i.getElementsByClass("event-list-item__type").text())
                    }catch (e : Exception){}

                    try
                    {
                        var Element : Element = i.getElementsByClass("event-list-item__title")[0];
                        var href = Element.attr("href")

                        eventObject.setName(Element.text())
                        eventObject.setHref(href)
                    }catch (e : Exception){}

                    try
                    {
                        eventObject.setDate(i.getElementsByClass("event-list-item__info")[0].text())
                        var arr = eventObject.getDate().split(' ')

                        //фильтруем
                        var key = 1
                        if( eventObject.getDate().contains('-'))
                        {
                            val days =  arr[0].split('-')
                            val dayMin = arr[0].toInt()
                            val dayMax = arr[2].toInt()
                            val month = Utils.convertMonth(arr[3])
                            val year = arr[4].toInt()
                            for(p in filters.getDate())
                                if( p[0] >= dayMin && p[0] <= dayMax && p[1] == month && p[2] == year)
                                {
                                    key = 0
                                    break
                                }
                        }
                        else
                        {
                            val days = arr[0].toInt()
                            var month = Utils.convertMonth(arr[1])
                            var year = arr[2].toInt()
                            for(p in filters.getDate())
                                if( p[0] == days && p[1] == month && p[2] == year)
                                {
                                    key = 0
                                    break
                                }
                        }

                        //добавляем в главный список
                        if( key == 0)
                        {
                            list.add(eventObject)
                            //обновляем recyclerView
                        }

                    }catch (e : Exception){}
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}