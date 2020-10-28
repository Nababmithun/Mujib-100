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

public class PosterAndPublicationsActivity extends AppCompatActivity {

    MujibApp mujibApp = new MujibApp();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Loadlanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_and_publications);

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

    public void imageOne(View view) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        openTab("https://mujib100.gov.bd/assets/pdf/National%20Implementation%20Committee%20Publications.pdf");

    }

    public void imageTwo(View view) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        openTab("https://mujib100.gov.bd/assets/pdf/Posters.pdf");

    }

    public void imageThree(View view) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        openTab("https://mujib100.gov.bd/assets/pdf/Mujib100%20bi%20fold%20Leaflet%20Final.pdf");

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

    public void openTab(String url){

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void imageFore(View view) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        openTab("https://mujib100.gov.bd/pages/resources/committee_posters.html");
    }

    public void imageFive(View view) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        openTab("https://drive.google.com/file/d/15K-Jz4dTJgl4vhSHmaRgGaiQk_I_OwBF/view");
    }

    public void imageSix(View view) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        openTab("https://drive.google.com/file/d/1ITrA2aFiiMaLcHXXyVAZGgWD1dUhDAHu/view");
    }

    public void imageSeven(View view) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        openTab("https://drive.google.com/file/d/15GiQgVJiylM6FRnvLp-W4jPL8lT19qSx/view");
    }

    public void imageEight(View view) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        openTab("https://drive.google.com/file/d/1CBIQQrG33syp59sTEkATclJcTEFCe8jh/view");
    }

    public void imageNine(View view) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        openTab("https://drive.google.com/file/d/1M-tC1SGmzM5sglasYGwa42wBN9eGV2lK/view");
    }

    public void imageTen(View view) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        openTab("https://drive.google.com/file/d/17LjA9Oho-HKEohK-dudebVw_Uzoblhzm/view");
    }

    public void imageElevent(View view) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        openTab("https://drive.google.com/file/d/1nWxGyjF1SSPN6xb-7igs4nsG1-M7kk8I/view");
    }

    public void image12(View view) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        openTab("https://drive.google.com/file/d/1dZr4ETKlyRwxHkJ8jz5SjyIkMNYmGWAl/view");
    }

    public void image13(View view) {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        openTab("https://drive.google.com/file/d/1X-RrbFCAPfQGpa3WDUMlZ_BzvYjwFh1a/view");
    }
}
