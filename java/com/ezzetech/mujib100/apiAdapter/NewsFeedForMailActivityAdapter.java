package com.ezzetech.mujib100.apiAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.activity.NewsFeedDetailsActivity;
import com.ezzetech.mujib100.newsFeedApiModel.Datum;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsFeedForMailActivityAdapter extends RecyclerView.Adapter<NewsFeedForMailActivityAdapter.NewsFeedViewHoldr> {

    Context context;
    List<Datum> newsFeedList;
    Datum newsFeed;

    public NewsFeedForMailActivityAdapter(Context context, List<Datum> newsFeedList) {
        this.context = context;
        this.newsFeedList = newsFeedList;
    }

    @NonNull
    @Override
    public NewsFeedViewHoldr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.news_feed_layout_for_main_activity, parent, false);
        return new NewsFeedViewHoldr(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFeedViewHoldr holder, int position) {
        newsFeed = newsFeedList.get(position);
        holder.titleTV.setText(newsFeed.getTitle());
        String description = stripHtml(newsFeed.getDescription());
        holder.descriptionTV.setText(description);
        //holder.publishDateTV.setText(newsFeed.getPublishedDate());

        if (newsFeed.getLinkType().equals("video")) {
            holder.videoIconIV.setVisibility(View.VISIBLE);
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
            Date newDate = format.parse(newsFeed.getPublishedDate());
            format = new SimpleDateFormat("dd MMM, yyyy hh:mm a", Locale.getDefault());
            String date = format.format(newDate);
            holder.publishDateTV.setText(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Picasso.get().load(newsFeed.getLink()).into(holder.newsImageIV);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsFeedDetailsActivity.class);
                intent.putExtra("url", newsFeed.getNewsLink());
                context.startActivity(intent);
            }
        });



    }


    @Override
    public int getItemCount() {
        return newsFeedList.size();
    }

    public class NewsFeedViewHoldr extends RecyclerView.ViewHolder {
        ImageView newsImageIV, videoIconIV;
        TextView titleTV, descriptionTV, publishDateTV;

        public NewsFeedViewHoldr(@NonNull View itemView) {
            super(itemView);

            newsImageIV = itemView.findViewById(R.id.newsImageIV);
            titleTV = itemView.findViewById(R.id.titleTV);
            descriptionTV = itemView.findViewById(R.id.descriptionTV);
            publishDateTV = itemView.findViewById(R.id.publishDateTV);
            videoIconIV = itemView.findViewById(R.id.videoIconIV);


        }
    }


    public String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(html).toString();
        }

    }
}