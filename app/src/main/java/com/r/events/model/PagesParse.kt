package com.r.events.model

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException
import java.lang.Exception
import java.net.HttpRetryException
import kotlin.concurrent.thread
var list : ArrayList<EventObject> = arrayListOf()
var filters : Filters = Filters("all", arrayListOf(arrayListOf(8,10,2019), arrayListOf(9,10,2019)), "all","all" )
class PagesParse(private var model : Model) {

    var Utils  = Utils()

    fun it_events()
    {

        //оптимизируем поиск по всем страницам сайта
        var dates = filters.getDate()
        //максимальная дата из поиска
        var maxdate = dates[dates.size - 1][1]
        //минимальная дата из посика
        var mindate = dates[0][1]

        thread {
            val doc: Document
            //создаем объект мероприятия
            val eventObject = EventObject()
            //заполняем его
            try {
                doc = Jsoup.connect("https://it-events.com/").get()

                val div: Elements = doc.getElementsByClass("section")
                //пробегаемся по всем записям
                for(z in  0 until  div.size)
                {
                    try {

                        val i = div[z]
                        eventObject.setType(i.getElementsByClass("event-list-item__type").text())

                        val Element : Element = i.getElementsByClass("event-list-item__title")[0]
                        val href = Element.attr("href")
                        eventObject.setName(Element.text())
                        eventObject.setHref(href)
                        eventObject.setDate(i.getElementsByClass("event-list-item__info")[0].text())
                        val arr = eventObject.getDate().split(' ')


                        //фильтруем
                        var key = 1
                        if( eventObject.getDate().contains('-'))
                        {
                            val dayMin = arr[0].toInt()
                            val dayMax = arr[2].toInt()
                            val month = Utils.convertMonth(arr[3])
                            val year = arr[4].toInt()

                            if( dayMin < mindate || dayMax > maxdate)
                                continue
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
                            val month = Utils.convertMonth(arr[1])
                            val year = arr[2].toInt()

                            if( days < mindate || days > maxdate)
                                continue

                            for(p in filters.getDate())
                                if( p[0] == days && p[1] == month && p[2] == year)
                                {
                                    key = 0
                                    break
                                }
                        }

                        //отправляем уведомление
                        if( key == 0)
                        {
                            list.add(eventObject)
                            model.pushNotification(eventObject.getName(), eventObject.getDate(), null)
                        }
                    }catch (e : Exception){}

                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


}