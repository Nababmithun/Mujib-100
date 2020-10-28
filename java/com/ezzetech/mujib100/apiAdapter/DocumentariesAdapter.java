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

import com.ezzetech.mujib100.DocumentariesApiModel.Datum;
import com.ezzetech.mujib100.MujibApp;
import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.activity.Ducumentaries_detailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DocumentariesAdapter extends RecyclerView.Adapter<DocumentariesAdapter.DocumentariesViewHolder> {
    private Context context;
    private List<Datum> documentariesList;

    MujibApp mujibApp = new MujibApp();

    public DocumentariesAdapter(Context context, List<Datum> documentariesList) {
        this.context = context;
        this.documentariesList = documentariesList;
    }

    @NonNull
    @Override
    public DocumentariesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.documentaries_item_layout,parent,false);

        return new DocumentariesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentariesViewHolder holder, int position) {

        Datum documentaries = documentariesList.get(position);

        if (mujibApp.getSharedPrefValue()==0){
            holder.captionTV.setText(documentaries.getCaptionEn());
        }else if (mujibApp.getSharedPrefValue()==1){
            holder.captionTV.setText(documentaries.getCaptionBn());
        }


        Picasso.get().load(documentaries.getLink())
                .into(holder.thumbleIV);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Ducumentaries_detailsActivity.class);
                intent.putExtra("documentaries",documentaries);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return documentariesList.size();
    }

    public class DocumentariesViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbleIV;
        private TextView captionTV;
        public DocumentariesViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbleIV = itemView.findViewById(R.id.thumbleIV);
            captionTV = itemView.findViewById(R.id.captionTV);

        }
    }
}
