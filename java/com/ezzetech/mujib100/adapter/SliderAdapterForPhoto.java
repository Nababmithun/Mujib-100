package com.ezzetech.mujib100.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ezzetech.mujib100.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapterForPhoto extends SliderViewAdapter<SliderAdapterForPhoto.SliderAdapterVH> {

    private Context context;

    public SliderAdapterForPhoto(Context context) {
        this.context = context;
    }

    @Override
    public SliderAdapterForPhoto.SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_slider_layout_item, null);
        return new SliderAdapterForPhoto.SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterForPhoto.SliderAdapterVH viewHolder, int position) {

        switch (position) {
            case 0:
               // viewHolder.imageViewBackground.setImageResource(R.drawable.photo1);
                  break;
            case 1:
                //viewHolder.imageViewBackground.setImageResource(R.drawable.photo2);
                 break;
            case 2:
                //viewHolder.imageViewBackground.setImageResource(R.drawable.photo3);
                 break;
            case 3:
                //viewHolder.imageViewBackground.setImageResource(R.drawable.photo4);
                break;
            case 4:
                //viewHolder.imageViewBackground.setImageResource(R.drawable.photo5);
                break;

            default:
                //viewHolder.imageViewBackground.setImageResource(R.drawable.photo1);
                 break;

        }

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return 5;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;


        public SliderAdapterVH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageViewBackground = itemView.findViewById(R.id.iv_auto_photo_slider);

        }
    }
}