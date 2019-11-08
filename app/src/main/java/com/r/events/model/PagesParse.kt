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
                        var key = 1
                        if (dateStr.contains('-')) {
                            val dayMin = arr[0].toInt()
                            val dayMax = arr[2].toInt()
                            val month = Utils.convertMonth(arr[3])
                            val year = arr[4].toInt()

//                            if (dayMin < mindate)
//                                continue
//                            if (dayMax > maxdate)
//                                break

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

//                            if (days < mindate)
//                                continue
//                            if (days > maxdate)
//                                break

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
    fun russian_hack( ) {

        //error
        thread {
            val doc: Document

            try {
                doc = Jsoup.connect("https://russianhackers.org/hackathons")
                    .maxBodySize(0)
                    .timeout(600000)
                    .get()

                val div: Elements =
                    doc.getElementsByClass("hackatons-week-block")

                val s = 5

                for(i in 0 until div.size)
                {
                    //hackathon-block-splitter
                    val eventObject  = EventObject()
                    val element : Elements = div[i].getElementsByClass("hackathon-block-splitter")
                    //create eventObj
                }

            } catch (e: Exception) { }
        }
        //https://beta.russianhackers.org/hackathons
    }
    fun dexigner()
    {

        thread {
            val doc: Document

            try {
                doc = Jsoup.connect("https://www.dexigner.com/design-events").get()
                val elemnt : Element = doc.getElementById("agenda")
                val elements : Elements = elemnt.getElementsByClass("event")

                for(i in 0 until elements.size)
                {
                    val eventObject = EventObject()
                    try{
                        eventObject.setdesctiption(elements[i].getElementsByTag("p")[0].text())
                        eventObject.setName(elements[i].getElementsByTag("h3")[0].text())
                        eventObject.setPhotoHref( "https://www.dexigner.com${ elements[i].getElementsByTag("img")[0].attr("data-src")}")
                        eventObject.setHref("https://www.dexigner.com${elements[i].getElementsByTag("a").attr("href")}")
                        eventObject.setLocation( elements[i].getElementsByClass("location").text())
                        eventObject.setType("1")
                        val date = elements[i].getElementsByTag("time").text()

                        list.add(eventObject)
                        val f = 5

                    }catch (e : Exception){}
                }

                val opa = 1


                }catch (e : Exception) {}
        }

    }

}