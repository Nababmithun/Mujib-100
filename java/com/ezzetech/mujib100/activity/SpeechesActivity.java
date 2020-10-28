package com.ezzetech.mujib100.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ezzetech.mujib100.ConnectivityHelper;
import com.ezzetech.mujib100.CustomVisibility;
import com.ezzetech.mujib100.MujibApp;
import com.ezzetech.mujib100.NetworkChangeReceiver;
import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.apiAdapter.SpeechesAdapter;
import com.ezzetech.mujib100.common.Common;
import com.ezzetech.mujib100.interfaces.ApiInterface;
import com.ezzetech.mujib100.interfaces.OnNetworkStateChangeListener;
import com.ezzetech.mujib100.speechesApiModel.Datum;
import com.ezzetech.mujib100.speechesApiModel.SpeechesResponse;
import com.ezzetech.mujib100.webApi.RetrofitClient;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class SpeechesActivity extends AppCompatActivity implements OnNetworkStateChangeListener {

    MujibApp mujibApp = new MujibApp();

    private RecyclerView speechesRV;
    private TextView noInternetTVED;

    private SpeechesAdapter speechesAdapter;
    List<Datum> speechesList = new ArrayList<>();

    private ApiInterface apiService;

    //for internet
    private int networkStateChangeCount = 0;
    private NetworkChangeReceiver mNetworkReceiver;
    private SwipeRefreshLayout swipeRefreshLayout;

    //custom media play--------start----------
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private String url = "https://mujib100.gov.bd/assets/images/videos/video%201%20(7th%20march).mp4";
    private ImageView playIv, pauseIv, exoPlayerFullscreen, exitfullscreenIv, replayIv;
    private ImageView thumbnail;
    private ProgressBar progress;
    private MediaSource videosource;
    private long position;
    private BandwidthMeter bandwidthMeter;
    private TrackSelector trackSelector;
    private Uri videoUri;
    private DefaultHttpDataSourceFactory defaultHttpDataSourceFactory;
    private ExtractorsFactory extractorsFactory;
    //custom media play--------end----------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Loadlanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speeches);



        init();
        initSwipeLayout();
        colorChangeStatusBar();
        loadDataFromAPI();


        //---------mediaPlayer------start--------
        playerInit();

        bandwidthMeter = new DefaultBandwidthMeter();
        trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        videoUri = Uri.parse(url);


        defaultHttpDataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer video");
        extractorsFactory = new DefaultExtractorsFactory();
        videosource = new ExtractorMediaSource(videoUri, defaultHttpDataSourceFactory, extractorsFactory, null, null);

        playerView.setPlayer(player);
        player.prepare(videosource);
        player.setPlayWhenReady(false);
        replayIv.setVisibility(GONE);

        try {
            player.addListener(new Player.EventListener() {


                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {


                }


                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {


                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }


                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {


                    switch (playbackState) {


                        case ExoPlayer.STATE_ENDED: {
                            playIv.setVisibility(GONE);
                            pauseIv.setVisibility(GONE);
                            progress.setVisibility(GONE);
                            thumbnail.setVisibility(View.VISIBLE);
                            replayIv.setVisibility(View.VISIBLE);
                            replayIv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    thumbnail.setVisibility(GONE);

                                    playIv.setVisibility(GONE);
                                    pauseIv.setVisibility(GONE);
                                    player.prepare(videosource);
                                    player.setPlayWhenReady(true);
                                    replayIv.setVisibility(GONE);
                                }
                            });

                        }

                        break;
                        case Player.STATE_BUFFERING:
                            position = player.getCurrentPosition();
                            player.seekTo(position);

                            break;
                        case ExoPlayer.STATE_IDLE:
                            player.prepare(videosource);
                            player.seekTo(position);

                            break;


                        default:
                            progress.setVisibility(View.INVISIBLE);
                            replayIv.setVisibility(GONE);
                            break;

                    }
                }


                @Override
                public void onRepeatModeChanged(int repeatMode) {

                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                    position = player.getCurrentPosition();

                    player.seekTo(position);

                    player.setPlayWhenReady(true);

                    replayIv.setVisibility(GONE);

                }

                @Override
                public void onPositionDiscontinuity(int reason) {


                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }

                @Override
                public void onSeekProcessed() {

                }
            });

            exoPlayerFullscreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fullscreen();
                }
            });
            exitfullscreenIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    exitfullscreen();
                }
            });


            playIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playVideo();


                }
            });
            pauseIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    pauseVideo();

                }
            });

        } catch (Exception e) {
        }
        //---------mediaPlayer------end--------
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


    private void loadDataFromAPI() {
        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

        apiService.getSpeechesResponse().enqueue(new Callback<SpeechesResponse>() {
            @Override
            public void onResponse(Call<SpeechesResponse> call, Response<SpeechesResponse> response) {
                if (response.isSuccessful()) {
                    SpeechesResponse speechesResponse = response.body();
                    speechesList = speechesResponse.getData();
                    loadSpeeches();
                }
            }

            @Override
            public void onFailure(Call<SpeechesResponse> call, Throwable t) {

                Toast.makeText(mujibApp, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        speechesRV = findViewById(R.id.speechesRV);
        noInternetTVED = findViewById(R.id.noInternetTVE);
        mNetworkReceiver = new NetworkChangeReceiver(this);
        registerNetworkBroadcast();
    }

    private void loadSpeeches() {
        speechesAdapter = new SpeechesAdapter(this, speechesList);
        speechesRV.setLayoutManager(new LinearLayoutManager(this));
        speechesRV.setAdapter(speechesAdapter);
        swipeRefreshLayout.setRefreshing(false);
        speechesAdapter.notifyDataSetChanged();
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
                    loadSpeeches();
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
                    loadSpeeches();
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



    //---------------custom player--------start------------
    private void pauseVideo() {
        player.setPlayWhenReady(false);
        replayIv.setVisibility(GONE);
        pauseIv.setVisibility(GONE);
        playIv.setVisibility(View.VISIBLE);
        playerView.getVideoSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playIv.setVisibility(View.VISIBLE);
                pauseIv.setVisibility(GONE);
            }
        });
        exoPlayerFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullscreen();
            }
        });
        exitfullscreenIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitfullscreen();
            }
        });
    }

    private void playVideo() {
        thumbnail.setVisibility(GONE);
        replayIv.setVisibility(GONE);
        player.setPlayWhenReady(true);
        playIv.setVisibility(View.GONE);

        pauseIv.setVisibility(View.VISIBLE);
        pauseIv.postDelayed(new Runnable() {
            public void run() {
                pauseIv.setVisibility(View.INVISIBLE);
            }
        }, 3000);


        playerView.getVideoSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerView.showController();
                playerView.setVisibility(View.VISIBLE);
                pauseIv.setVisibility(View.VISIBLE);
                pauseIv.postDelayed(new Runnable() {
                    public void run() {
                        pauseIv.setVisibility(View.INVISIBLE);
                    }
                }, 3000);
            }
        });


        exoPlayerFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullscreen();
            }
        });
        exitfullscreenIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitfullscreen();
            }
        });

    }

    private void fullscreen() {

        Intent intent = new Intent(this,VideoFullScreenActivity.class);
        intent.putExtra("url",url);
       // intent.putExtra("thum",R.drawable.seventh_march);
        startActivity(intent);

    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void exitfullscreen() {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) playerView.getLayoutParams();
        params.width=params.MATCH_PARENT;
        params.height=params.WRAP_CONTENT;
        playerView.setLayoutParams(params);

        exoPlayerFullscreen.setVisibility(View.VISIBLE);
        exitfullscreenIv.setVisibility(GONE);


    }

    @Override
    protected void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
        pauseVideo();
    }
    private void playerInit() {
        playIv = findViewById(R.id.playIv);
        pauseIv = findViewById(R.id.pauseIv);
        exoPlayerFullscreen = findViewById(R.id.exo_fullscreenIv);
        exitfullscreenIv = findViewById(R.id.exit_fullscreenIv);
        thumbnail = findViewById(R.id.thumbnail);
        replayIv = findViewById(R.id.replayIv);
        progress = findViewById(R.id.exo_buffering);
        playerView = findViewById(R.id.playerview);

    }
    //---------------custom player---------end-------------


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
