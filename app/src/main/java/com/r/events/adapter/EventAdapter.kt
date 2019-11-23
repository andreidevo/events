package com.r.events.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.r.events.R
import com.r.events.Database.EventObject

import java.util.ArrayList

class EventAdapter(private val context: Context, private val events: ArrayList<EventObject>) :
    RecyclerView.Adapter<EventAdapter.EventHolder>() {

    private var id = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false)

        return EventHolder(view)
    }

    //test

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        val eventObject = events[position]

        holder.eventDate.text = eventObject.getDataNormal(0, 0)
        Glide.with(context).load(eventObject.photoHref).into(holder.eventImage)
        holder.eventTitle.text = eventObject.name
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun getItemId(position: Int): Long {

        return position.toLong()
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class EventHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val eventTitle: TextView = itemView.findViewById(R.id.event_title)
        internal val eventDate: TextView = itemView.findViewById(R.id.event_date)
        internal val eventImage: ImageView = itemView.findViewById(R.id.event_image)

    }
}
