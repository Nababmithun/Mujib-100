package com.ezzetech.mujib100.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ezzetech.mujib100.ConnectivityHelper;
import com.ezzetech.mujib100.CustomVisibility;
import com.ezzetech.mujib100.MujibApp;
import com.ezzetech.mujib100.NetworkChangeReceiver;
import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.apiAdapter.TimeLineAdapter;
import com.ezzetech.mujib100.timeLineApiModel.Datum;
import com.ezzetech.mujib100.timeLineApiModel.TimeLineResponse;
import com.ezzetech.mujib100.common.Common;
import com.ezzetech.mujib100.interfaces.ApiInterface;
import com.ezzetech.mujib100.interfaces.OnNetworkStateChangeListener;
import com.ezzetech.mujib100.webApi.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeLineActivity extends AppCompatActivity implements OnNetworkStateChangeListener {

    MujibApp mujibApp = new MujibApp();
    private RecyclerView timeLineRecyclerView;

    private TimeLineAdapter timeLineAdapter;
    private List<Datum> timeLineList = new ArrayList<>();
    private ApiInterface apiService;

    TextView noInternetTVED;

    //for internet
    private int networkStateChangeCount = 0;
    private NetworkChangeReceiver mNetworkReceiver;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Loadlanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        init();
        colorChangeStatusBar();
        loadDataFromAPI();
        initSwipeLayout();

    }

    private void Loadlanguage() {
        Locale locale = new Locale(mujibApp.getSharedPrefValue() == 0 ? "en" : "bn");
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


    private void initSwipeLayout() {
        //view
        swipeRefreshLayout = findViewById(R.id.mediaCoverageSwipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Common.isConnectToInternet(getBaseContext())) {
                    loadTimeLine();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(getBaseContext(), "Please check your connection!!", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }
            }
        });


        //Default, load for first time
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                if (Common.isConnectToInternet(getBaseContext())) {
                    loadTimeLine();
                    //  findViewById(R.id.NestedScrollView).setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(true);

                } else {
                    Toast.makeText(getBaseContext(), "Please check your connection!!", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }
            }

        });

    }


    private void loadDataFromAPI() {
        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

        apiService.getTimeLineResponse().enqueue(new Callback<TimeLineResponse>() {
            @Override
            public void onResponse(Call<TimeLineResponse> call, Response<TimeLineResponse> response) {

                if (response.isSuccessful()) {
                    TimeLineResponse timeLineResponse = response.body();
                    timeLineList = timeLineResponse.getData();
                    loadTimeLine();
                }
            }

            @Override
            public void onFailure(Call<TimeLineResponse> call, Throwable t) {
                Toast.makeText(TimeLineActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadTimeLine() {
        timeLineAdapter = new TimeLineAdapter(this, timeLineList);
        timeLineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        timeLineRecyclerView.setAdapter(timeLineAdapter);
        swipeRefreshLayout.setRefreshing(false);
        timeLineAdapter.notifyDataSetChanged();
    }

    private void init() {
        timeLineRecyclerView = findViewById(R.id.timeLineRecyclerView);
        noInternetTVED = findViewById(R.id.noInternetTVE);
        mNetworkReceiver = new NetworkChangeReceiver(this);
        registerNetworkBroadcast();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
