package com.ezzetech.mujib100.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.ezzetech.mujib100.ExampleBottomSheetDialog;
import com.ezzetech.mujib100.MujibApp;
import com.ezzetech.mujib100.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity implements ExampleBottomSheetDialog.BottomSheetListener{

    private SwitchCompat switchCompat;
    private Locale myLocale;
    private String currentLanguage = "en", currentLang;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private long highScore;

    LinearLayout linearLayout;

    BottomSheetBehavior bottomSheetBehavior;

    MujibApp mujibApp = new MujibApp();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Loadlanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initialization();
        getIntentValue();
        colorChangeStatusBar();

        //bottomSheetBehavior =BottomSheetBehavior.from(linearLayout);
    }

    private void getIntentValue() {
        currentLanguage = getIntent().getStringExtra(currentLang);
    }



    private void initialization() {
        linearLayout = findViewById(R.id.bottom_sheet);
  }

    public void setLocale(String localeName, int i) {
        if (1 == i) {
            return;
        }

        if (!localeName.equals(currentLanguage)) {
            myLocale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
//            startActivity(new Intent(this, SettingsActivity.class)
//                    .putExtra(currentLang, localeName));
//            finish();

        }
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

    public void goLanguageChange(View view) {
       // bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this,
                R.style.BottomSheetDialogTheme);
        ExampleBottomSheetDialog bottomSheet = new ExampleBottomSheetDialog();
        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");

    }

    @Override
    public void onButtonClicked(String text) {

    }

    public void goAboutUs(View view) {
        startActivity(new Intent(this,AboutUsActivity.class));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
}
