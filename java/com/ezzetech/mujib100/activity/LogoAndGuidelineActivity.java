package com.ezzetech.mujib100.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;

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

public class LogoAndGuidelineActivity extends AppCompatActivity {

    MujibApp mujibApp = new MujibApp();

    CardView image1IV,image2IV,image3IV,image4IV,image5IV,image6IV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Loadlanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_and_guideline);

        colorChangeStatusBar();

        init();

        image1IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                openTab("https://drive.google.com/file/d/1uxiyJ5fQH3hXN_W0rn_3mrYPMBHXGvQU/view");
            }
        });

        image2IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                openTab("https://drive.google.com/file/d/1uxiyJ5fQH3hXN_W0rn_3mrYPMBHXGvQU/view");

            }
        });

        image3IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                openTab("https://drive.google.com/file/d/144bSZalH3S5a4doBWvUR7OLf2Yw1JRbo/view");

            }
        });

        image4IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                openTab("https://drive.google.com/file/d/1P2tSrekuM-CAqKwaS_AICpReuh1N7yzQ/view");

            }
        });

        image5IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                openTab("https://drive.google.com/file/d/1XZ3DHzF--oZmYmt_m2ler1xBphE_RN42/view");

            }
        });

        image6IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                openTab("https://drive.google.com/file/d/1L-DfrRSZo5B9RYduaTVez7Px1JqZfmDM/view");

            }
        });
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

    private void init() {
        image1IV = findViewById(R.id.image1IV);
        image2IV = findViewById(R.id.image2IV);
        image3IV = findViewById(R.id.image3IV);
        image4IV = findViewById(R.id.image4IV);
        image5IV = findViewById(R.id.image5IV);
        image6IV = findViewById(R.id.image6IV);
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
}
