package com.ezzetech.mujib100.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.activity.FullScreenActivity;
import com.ezzetech.mujib100.model.Mujib;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MujibBooksAdapter extends RecyclerView.Adapter<MujibBooksAdapter.ImageViewHolder> {

    private Context context;
    private ArrayList<Mujib> photoList;

    public MujibBooksAdapter() {
    }

    public MujibBooksAdapter(Context context, ArrayList<Mujib> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.novel_books_item_layout,
                        parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, final int position) {
       final Mujib novels = photoList.get(position);

      //holder.galleryImageView.setImageResource(novels.getNovelImage());
        Picasso.get().load(novels.getNovelImage())
                .into(holder.galleryImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //showDialog(position,holder);
                Intent intent = new Intent(context, FullScreenActivity.class);
                intent.putParcelableArrayListExtra("photo", photoList);
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });


    }

    private void showDialog(int position, ImageViewHolder holder) {
        final Mujib novels = photoList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_grapic_dialog,null);
        PhotoView photoView = view.findViewById(R.id.photo_view);
        ImageView cancelAction = view.findViewById(R.id.cancel_action);
        //photoView.setImageResource(R.drawable.image);

        Picasso.get().load(novels.getNovelImage())
                .into(photoView);
        builder.setView(view);
        builder.show();
        cancelAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView galleryImageView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            galleryImageView = itemView.findViewById(R.id.galleryImageView);
        }
    }
}
