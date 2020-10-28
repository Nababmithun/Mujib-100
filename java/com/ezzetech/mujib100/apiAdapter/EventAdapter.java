package com.ezzetech.mujib100.apiAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.ezzetech.mujib100.MujibApp;
import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.eventsApiModel.Datum;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context context;
    private List<Datum> eventList;

    MujibApp mujibApp = new MujibApp();

    public EventAdapter(Context context, List<Datum> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.event_item_layout, parent, false);

        return new EventViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        Datum event = eventList.get(position);
        holder.titleTV.setText(event.getTitleEn());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
            Date newDate = format.parse(event.getEventDate());
            format = new SimpleDateFormat("MMM dd, yyyy");
            String date = format.format(newDate);
            holder.eventDateTV.setText(date);

            if (System.currentTimeMillis() > newDate.getTime()) {
                holder.rsvpBtn.setVisibility(View.INVISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



        if (mujibApp.getSharedPrefValue() == 0) {
            holder.locationTV.setText(event.getLocation());
            holder.titleTV.setText(event.getTitleEn());
        } else if (mujibApp.getSharedPrefValue() == 1) {
            holder.locationTV.setText(event.getBnLocation());
            holder.titleTV.setText(event.getTitleBn());
        }

        Picasso.get().load(event.getLink()).into(holder.eventImageIV);
        holder.rsvpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTab(event.getLink());
            }
        });

    }

    public void openTab(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(url));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV, eventDateTV, locationTV;
        ImageView eventImageIV;
        Button rsvpBtn;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.titleTV);
            eventDateTV = itemView.findViewById(R.id.eventDateTV);
            locationTV = itemView.findViewById(R.id.locationTV);
            eventImageIV = itemView.findViewById(R.id.eventImageIV);
            rsvpBtn = itemView.findViewById(R.id.rsvpBtn);
        }
    }


}
