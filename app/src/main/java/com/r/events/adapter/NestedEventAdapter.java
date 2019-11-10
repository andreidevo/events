package com.r.events.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.r.events.R;
import com.r.events.model.EventObject;

import java.util.ArrayList;
//Nested
public class NestedEventAdapter extends RecyclerView.Adapter<NestedEventAdapter.TestHolder> {
    private Context context;
    private ArrayList<EventObject> events;

    public NestedEventAdapter(Context context, ArrayList<EventObject> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public TestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);

        return new TestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestHolder holder, int position) {
        EventObject event = events.get(position);
        holder.date.setText(event.getDataNormal(event.getRUS(), event.getTEXT_FORMAN()));
        holder.title.setText(event.getName());
        Glide.with(context).load(event.getPhotoHref()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class TestHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView date;
        private TextView title;


        public TestHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.event_image);
            date = itemView.findViewById(R.id.event_date);
            title = itemView.findViewById(R.id.event_title);
        }
    }
}
