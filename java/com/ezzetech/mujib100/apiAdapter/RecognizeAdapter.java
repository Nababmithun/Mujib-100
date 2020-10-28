package com.ezzetech.mujib100.apiAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezzetech.mujib100.MujibApp;
import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.recognitionApiModel.Datum;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecognizeAdapter extends RecyclerView.Adapter<RecognizeAdapter.RecognizeViewHolder> {

    private Context context;
    private List<Datum> recognizeList;

    MujibApp mujibApp = new MujibApp();

    public RecognizeAdapter(Context context, List<Datum> recognizeList) {
        this.context = context;
        this.recognizeList = recognizeList;
    }

    @NonNull
    @Override
    public RecognizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recognising_item_layout,
                        parent,
                        false);

        return new RecognizeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecognizeViewHolder holder, int position) {

        Datum recognize = recognizeList.get(position);

        if (mujibApp.getSharedPrefValue()==0){
            holder.professionTV.setText(recognize.getProfessionEn());
            holder.descriptionTV.setText(recognize.getQuoteEn());
            holder.nameTV.setText(recognize.getNameEn());
        }
        else  if (mujibApp.getSharedPrefValue()==1){
            holder.professionTV.setText(recognize.getProfessionBn());
            holder.descriptionTV.setText(recognize.getQuoteBn());
            holder.nameTV.setText(recognize.getNameBn());
        }


        Picasso.get().load( recognize.getLink())
                .into(holder.profileImage);
    }

    @Override
    public int getItemCount() {
        return recognizeList.size();
    }

    public class RecognizeViewHolder extends RecyclerView.ViewHolder {

        TextView descriptionTV,nameTV,professionTV;
        ImageView profileImage;


        public RecognizeViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionTV = itemView.findViewById(R.id.descriptionTV);
            nameTV = itemView.findViewById(R.id.nameTV);
            professionTV = itemView.findViewById(R.id.professionTV);
            profileImage = itemView.findViewById(R.id.profile_image);
        }
    }
}
