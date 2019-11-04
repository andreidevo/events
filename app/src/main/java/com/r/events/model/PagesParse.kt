package com.r.events.model

import com.r.events.Model
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException
import java.lang.Exception
import java.security.Key
import java.util.ArrayList
import kotlin.concurrent.thread

class PagesParse(model: Model) {

    var model : Model? = null

    init {
        this.model =model;
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

                var div: Elements = doc.getElementsByClass("col-10")
                var id = (1 until div.size).random()

                try {
                    eventObject.setType(div[id].getElementsByClass("event-list-item__type").text())
                }catch (e : Exception){}

                try {
                    eventObject.setName(div[id].getElementsByClass("event-list-item__title").text())
                }catch (e : Exception){}

                try {
                    eventObject.setDate(div[id].getElementsByClass("event-list-item__info")[0].text())
                }catch (e : Exception){}

                //отправляем уведомление
                model!!.pushNotification(eventObject.getName(), eventObject.getDate(), null)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}