package com.ezzetech.mujib100.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ezzetech.mujib100.MujibApp;
import com.ezzetech.mujib100.R;

import java.util.Locale;

public class NoticeActivity extends AppCompatActivity {

    MujibApp mujibApp = new MujibApp();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Loadlanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        colorChangeStatusBar();
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

    public void cardOne(View view) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        openTab("https://drive.google.com/file/d/1KH4RIsN_W7KSMF2oVl7hT7HslJkYAeLD/view");

    }

    public void cardTwo(View view) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        openTab("https://drive.google.com/file/d/1nYwSYXWo0Fg0yD3-Ub4WnjtJE1UREqeR/view");
    }

    public void cardTree(View view) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        openTab("https://drive.google.com/file/d/1MZlcuEREAq495PxnpvI9Kq42TYlgT0K0/view");

    }


    public void openTab(String url){
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
