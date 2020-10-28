package com.ezzetech.mujib100.apiAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.ezzetech.mujib100.MujibApp;
import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.pressRelease.Datum;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PressReleaseAdapter extends RecyclerView.Adapter<PressReleaseAdapter.PressReleaseViewHolder> {

    private Context context;
    private List<Datum> pressReleaseList;
    MujibApp mujibApp = new MujibApp();

    public PressReleaseAdapter(Context context, List<Datum> pressReleaseList) {
        this.context = context;
        this.pressReleaseList = pressReleaseList;
    }

    @NonNull
    @Override
    public PressReleaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.press_release_item,parent,false);
       return new PressReleaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PressReleaseViewHolder holder, int position) {

        Datum pressRelease = pressReleaseList.get(position);

        if (mujibApp.getSharedPrefValue()==0){
            holder.titleTV.setText(pressRelease.getTitleEn());
        }else if (mujibApp.getSharedPrefValue()==1){
            holder.titleTV.setText(pressRelease.getTitleBn());
        }

        //holder.dateTV.setText(pressRelease.getDate());
        Picasso.get().load(pressRelease.getLink()).into(holder.pressImageIV);

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date newDate = format.parse(pressRelease.getDate());
            format = new SimpleDateFormat("dd MMM, yyyy");
            String date = format.format(newDate);
            holder.dateTV.setText(date);



        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTab(pressRelease.getNewsUrl());
            }
        });
    }

    public void openTab(String url){
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(url));
    }

    @Override
    public int getItemCount() {
        return pressReleaseList.size();
    }

    public class PressReleaseViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTV, dateTV;
        private ImageView pressImageIV;
        public PressReleaseViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.pressTitleTV);
            dateTV = itemView.findViewById(R.id.pressDateTV);
            pressImageIV = itemView.findViewById(R.id.pressImageIV);

        }
    }
}
