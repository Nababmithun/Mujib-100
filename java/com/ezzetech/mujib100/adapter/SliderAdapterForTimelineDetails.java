package com.ezzetech.mujib100.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.timeLineApiModel.TimelineImage;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapterForTimelineDetails extends SliderViewAdapter<SliderAdapterForTimelineDetails.SliderAdapterVH> {


    private Context context;
    private List<TimelineImage> sliderList;

    public SliderAdapterForTimelineDetails(Context context, List<TimelineImage> sliderList) {
        this.context = context;
        this.sliderList = sliderList;
    }

    public SliderAdapterForTimelineDetails(Context context) {
        this.context = context;
    }

    @Override
    public SliderAdapterForTimelineDetails.SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout_item_for_timeline_details, null);
        return new SliderAdapterForTimelineDetails.SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterForTimelineDetails.SliderAdapterVH viewHolder, int position) {

        final TimelineImage slider = sliderList.get(position);

            Picasso.get().load( slider.getLink())
                    .into(viewHolder.imageViewBackground);

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return sliderList.size();
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
