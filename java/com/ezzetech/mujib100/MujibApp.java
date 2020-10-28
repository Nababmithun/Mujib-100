package com.ezzetech.mujib100;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;

import androidx.annotation.NonNull;

import java.util.Locale;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class MujibApp extends Application {

    public static SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static long highScore;

    int value;
    int v;


    @Override
    public void onCreate() {

        sharedPreferences = getSharedPreferences("languageState",Context.MODE_PRIVATE);

        getSharedPrefValue();
        super.onCreate();
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/lato_regular.ttf")
                                .setDefaultFontPath("fonts/solaiman_lipi.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        Loadlanguage();
    }

    public int getValue() {
        return v;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Loadlanguage();
    }

    public int sharedPrefValue(int value) {
        editor = sharedPreferences.edit();
        editor.putInt("value", value);
        editor.apply();
        return value;
    }

    public long getSharedPrefValue() {
        highScore = sharedPreferences.getInt("value", 0);
        return highScore;
    }

    private void Loadlanguage() {
        Locale locale = new Locale(getSharedPrefValue() == 0 ? "en" : "bn");
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
