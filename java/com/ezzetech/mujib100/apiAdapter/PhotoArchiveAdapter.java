package com.ezzetech.mujib100.apiAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.ezzetech.mujib100.MujibApp;
import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.photoAchiveApiModel.Datum;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoArchiveAdapter extends RecyclerView.Adapter<PhotoArchiveAdapter.PhotoViewHolder> {

    private Context context;
    private List<Datum> photoArchiveList;

    MujibApp mujibApp = new MujibApp();

    public PhotoArchiveAdapter(Context context, List<Datum> photoArchiveList) {
        this.context = context;
        this.photoArchiveList = photoArchiveList;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_archive_item,
                        parent,
                        false);

        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {

        Datum photoArchive =photoArchiveList.get(position);

        if (mujibApp.getSharedPrefValue()==0){
            holder.descriptionTV.setText(photoArchive.getCaptionEn());
        }
        else  if (mujibApp.getSharedPrefValue()==1){
            holder.descriptionTV.setText(photoArchive.getCaptionBn());
        }

        Picasso.get().load( photoArchive.getLink())
                .into(holder.photoArchiveIV);

        holder.photoArchiveIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(position);
            }
        });


    }

    private void showDialog(int position) {
        Datum photoArchive =photoArchiveList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_grapic_dialog,null);
        PhotoView photoView = view.findViewById(R.id.photo_view);
        //photoView.setImageResource(R.drawable.image);

        Picasso.get().load(photoArchive.getLink())
                .into(photoView);
        builder.setView(view);
        builder.show();
    }

    @Override
    public int getItemCount() {
        return photoArchiveList.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView photoArchiveIV;
        TextView descriptionTV;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            photoArchiveIV = itemView.findViewById(R.id.PhotoArchiveIV);
            descriptionTV = itemView.findViewById(R.id.descriptionTV);
        }
    }
}
