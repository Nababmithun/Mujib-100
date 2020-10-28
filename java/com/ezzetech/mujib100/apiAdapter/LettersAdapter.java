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
import com.ezzetech.mujib100.lettersApiModel.Datum;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LettersAdapter extends RecyclerView.Adapter<LettersAdapter.LettersViewHolder> {

    List<Datum> letterList;
    Context context;
    MujibApp mujibApp = new MujibApp();

    public LettersAdapter(List<Datum> letterList, Context context) {
        this.letterList = letterList;
        this.context = context;
    }

    @NonNull
    @Override
    public LettersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.letters_item_layout,parent,false);
        return new LettersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LettersViewHolder holder, int position) {

        Datum letter = letterList.get(position);
        if (mujibApp.getSharedPrefValue()==0){
            holder.captionTV.setText(letter.getCaptionEn());
        }else if (mujibApp.getSharedPrefValue()==1){
            holder.captionTV.setText(letter.getCaptionBn());
        }

        //holder.captionTV.setText(letter.getCaptionBn());
        Picasso.get().load(letter.getLink()).into(holder.letterImageIV);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(position);
            }
        });
    }


    private void showDialog(int position) {
        Datum letter = letterList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_grapic_dialog,null);
        PhotoView photoView = view.findViewById(R.id.photo_view);
        //photoView.setImageResource(R.drawable.image);

        Picasso.get().load(letter.getLink())
                .into(photoView);
        builder.setView(view);
        builder.show();
    }

    @Override
    public int getItemCount() {
        return letterList.size();
    }

    public class LettersViewHolder extends RecyclerView.ViewHolder {
        ImageView letterImageIV;
        TextView captionTV;
        public LettersViewHolder(@NonNull View itemView) {
            super(itemView);
            letterImageIV = itemView.findViewById(R.id.lettersImageIV);
            captionTV = itemView.findViewById(R.id.captionTV);
        }
    }
}
