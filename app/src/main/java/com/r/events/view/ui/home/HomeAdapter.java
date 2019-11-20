package com.r.events.view.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.r.events.Database.EventObject;
import com.r.events.R;
import com.r.events.model.ClassesForRecyclerView.EventItem;
import com.r.events.model.ClassesForRecyclerView.HeaderItem;
import com.r.events.model.ClassesForRecyclerView.ListItem;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ListItem> events;
    private OnItemClickListener onItemClickListener;

    public HomeAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(EventObject eventObj);
    }

    public void setEvents(List<ListItem> notes) {
        events = notes;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case ListItem.TYPE_HEADER: {
                View itemView = inflater.inflate(R.layout.header, parent, false);
                viewHolder = new HeaderViewHolder(itemView);
                break;
            }
            case ListItem.TYPE_EVENT: {
                View itemView = inflater.inflate(R.layout.event_item, parent, false);
                viewHolder = new EventViewHolder(itemView, onItemClickListener);


                break;
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        final List<ListItem> ev = events;
        int viewType = ev.get(position).getType();

        switch (viewType) {
            case ListItem.TYPE_HEADER: {
                break;
            }
            case ListItem.TYPE_EVENT: {
                final EventItem event = (EventItem) ev.get(position);
                final EventObject eventObg = event.getEvent();
                EventViewHolder holder = (EventViewHolder) viewHolder;

                holder.date.setText(eventObg.getDate());
                holder.title.setText(eventObg.getName());

                /**
                 * Ищем количество Header-ов ДО нашего елемента что-бы получить нормальный индекс
                 */
                int numToDel = 0;
                for(int i=0; i<events.size(); i++) {
                    if(ev.get(i).getType() == ListItem.TYPE_HEADER)
                        numToDel++;
                }

                holder.position = position - numToDel;
                holder.eventObj = eventObg;
                Glide.with(context).load(eventObg.getPhotoHref()).into(holder.img);
                break;
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public int getItemCount() {
        if(events == null)
            return 0;
        return events.size();
    }

    @Override
    public int getItemViewType(int position) {
        return events.get(position).getType();
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView txt_header;

        HeaderViewHolder(View itemView) {
            super(itemView);
            txt_header = itemView.findViewById(R.id.header_txt);
        }

    }

    private static class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView date;
        TextView title;
        ImageView img;
        int position;
        EventObject eventObj;

        OnItemClickListener onEventListener;

        EventViewHolder(View itemView, OnItemClickListener onEventListener) {
            super(itemView);
            img = itemView.findViewById(R.id.event_image);
            date = itemView.findViewById(R.id.event_date);
            title = itemView.findViewById(R.id.event_title);

            this.onEventListener = onEventListener;
            ImageButton imgButton = itemView.findViewById(R.id.item_image_button);
            imgButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onEventListener.onItemClick(eventObj);
        }
    }
}
