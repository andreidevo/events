package com.r.events.model.ClassesForRecyclerView;

import androidx.annotation.NonNull;

public class HeaderItem extends ListItem {

    @NonNull
    private String sector;

    public HeaderItem(@NonNull String sector) {
        this.sector = sector;
    }

    @NonNull
    public String getSector() {
        return sector;
    }

    // here getters and setters
    // for title and so on, built
    // using date

    @Override
    public int getType() {
        return TYPE_HEADER;
    }
}
