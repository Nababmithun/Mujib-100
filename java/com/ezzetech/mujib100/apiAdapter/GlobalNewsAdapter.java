package com.ezzetech.mujib100.apiAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.ezzetech.mujib100.GlobalNewsApiModel.Datum;
import com.ezzetech.mujib100.MujibApp;
import com.ezzetech.mujib100.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GlobalNewsAdapter extends RecyclerView.Adapter<GlobalNewsAdapter.GlobalNewsViewHolder> {
    Context context;
    List<Datum> globalNewsList;

    MujibApp mujibApp = new MujibApp();

    public GlobalNewsAdapter(Context context, List<Datum> globalNewsList) {
        this.context = context;
        this.globalNewsList = globalNewsList;
    }

    @NonNull
    @Override
    public GlobalNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.international_celebrations_item_layout,parent,false);
        return new GlobalNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GlobalNewsViewHolder holder, int position) {

        Datum globalNews = globalNewsList.get(position);

        if (mujibApp.getSharedPrefValue()==0){
            holder.titleTV.setText(globalNews.getTitleEn());
           // holder.descriptionTV.setText(globalNews.getDescriptionEn());
        }else {
            holder.titleTV.setText(globalNews.getTitleBn());
            //holder.descriptionTV.setText(globalNews.getDescripionBn());
        }

        Picasso.get().load(globalNews.getLink()).into(holder.eventImageIV);


        holder.readMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTab(globalNews.getNewsLink());

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
        return globalNewsList.size();
    }

    public class GlobalNewsViewHolder extends RecyclerView.ViewHolder {

        TextView titleTV,descriptionTV;
        ImageView eventImageIV;
        Button readMoreBtn;
        public GlobalNewsViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.titleTV);
         /*   descriptionTV = itemView.findViewById(R.id.descriptionTV);*/
            eventImageIV = itemView.findViewById(R.id.eventImageIV);
            readMoreBtn  = itemView.findViewById(R.id.readMoreBtn);
        }
    }
}
