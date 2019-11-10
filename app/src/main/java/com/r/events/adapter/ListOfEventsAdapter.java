package com.r.events.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.r.events.R;
import com.r.events.model.EventObject;
import com.r.events.model.Utils;

import java.util.ArrayList;

public class ListOfEventsAdapter extends RecyclerView.Adapter<ListOfEventsAdapter.TestEventHolder> {
    private Context context;
    private ArrayList<EventObject> events;
    private Utils utils = new Utils();

    public ListOfEventsAdapter(Context context, ArrayList<EventObject> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public TestEventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nested_list_of_event_items, parent, false);

        return new TestEventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestEventHolder holder, int position) {
        if (position == 0) holder.title.setText("Программирование");
        else holder.title.setText("Дизайн");
        holder.nested.setLayoutManager(new LinearLayoutManager(context));
        holder.nested.setAdapter(new NestedEventAdapter(context, events));
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class TestEventHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private RecyclerView nested;

        public TestEventHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_of_card);
            nested = itemView.findViewById(R.id.nested);
        }
    }
}
