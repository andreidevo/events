package com.r.events.model

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException
import java.lang.Exception
import kotlin.concurrent.thread
var list : ArrayList<EventObject> = arrayListOf()
var filters : Filters = Filters("all", arrayListOf(arrayListOf()), "all","all" )
class PagesParse(private var model : Model) {

    //это сделать глобальным потом
    var Utils  = Utils()
    var CheckWithFilters = CheckWithFilters()

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

            try {
                doc = Jsoup.connect("https://it-events.com/").get()

                val div: Elements = doc.getElementsByClass("section")[0].getElementsByClass("event-list-item")

                //пробегаемся по всем записям
                for(z in  0 until  div.size) {
                    try {
                        val i = div[z]

                        //создаем объект мероприятия
                        val eventObject = EventObject()
                        //заполняем его

                        eventObject.setType(i.getElementsByClass("event-list-item__type").text())

                        val Element: Element = i.getElementsByClass("event-list-item__title")[0]
                        val href = Element.attr("href")
                        eventObject.setName(Element.text())
                        eventObject.setHref(href)
                        val dateStr = i.getElementsByClass("event-list-item__info")[0].text()

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
                            val check = i.getElementsByClass("event-list-item__info_online").text()
                            if( check != "")
                                eventObject.setOnline(true)
                            else
                                eventObject.setOnline(false)
                        }catch (e : Exception){
                            eventObject.setOnline(false) //LOOOOOOOL genius ! (idiot)
                        }

                        val arr = dateStr.split(' ')
                        //фильтруем
                        if (dateStr.contains('-')) {
                            val dayMin = arr[0].toInt()
                            val dayMax = arr[2].toInt()
                            val month = Utils.convertMonth(arr[3])
                            val year = arr[4].toInt()

                            val str = arrayListOf(arrayListOf(dayMin, month, year))
                            eventObject.setDate(str)
                            if( CheckWithFilters.check(eventObject))
                            {
                                eventObject.setType("0")
                                list.add(eventObject)
                                model.pushNotification(eventObject.getName(),
                                    dateStr,
                                    null
                                )
                            }

                        }
                        else {
                            val days = arr[0].toInt()
                            val month = Utils.convertMonth(arr[1])
                            val year = arr[2].toInt()

                            val str = arrayListOf(arrayListOf(days, month, year))
                            eventObject.setDate(str)
                            if( CheckWithFilters.check(eventObject))
                            {
                                list.add(eventObject)
                                model.pushNotification(
                                    eventObject.getName(),
                                    dateStr,
                                    null
                                )
                            }
                        }
                    } catch (e: Exception) { }
                }
                val x = 5;

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    fun dexigner()
    {

        thread {
            val doc: Document

            try {
                doc = Jsoup.connect("https://www.dexigner.com/design-events").get()
                val element : Element = doc.getElementById("agenda")
                val elements : Elements = element.getElementsByClass("event")

                for(i in 0 until elements.size)
                {
                    val eventObject = EventObject()
                    try{
                        eventObject.setDescription(elements[i].getElementsByTag("p")[0].text())
                        eventObject.setName(elements[i].getElementsByTag("h3")[0].text())
                        eventObject.setPhotoHref( "https://www.dexigner.com${ elements[i].getElementsByTag("img")[0].attr("data-src")}")
                        eventObject.setHref("https://www.dexigner.com${elements[i].getElementsByTag("a").attr("href")}")
                        eventObject.setLocation( elements[i].getElementsByClass("location").text())
                        eventObject.setType("1")
                        val date = elements[i].getElementsByTag("time").text()
                        //Nov 20 - Nov 22, 2019
                        //Nov 22, 2019 (in 14 days)

                        //ToDo добавить двойную дату
                        if(date.contains("ends"))
                        {
                            //ends Dec 14, 2019 (1 month left)
                            val arr = date.split(' ')
                            val month = Utils.convertMonth(arr[1])
                            val day = arr[2].dropLast(1).toInt()
                            val year = arr[3].toInt()
                            eventObject.setDate(arrayListOf(arrayListOf(day, month, year)))
                        }
                        else if(date.contains("-"))
                        {
                            //Nov 20 - Nov 22, 2019
                            val arr = date.split(",")
                            val year = arr[1].drop(1).toInt()
                            val dat = arr[0].split(" - ")
                            val fDate = dat[0].split(' ')
                            val sDate = dat[1].split(' ')
                            val fDay = fDate[1].toInt()
                            val sDay = sDate[1].toInt()
                            val fMonth = Utils.convertMonth(fDate[0])
                            val sMonth = Utils.convertMonth(sDate[0])
                            eventObject.setDate(arrayListOf(arrayListOf(fDay, fMonth, year), arrayListOf(sDay, sMonth, year)))
                        }
                        else
                        {
                            //Dec 14, 2019
                            val arr = date.split(",")
                            val year = arr[1].drop(1).toInt()
                            val dat = arr[0].split(' ')
                            val month = Utils.convertMonth(dat[0])
                            val day = dat[1].toInt()
                            eventObject.setDate(arrayListOf(arrayListOf(day, month, year)))
                        }

                        list.add(eventObject)
                    }catch (e : Exception){}
                }

                val opa = 1


                }catch (e : Exception) {}
        }

    }

}