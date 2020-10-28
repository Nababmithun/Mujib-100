package com.ezzetech.mujib100.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ezzetech.mujib100.MujibApp;
import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.adapter.SliderAdapterForTimelineDetails;
import com.ezzetech.mujib100.timeLineApiModel.Datum;
import com.ezzetech.mujib100.timeLineApiModel.TimelineImage;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class TimeLineDetailsActivity extends AppCompatActivity {

    MujibApp mujibApp = new MujibApp();
    Datum timeline;
    private List<Datum> sliderList = new ArrayList<>();

    private SliderView sliderView;
    SliderAdapterForTimelineDetails sliderAdapterForTimelineDetails;

    TextView yearTV, descriptionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Loadlanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line_details);

        init();
        colorChangeStatusBar();
        Intent i = getIntent();
        timeline = (Datum) i.getParcelableExtra("timeline");
        if (mujibApp.getSharedPrefValue()==0){
            yearTV.setText(timeline.getYearEn());
            descriptionTV.setText(timeline.getDescriptionEn());
        }else {
            yearTV.setText(timeline.getYearBn());
            descriptionTV.setText(timeline.getDescriptionBn());
        }



       List<TimelineImage> imageList =  i.getParcelableArrayListExtra("image");


        initBanner();

        sliderAdapterForTimelineDetails = new SliderAdapterForTimelineDetails(this, imageList);
      /*  for (int j =0; j<sliderList.size();j++){
            String image = timeline.getTimelineImages().get(j).getLink();

        }*/
        sliderView.setSliderAdapter(sliderAdapterForTimelineDetails);
        sliderAdapterForTimelineDetails.notifyDataSetChanged();


    }

    private void Loadlanguage() {
        Locale locale = new Locale(mujibApp.getSharedPrefValue()==0?"en":"bn");
        Locale.setDefault(locale);
        Configuration config = new Configuration(getResources().getConfiguration());
        if (Build.VERSION.SDK_INT >= 24) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
    //font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }




    private void init() {
        yearTV = findViewById(R.id.yearTV);
        descriptionTV = findViewById(R.id.descriptionTV);
        sliderView = findViewById(R.id.imageSlider);
    }

    public void initBanner() {
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        /*sliderView.setIndicatorSelectedColor(R.color.gold_color);
        sliderView.setIndicatorUnselectedColor(R.color.gold_color);*/
        sliderView.setScrollTimeInSec(3); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }

    public void backBtn(View view) {
        onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    public void colorChangeStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
