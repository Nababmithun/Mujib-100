package com.ezzetech.mujib100.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.activity.FullScreenActivity;
import com.ezzetech.mujib100.model.Mujib;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FullSizeAdapter extends PagerAdapter {
   Context context;
   ArrayList<Mujib> images;
   LayoutInflater inflater;
   Mujib photo;

    public FullSizeAdapter(Context context, ArrayList<Mujib> images){
        this.context = context;
        this.images= images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View v = inflater.inflate(R.layout.full_item,null);
        PhotoView photoView = v.findViewById(R.id.imageView);
        Picasso.get().load(images.get(position).getNovelImage())
                .into(photoView);
        ViewPager vp = (ViewPager) container;
        vp.addView(v,0);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
      //  super.destroyItem(container, position, object);
        ViewPager viewPager =(ViewPager)container;
        View v = (View)object;
        viewPager.removeView(v);
    }
}
