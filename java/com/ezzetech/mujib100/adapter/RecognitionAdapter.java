package com.ezzetech.mujib100.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.model.Recognize;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecognitionAdapter extends RecyclerView.Adapter<RecognitionAdapter.ViewHolder> {


    private Context context;
    private ArrayList<Recognize> recognizes;

    public RecognitionAdapter(Context context, ArrayList<Recognize> recognizes) {
        this.context = context;
        this.recognizes = recognizes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recognising_item_for_home,
                        parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Recognize recognize = recognizes.get(position);

        Picasso.get().load(recognize.getImage())
                .into(holder.profileImage);
        //holder.profileImage.setImageResource(recognize.getImage());
        holder.titleTV.setText(recognize.getTitle());
        holder.descriptionTV.setText(recognize.getDescription());
        holder.nameTV.setText(recognize.getName());

    }

    @Override
    public int getItemCount() {
        return recognizes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImage;
        TextView titleTV,descriptionTV,nameTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.titleTV);
            descriptionTV = itemView.findViewById(R.id.descriptionTV);
            profileImage = itemView.findViewById(R.id.profile_image);
            nameTV = itemView.findViewById(R.id.nameTV);
        }
    }
}
