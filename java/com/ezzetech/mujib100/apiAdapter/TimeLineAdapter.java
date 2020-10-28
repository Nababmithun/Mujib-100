package com.ezzetech.mujib100.apiAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezzetech.mujib100.MujibApp;
import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.activity.TimeLineDetailsActivity;
import com.ezzetech.mujib100.timeLineApiModel.Datum;
import com.ezzetech.mujib100.timeLineApiModel.TimelineImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {

    private Context context;
    private List<Datum> timelineList;

    MujibApp mujibApp = new MujibApp();

    private long highScore;

    public TimeLineAdapter(Context context, List<Datum> timelineList) {
        this.context = context;
        this.timelineList = timelineList;
    }

    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_line_item_layout,
                        parent,
                        false);

        return new TimeLineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {

        final Datum timeLine = timelineList.get(position);
        List<TimelineImage> imageList = new ArrayList<>();
        imageList = timeLine.getTimelineImages();

        for (int i = 0; i <= imageList.size(); i++) {
            Picasso.get().load(timeLine.getTimelineImages().get(0).getLink())
                    .into(holder.timeLineImageIV);
        }

        if (mujibApp.getSharedPrefValue() == 0) {
            holder.timeLineYearTV.setText(timeLine.getYearEn());
            holder.timeLineDescriptionTV.setText(timeLine.getDescriptionEn());
        } else if (mujibApp.getSharedPrefValue() == 1) {
            holder.timeLineYearTV.setText(timeLine.getYearBn());
            holder.timeLineDescriptionTV.setText(timeLine.getDescriptionBn());
        }


        List<TimelineImage> finalImageList = imageList;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TimeLineDetailsActivity.class);
                intent.putExtra("timeline", timeLine);
                intent.putParcelableArrayListExtra("image", (ArrayList<? extends Parcelable>) finalImageList);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return timelineList.size();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {
        ImageView timeLineImageIV;
        TextView timeLineYearTV, timeLineDescriptionTV;

        public TimeLineViewHolder(@NonNull View itemView) {
            super(itemView);

            timeLineImageIV = itemView.findViewById(R.id.timeLineImageIV);
            timeLineYearTV = itemView.findViewById(R.id.timeLineYearTV);
            timeLineDescriptionTV = itemView.findViewById(R.id.timeLineDescriptionTV);
        }
    }
}
