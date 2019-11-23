package com.r.events.model

import android.util.Log
import android.widget.Toast
import com.r.events.Database.EventObject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException
import java.sql.Date
import java.lang.Exception
import java.net.HttpRetryException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class PagesParse {

    //это сделать глобальным потом
    var Utils  = Utils()
    var CheckWithFilters = CheckWithFilters()

    //fun selector(p: EventObject): Long = getDataCode(p.dataList)
    suspend  fun getDataFromPage(){
        it_events()
        dexigner()
        //list.sortBy { selector(it)}
    }
    suspend fun it_events()
    {
        val doc: Document
        try {
            doc = Jsoup.connect("https://it-events.com/").get()
            val div: Elements = doc.getElementsByClass("section")[0].getElementsByClass("event-list-item")

            for(eventer in  0 until  div.size) {
                try {
                    val SomeElement = div[eventer]

                    val eventObject = EventObject()

                    eventObject.type = SomeElement.getElementsByClass("event-list-item__type").text()

                    val Element: Element = SomeElement.getElementsByClass("event-list-item__title")[0]
                    val href = Element.attr("href")
                    eventObject.name = Element.text()
                    eventObject.href = href
                    eventObject.sector = "0"


                    val dateStr = SomeElement.getElementsByClass("event-list-item__info")[0].text()
                    val photoh = SomeElement.getElementsByClass("event-list-item__image")[0]
                    val photoH = photoh.attr("style")
                    val idx1 = 22
                    val idx2 = photoH.indexOf('.') + 4

                    eventObject.photoHref = "https://it-events.com${photoH.substring(idx1, idx2)}"
                    try {
                        eventObject.location = SomeElement.getElementsByClass("event-list-item__info_location").text()
                    }catch (e : Exception){ }

                    try {
                        val check = SomeElement.getElementsByClass("event-list-item__info_online").text()
                        if( check != "")
                            eventObject.online = true
                        else
                            eventObject.online = false
                    }catch (e : Exception){ }

                    val arr = dateStr.split(' ')

                    if (dateStr.contains('-')) {
                        if(arr.size  == 7)
                        {
                            val dayMin = arr[0].toInt()
                            val month = Utils.convertMonth(arr[1])
                            val year = arr[2].toInt()

                            eventObject.date = mutableListOf(Day(dayMin, month, year))
                            //if( CheckWithFilters.check(eventObject))
                            //arrayStr = mutableListOf(Day(year, month, dayMin))
                            //eventObject.date = arrayStr
                        }
                        else
                        {
                            list.add(eventObject)
                            val dayMin = arr[0].toInt()
                            val month = Utils.convertMonth(arr[3])
                            val year = arr[4].toInt()

                            eventObject.date = mutableListOf(Day(dayMin, month, year))
                        }

                        list.add(eventObject)
                    }
                    else {
                        val days = arr[0].toInt()
                        val month = Utils.convertMonth(arr[1])
                        val year = arr[2].toInt()

                        eventObject.date = mutableListOf(Day(days, month, year))

                        //ToDo тут фильтр будет
                        list.add(eventObject)

                    }
                } catch (e: Exception) { }
            }
        } catch (e: IOException) { }

    }
    suspend fun dexigner()
    {

        val doc: Document
        try {
            doc = Jsoup.connect("https://www.dexigner.com/design-events").get()
            val elemnt : Element = doc.getElementById("agenda")
            val elements : Elements = elemnt.getElementsByClass("event")

            for(SomeElement in 0 until elements.size)
            {
                val eventObject = EventObject()
                try{
                    eventObject.description = elements[SomeElement].getElementsByTag("p")[0].text()
                    eventObject.name = elements[SomeElement].getElementsByTag("h3")[0].text()
                    eventObject.photoHref =  "https://www.dexigner.com${ elements[SomeElement].getElementsByTag("img")[0].attr("data-src")}"
                    eventObject.href = "https://www.dexigner.com${elements[SomeElement].getElementsByTag("a").attr("href")}"
                    eventObject.location = elements[SomeElement].getElementsByClass("location").text()
                    eventObject.sector = "1"
                    val date = elements[SomeElement].getElementsByTag("time").text()

                    //Nov 20 - Nov 22, 2019
                    //Nov 22, 2019 (in 14 days)

                    //ToDo добавить двойную дату
                    if(date.contains("ends"))
                    {
                        //ends Dec 14, 2019 (1 month left)
                        val arr = date.split(' ')
                        var month = Utils.convertMonth(arr[1])
                        val day = arr[2].dropLast(1).toInt()
                        var year = arr[3].toInt()
                        val FirstArr = Day(day, month, year)
                        var day2 = arr[4].drop(1).toInt() + day
                        var month2 = 0
                        if( arr[5].contains("month"))
                        {
                            month2 = month + day2
                            if( month2 > 12)
                            {
                                month2 -=12
                                year++
                            }

                            eventObject.date = mutableListOf(FirstArr, Day(day, month2, year))
                        }
                        else if( arr[5].contains("days"))
                        {
                            if( day2 > 29)
                            {
                                month++
                                day2 -= 29
                                if( month > 12)
                                {
                                    month -= 12
                                    year++
                                }
                            }
                            eventObject.date = mutableListOf(FirstArr, Day(day2, month2, year))
                        }

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
                        eventObject.date = mutableListOf(Day(fDay, fMonth, year), Day(sDay, sMonth, year))
                    }
                    else
                    {
                        //Dec 14, 2019
                        val arr = date.split(",")
                        val year = arr[1].drop(1).toInt()
                        val dat = arr[0].split(' ')
                        val month = Utils.convertMonth(dat[0])
                        val day = dat[1].toInt()
                        eventObject.date = mutableListOf(Day(day, month, year))
                    }


                    list.add(eventObject)
                }catch (e : Exception){}
            }
        }catch (e : Exception) {}


    }
    suspend fun hacktons()
    {
        //http://www.xn--80aa3anexr8c.xn--p1ai/
        val doc: Document
        try {
            doc = Jsoup.connect("http://www.xn--80aa3anexr8c.xn--p1ai/").get()
            val di: Element = doc.getElementsByClass("t776__container_mobile-grid")[1]
            val div : Elements = di.getElementsByClass("t776__col")

            for(eventer in  0 until  div.size) {
                try {
                    val SomeElement = div[eventer]

                    val eventObject = EventObject()
                    //teg a - href
                    //t776__title - titile
                    //t776__desrc - descr -> strong - date
                    //t776__imgwrapper - photoHref


                    //10 октября - 25 ноября, Онлайн Призовой фонд и призы. Призовой фонд и призы. Призовой фонд и призы.
                    eventObject.href =  (SomeElement.getElementsByTag("a")[0].attr("href"))
                    eventObject.name =  (SomeElement.getElementsByClass("t-name")[0].text())
                    val op = SomeElement.getElementsByClass("t-descr")[0].getElementsByTag("strong").text()
                    val p = op.substring(0, op.indexOf(',') + 1)
                    //parse !!!!!!
                    //eventObject.(arrayListOf(arrayListOf(0,1,2019)))
                    eventObject.photoHref= (SomeElement.getElementsByClass("t-img")[0].attr("src").replace("-/empty/", ""))

                    val x = 5;

                    list.add(eventObject)
                } catch (e: Exception) {
                    val x = 5;
                }
            }
        } catch (e: IOException) { }
    }

    fun getDataNormal(len: Int, format: Int, data : ArrayList<ArrayList<Int>>): String {
        val RUS: Int = 0
        val ENG: Int = 1

        val NUM_FORMAT: Int = 0 // 1-15-2019
        val TEXT_FORMAN: Int = 1 // 1 Nov 2019
        if (len == RUS) {
            if (data.size == 2) {
                //значит промежуток
                if (format == NUM_FORMAT)
                    return "от ${data[0][0]}-${data[0][1]}-${data[0][2]} до ${data[1][0]}-${data[1][1]}-${data[1][2]}"
                else if (format == TEXT_FORMAN) {
                    val firstMonth = Utils.convertNumToMonth(data[0][1], len)
                    val secMonth = Utils.convertNumToMonth(data[1][1], len)
                    val string = "от ${data[0][0]} ${firstMonth} ${data[0][2]} до ${data[1][0]} ${secMonth} ${data[1][2]}"
                    Log.d("TAG", string)
                    return string
                }
            }
            else {
                if (format == NUM_FORMAT)
                    return "${data[0][0]}-${data[0][1]}-${data[0][2]}"
                else if (format == TEXT_FORMAN) {
                    val FirstMonth = Utils.convertNumToMonth(data[0][1], len)
                    val string = "${data[0][0]} ${FirstMonth} ${data[0][2]}"
                    Log.d("TAG", string)
                    return string
                }
            }
        }
        else if (len == ENG) {
            if (data.size == 2) {
                //значит промежуток
                if (format == NUM_FORMAT)
                    return "${data[0][0]}-${data[0][1]}-${data[0][2]} for ${data[1][0]}-${data[1][1]}-${data[1][2]}"
                else if (format == TEXT_FORMAN) {
                    val firstMonth = Utils.convertNumToMonth(data[0][1], len)
                    val secMonth = Utils.convertNumToMonth(data[1][1], len)
                    val string = "${data[0][0]} ${firstMonth} ${data[0][2]} for ${data[1][0]} ${secMonth} ${data[1][2]}"
                    return string
                }
                else
                    return ""
            }
            else {
                if (format == NUM_FORMAT)
                    return "${data[0][0]}-${data[0][1]}-${data[0][2]}"
                else if (format == TEXT_FORMAN) {
                    val FirstMonth = Utils.convertNumToMonth(data[0][1], len) //ToDo тут заменить на английский перевод
                    val string = "${data[0][0]} ${FirstMonth} ${data[0][2]}"
                    return string
                }
            }
        }

        return ""
    }

    /*
    fun mergeSort(list : ArrayList<EventObject>) : ArrayList<EventObject>
    {
        if( list.size <= 1)
            return  list
        else
        {
            val middle = list.size / 2
            val left: List<EventObject> = list.subList(0, middle)
            val right : List<EventObject> = list.subList(middle , list.size)
            val ll = arrayListOf<EventObject>()
            val rr = arrayListOf<EventObject>()
            ll.addAll(left)
            rr.addAll(right)
            return merge(mergeSort(ll), mergeSort(rr))
        }
    }
    fun merge(left: ArrayList<EventObject>, right: ArrayList<EventObject>): ArrayList<EventObject> {
        var indexLeft = 0
        var indexRight = 0
        val newList : ArrayList<EventObject> = arrayListOf()
        while (indexLeft < left.count() && indexRight < right.count()) {
            var l = getDataCode(getArrayFromString(left[indexLeft].date!!))
            var r = getDataCode(getArrayFromString(right[indexRight].date!!))

            if ( l <= r) {
                newList.add(left[indexLeft])
                indexLeft++
            } else {
                newList.add(right[indexRight])
                indexRight++
            }
        }
        while (indexLeft < left.size) {
            newList.add(left[indexLeft])
            indexLeft++
        }
        while (indexRight < right.size) {
            newList.add(right[indexRight])
            indexRight++
        }
        return newList;
    }

    //ToDO не уверен насчет правильности
    fun getArrayFromString(str : String) : ArrayList<ArrayList<Int>> {
        val arr = str.split(' ')

        val year = arr[2].toInt()
        val month = utils.convertMonth(arr[1])
        val day = arr[0].toInt()

        var array : ArrayList<ArrayList<Int>> = arrayListOf(arrayListOf(day, month, year))

        return array
    }
*/
    fun getDataCode(list : ArrayList<ArrayList<Int>>?) : Long
    {
        val result = list!![0]
        val res = Date.valueOf("${result[2]}-${result[1] - 1}-${result[0]}")

        return res.time
        //17 0 2019  = 2036
        //17 1 2019 = 2136
        //17 11 2019 = 3136
        //17 11 2018 = 3135
        // 17 0 2000
    }
}