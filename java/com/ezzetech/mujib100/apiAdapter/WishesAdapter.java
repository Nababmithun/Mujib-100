package com.ezzetech.mujib100.apiAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.webApi.Datum;

import java.util.List;

public class WishesAdapter extends RecyclerView.Adapter<WishesAdapter.WishesViewHolder> {

    Context context;


    List<Datum> wishList;

    public WishesAdapter(Context context, List<Datum> wishList) {
        this.context = context;
        this.wishList = wishList;
    }

    @NonNull
    @Override
    public WishesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wishes_item_layout, parent, false);
        return new WishesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishesViewHolder holder, int position) {
        Datum wishes = wishList.get(position);
        String location = wishes.getLocation();

        holder.wishesTV.setText(wishes.getWish());
        holder.nameTV.setText(wishes.getName()+"("+location+")");
       // holder.locationTV.setText(wishes.getLocation());
    }

    @Override
    public int getItemCount() {
        return wishList.size();
    }

    public class WishesViewHolder extends RecyclerView.ViewHolder {
        TextView wishesTV, nameTV,locationTV;
        public WishesViewHolder(@NonNull View itemView) {
            super(itemView);

            wishesTV = itemView.findViewById(R.id.wishesTV);
            nameTV = itemView.findViewById(R.id.nameTV);
          //  locationTV = itemView.findViewById(R.id.locationTV);
        }
    }
}
