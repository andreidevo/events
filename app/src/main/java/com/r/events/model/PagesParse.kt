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
        val dates = filters.getDate()
        //максимальная дата из поиска
        val maxdate = dates[dates.size - 1][0]
        //минимальная дата из посика
        val mindate = dates[0][0]

        thread {
            val doc: Document
            //создаем объект мероприятия
            val eventObject = EventObject()
            //заполняем его
            try {
                doc = Jsoup.connect("https://it-events.com/").get()

                val div: Elements = doc.getElementsByClass("section")[0].getElementsByClass("event-list-item")

                //пробегаемся по всем записям
                for(z in  0 until  div.size) {
                    try {
                        val i = div[z]
                        eventObject.setType(i.getElementsByClass("event-list-item__type").text())

                        val Element: Element = i.getElementsByClass("event-list-item__title")[0]
                        val href = Element.attr("href")
                        eventObject.setName(Element.text())
                        eventObject.setHref(href)
                        eventObject.setDate(i.getElementsByClass("event-list-item__info")[0].text())

                        val photoh = i.getElementsByClass("event-list-item__image")[0]
                        val photoH = photoh.attr("style")
                        val idx1 = 22
                        val idx2 = photoH.indexOf('.') + 4
                        eventObject.setPhotoHref(
                            "https://it-events.com${photoH.substring(idx1, idx2)}"
                        )
                        try {
                            eventObject.setLocation(i.getElementsByClass("event-list-item__info_location").text())
                        }catch (e : Exception){}

                        try {
                            var check = i.getElementsByClass("event-list-item__info_online").text()
                            eventObject.setOnline(true)
                        }catch (e : Exception){
                            eventObject.setOnline(false) //LOOOOOOOL genius !
                        }

                        //val arr = eventObject.getDate().split(' ')
                        //фильтруем
                        //var key = 1
//                        if (eventObject.getDate().contains('-')) {
//                            val dayMin = arr[0].toInt()
//                            val dayMax = arr[2].toInt()
//                            val month = Utils.convertMonth(arr[3])
//                            val year = arr[4].toInt()
//
//
//                            if (dayMin < mindate)
//                                continue
//                            if (dayMax > maxdate)
//                                break
//
//                            for (p in filters.getDate())
//                                if (p[0] in dayMin..dayMax && p[1] == month && p[2] == year) {
//                                    key = 0
//                                    break
//                                }
//                        }
//                        else {
//                            val days = arr[0].toInt()
//                            val month = Utils.convertMonth(arr[1])
//                            val year = arr[2].toInt()
//
//                            if (days < mindate)
//                                continue
//                            if (days > maxdate)
//                                break
//                            for (p in filters.getDate())
//                                if (p[0] == days && p[1] == month && p[2] == year) {
//                                    key = 0
//                                    break
//                                }
//                        }

                        list.add(eventObject)
                        model.pushNotification(
                            eventObject.getName(),
                            eventObject.getDate(),
                            null
                        )

                    } catch (e: Exception) { }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


}