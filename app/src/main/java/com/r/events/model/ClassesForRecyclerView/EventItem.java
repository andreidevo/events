package com.r.events.model.ClassesForRecyclerView;

import androidx.annotation.NonNull;

import com.r.events.Database.EventObject;

public class EventItem extends ListItem {

    @NonNull
    private EventObject event;

    public EventItem(@NonNull EventObject event) {
        this.event = event;
    }

    @NonNull
    public EventObject getEvent() {
        return event;
    }

    // here getters and setters
    // for title and so on, built
    // using event

    @Override
    public int getType() {
        return TYPE_EVENT;
    }
}
