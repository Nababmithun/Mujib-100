package com.ezzetech.mujib100.apiAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezzetech.mujib100.MujibApp;
import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.activity.SpeechesDetailsActivity;
import com.ezzetech.mujib100.speechesApiModel.Datum;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SpeechesAdapter extends RecyclerView.Adapter<SpeechesAdapter.SpeechesViewHolder> {

    private Context context;
    private List<Datum> videoList;

    MujibApp mujibApp = new MujibApp();

    public SpeechesAdapter(Context context, List<Datum> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public SpeechesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.speeches_item_layout,parent,false);

        return new SpeechesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeechesViewHolder holder, int position) {

        Datum speeches = videoList.get(position);

        if (mujibApp.getSharedPrefValue()==0){
            holder.captionTV.setText(speeches.getCaptionEn());
        }
        else if (mujibApp.getSharedPrefValue()==1){
            holder.captionTV.setText(speeches.getCaptionBn());
        }

        Picasso.get().load(speeches.getLink()).into(holder.thumbleIV);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SpeechesDetailsActivity.class);
                intent.putExtra("speeches",speeches);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class SpeechesViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbleIV;
        private TextView captionTV;
        public SpeechesViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbleIV = itemView.findViewById(R.id.thumbleIV);
            captionTV = itemView.findViewById(R.id.captionTV);
        }
    }
}
