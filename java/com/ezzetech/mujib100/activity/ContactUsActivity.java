package com.ezzetech.mujib100.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ezzetech.mujib100.MujibApp;
import com.ezzetech.mujib100.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class ContactUsActivity extends AppCompatActivity implements OnMapReadyCallback {

    MujibApp mujibApp = new MujibApp();
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Loadlanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        colorChangeStatusBar();

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.map, mapFragment);
        fragmentTransaction.commit();
        mapFragment.getMapAsync(this);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng location = new LatLng(23.7342519, 90.4031961);
        mMap.addMarker(new MarkerOptions().position(location));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));

       // mapBackgroundDesignChange(mMap);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
