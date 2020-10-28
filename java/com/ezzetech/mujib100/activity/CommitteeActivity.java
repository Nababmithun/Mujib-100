package com.ezzetech.mujib100.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;

import android.content.Context;
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

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class CommitteeActivity extends AppCompatActivity {

    private CardView cardOne, cardTwo,cardThree,cardFore,cardFive,cardSix;

    MujibApp mujibApp = new MujibApp();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Loadlanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee);
        init();

        colorChangeStatusBar();

        cardOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTab("https://mujib100.gov.bd/assets/pdf/Gazette%20Imp%20Committee.pdf");
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        cardTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTab("https://mujib100.gov.bd/assets/pdf/Gazette-National%20Committee.pdf");
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                finish();
            }
        });

        cardThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTab("https://mujib100.gov.bd/assets/pdf/%E0%A6%97%E0%A7%87%E0%A6%9C%E0%A7%87%E0%A6%9F%20%E0%A6%9C%E0%A6%BE%E0%A6%A4%E0%A7%80%E0%A6%AF%E0%A6%BC%20%E0%A6%95%E0%A6%AE%E0%A6%BF%E0%A6%9F%E0%A6%BF%20%E0%A6%95%E0%A7%8B%E0%A6%85%E0%A6%AA%E0%A7%8D%E0%A6%9F.pdf");
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        cardFore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTab("https://mujib100.gov.bd/assets/pdf/Website%20Content%20Committee.pdf");
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        cardFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTab("https://mujib100.gov.bd/assets/pdf/%E0%A6%93%E0%A6%AF%E0%A6%BC%E0%A7%87%E0%A6%AC%E0%A6%B8%E0%A6%BE%E0%A6%87%E0%A6%9F%20%E0%A6%A8%E0%A6%BF%E0%A6%B0%E0%A7%8D%E0%A6%AE%E0%A6%BE%E0%A6%A3%20%E0%A6%95%E0%A6%AE%E0%A6%BF%E0%A6%9F%E0%A6%BF.pdf");
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        cardSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTab("https://mujib100.gov.bd/assets/pdf/Subcommittees.pdf");
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
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

    //font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    private void init() {
        cardOne = findViewById(R.id.cardOne);
        cardTwo = findViewById(R.id.cardTwo);
        cardThree = findViewById(R.id.cardThree);
        cardFore = findViewById(R.id.cardFore);
        cardFive = findViewById(R.id.cardFive);
        cardSix = findViewById(R.id.cardSix);
    }

    public void backBtn(View view) {
       // startActivity(new Intent(this,MainActivity.class));
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
