package com.ezzetech.mujib100.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezzetech.mujib100.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;

    public SliderAdapterExample(Context context) {
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {

        switch (position) {
            case 0:
               viewHolder.imageViewBackground.setImageResource(R.drawable.mujib1);
               viewHolder.titleTV.setText(R.string.mujib);
               viewHolder.descriptionTV.setText(R.string.mujib_slider);
                break;
            case 1:
                viewHolder.imageViewBackground.setImageResource(R.drawable.mujib2);
                viewHolder.titleTV.setText(R.string.celebration);
                viewHolder.descriptionTV.setText(R.string.celebration_slider);
                break;
            case 2:
                viewHolder.imageViewBackground.setImageResource(R.drawable.mujib1);
                viewHolder.titleTV.setText(R.string.resource);
                viewHolder.descriptionTV.setText(R.string.resource_slider);
                break;
            default:
                viewHolder.imageViewBackground.setImageResource(R.drawable.mujib2);
                viewHolder.titleTV.setText(R.string.event);
                viewHolder.descriptionTV.setText(R.string.event_slider);
                break;
        }

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return 4;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView descriptionTV,titleTV;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            // textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            titleTV = itemView.findViewById(R.id.titleTV);
            descriptionTV = itemView.findViewById(R.id.descriptionTV);

        }
    }
}