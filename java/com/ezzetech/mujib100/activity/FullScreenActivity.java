package com.ezzetech.mujib100.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


import com.ezzetech.mujib100.ConnectivityHelper;
import com.ezzetech.mujib100.CustomVisibility;
import com.ezzetech.mujib100.NetworkChangeReceiver;
import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.adapter.FullSizeAdapter;
import com.ezzetech.mujib100.interfaces.OnNetworkStateChangeListener;
import com.ezzetech.mujib100.model.Mujib;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

public class FullScreenActivity extends AppCompatActivity implements OnNetworkStateChangeListener {

    private ImageView imageView;
    private PhotoView photoView;
    private Mujib photo = null;
    private ViewPager viewPager;
    private ArrayList<Mujib> photos;
    int position;

    public Toolbar toolbar;

    //for internet check
    private TextView noInternetTVED;
    private int networkStateChangeCount = 0;
    private NetworkChangeReceiver mNetworkReceiver;

    private Animation fromBottomAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        toolbar = findViewById(R.id.toolbar);
        fromBottomAnimation = AnimationUtils.loadAnimation(this, R.anim.frombuttom);
        toolbar.setAnimation(fromBottomAnimation);

        viewPager = findViewById(R.id.viewPagerImage);

        //for internet check
        noInternetTVED = findViewById(R.id.noInternetTVE);
        mNetworkReceiver = new NetworkChangeReceiver(this);
        registerNetworkBroadcast();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white));
        }


       if (savedInstanceState == null){
           Intent i = getIntent();
           photos = i.getParcelableArrayListExtra("photo");
         //  String s = photo.getImage();

           position = i.getIntExtra("position",0);

           FullSizeAdapter fullSizeAdapter = new FullSizeAdapter(FullScreenActivity.this,photos);
           viewPager.setAdapter(fullSizeAdapter);
           viewPager.setCurrentItem(position,true);
           
       }


    }

    public void backBtn(View view) {
        onBackPressed();

    }
    
    //for internet check------start---------
    private void registerNetworkBroadcast() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    private void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ConnectivityHelper.isConnected(this) == true) {
            noInternetTVED.setVisibility(View.GONE);
        } else {
            noInternetTVED.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onChange(boolean isConnected) {
        networkStateChangeCount++;
        if (isConnected) {
            noInternetTVED.setBackgroundColor(getResources().getColor(R.color.green));
            noInternetTVED.setText(getResources().getString(R.string.back_online));

            if (networkStateChangeCount >= 2) {
                //  checkNextActivity();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    CustomVisibility.collapse(noInternetTVED, 500);
                }
            }, 2000);
        } else {
            noInternetTVED.setBackgroundColor(getResources().getColor(R.color.red));
            noInternetTVED.setText(getResources().getString(R.string.no_internet_connection));
            CustomVisibility.expand(noInternetTVED, 500);
        }
    }
    //for internet check-------end-----------------
}
