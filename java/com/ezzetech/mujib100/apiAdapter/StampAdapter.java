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
import com.ezzetech.mujib100.stempApiModel.Datum;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StampAdapter extends RecyclerView.Adapter<StampAdapter.StampViewHolder> {

    private Context context;
    private List<Datum> stampList;

    MujibApp mujibApp = new MujibApp();

    public StampAdapter(Context context, List<Datum> stampList) {
        this.context = context;
        this.stampList = stampList;
    }

    @NonNull
    @Override
    public StampViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.stamp_item_layout,parent,false);

        return new StampViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StampViewHolder holder, int position) {

        Datum stamp = stampList.get(position);

        if (mujibApp.getSharedPrefValue()==0){
            holder.titleTV.setText(stamp.getTitleEn());
            holder.useYearTV.setText(stamp.getUseYearEn());
        }
        else if (mujibApp.getSharedPrefValue()==1){
            holder.titleTV.setText(stamp.getTitleBn());
            holder.useYearTV.setText(stamp.getUseYearBn());
        }



        Picasso.get().load(stamp.getLink()).into(holder.stampIV);
    }

    @Override
    public int getItemCount() {
        return stampList.size();
    }

    public class StampViewHolder extends RecyclerView.ViewHolder {
        private TextView useYearTV,titleTV;
        private ImageView stampIV;
        public StampViewHolder(@NonNull View itemView) {
            super(itemView);

            useYearTV = itemView.findViewById(R.id.useYearTV);
            titleTV = itemView.findViewById(R.id.titleTV);
            stampIV = itemView.findViewById(R.id.stampIV);
        }
    }
}
