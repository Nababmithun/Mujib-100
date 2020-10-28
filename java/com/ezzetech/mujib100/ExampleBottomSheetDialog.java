package com.ezzetech.mujib100;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ezzetech.mujib100.activity.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Locale;

public class ExampleBottomSheetDialog extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    private TextView changeBanglaTV, changeEnglishTV;

    // private SwitchCompat switchCompat;
    private Locale myLocale;
    private String currentLanguage = "en", currentLang;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private long highScore;
    MujibApp mujibApp = new MujibApp();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);


        changeBanglaTV = v.findViewById(R.id.changeBanglaTV);
        changeEnglishTV = v.findViewById(R.id.changeEnglishTV);


        initialization();

        getIntentValue();

        getSharedPrefValue();


       /* Locale locale = new Locale("ar");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getResources().updateConfiguration(config,
                getActivity().getResources().getDisplayMetrics());
        sharedPreferences.edit().putString("locale", "ar").commit();

        Intent refresh = new Intent(getContext(), SettingsActivity.class);
        startActivity(refresh);*/


        changeBanglaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mujibApp.sharedPrefValue(1);
                setLocale("bn", 0);
            }
        });

        changeEnglishTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mujibApp.sharedPrefValue(0);
                setLocale("en", 0);
            }
        });


        ImageView cancelBtn = v.findViewById(R.id.calcelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Button 1 clicked");
                dismiss();
            }
        });


        return v;
    }


    private void getSharedPrefValue() {
        highScore = mujibApp.sharedPreferences.getInt("value", 0);
    }

    private void initialization() {

        //sharedPreferences = getContext().getSharedPreferences("languageState", Context.MODE_PRIVATE);
    }

/*    public void sharedPrefValue(int value) {
        editor = sharedPreferences.edit();
        editor.putInt("value", value);
        editor.apply();
    }*/

    public void setLocale(String localeName, int i) {
        if (1 == i) {
            return;
        }

        if (!localeName.equals(currentLanguage)) {
            myLocale = new Locale(localeName);
            Resources res = getResources();

            Configuration conf = new Configuration();
            DisplayMetrics dm = res.getDisplayMetrics();
            conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            getActivity().finish();
            startActivity(new Intent(getContext(), MainActivity.class)
                    .putExtra(currentLang, localeName));


        } else {
//            Toast.makeText(MainActivity.this, "Language already selected!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getIntentValue() {
        currentLanguage = getActivity().getIntent().getStringExtra(currentLang);
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }
}