package com.ezzetech.mujib100.apiAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.booksApiModel.Datum;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {

    private Context context;
    private List<Datum> bookList;

    public BooksAdapter(Context context, List<Datum> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.books_item_layout,parent,false);
       return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {

        Datum books =bookList.get(position);
        Picasso.get().load(books.getLink()).into(holder.bookImageIV);

        if (books.getDownloadLink().equals("")){
            holder.downloadBtn.setVisibility(View.GONE);
        }
        else {
            holder.downloadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openTab(books.getLink());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BooksViewHolder extends RecyclerView.ViewHolder {
        private ImageView bookImageIV;
        Button downloadBtn;
        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);

            bookImageIV = itemView.findViewById(R.id.bookImageIV);
            downloadBtn = itemView.findViewById(R.id.downloadBtn);


        }
    }

    public void openTab(String url){
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(url));
    }
}
