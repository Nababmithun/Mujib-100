package com.ezzetech.mujib100.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ezzetech.mujib100.ConnectivityHelper;
import com.ezzetech.mujib100.CustomVisibility;
import com.ezzetech.mujib100.MujibApp;
import com.ezzetech.mujib100.NetworkChangeReceiver;
import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.adapter.MujibBooksAdapter;
import com.ezzetech.mujib100.adapter.NovelBooksAdapter;
import com.ezzetech.mujib100.interfaces.OnNetworkStateChangeListener;
import com.ezzetech.mujib100.model.Mujib;
import com.ezzetech.mujib100.model.Novel;
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
import java.util.Locale;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static android.view.View.GONE;
import static android.view.View.TEXT_ALIGNMENT_CENTER;


public class GraphicNovelActivity extends AppCompatActivity implements OnNetworkStateChangeListener {
    //for internet
    private int networkStateChangeCount = 0;
    private NetworkChangeReceiver mNetworkReceiver;
    private TextView noInternetTVED;

    Toolbar toolbar;
    CardView contactCard;
    LinearLayout linearLayout, linearLayout2;

    RelativeLayout relativeLayout;

    MujibApp mujibApp = new MujibApp();
    private RecyclerView novelRecyclerView, mujibRecyclerView;

    private NovelBooksAdapter adapter;
    private MujibBooksAdapter mujibBooksAdapter;
    private Novel novel;
    private ArrayList<Novel> novelArrayList = new ArrayList<>();
    private ArrayList<Mujib> mujibList = new ArrayList<>();
    // private VideoView videoView;

    CardView cardView2;

    //custom media play--------start----------
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private String url = "https://mujib100.gov.bd/assets/images/videos/mujib.mp4?fbclid=IwAR0lU6C2jCnPJvMI_lKg4SH_IDY5WZ4fnR6vgFxBJhHpyTCHCvSNDfSNXS4";
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
    private LinearLayout layoutArticle;
    //custom media play--------end----------

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Loadlanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic_novel);
        novelRecyclerView = findViewById(R.id.recyclerView);
        mujibRecyclerView = findViewById(R.id.mujibRecyclerView);

        layoutArticle = findViewById(R.id.layoutArticle);

        noInternetTVED = findViewById(R.id.noInternetTVE);
        mNetworkReceiver = new NetworkChangeReceiver(this);
        registerNetworkBroadcast();

        colorChangeStatusBar();
        loadNovelsImage();
        loadMujibImage();
        getAllNovels();
        getAllMujib();
        init();

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
                    //fullscreen();

                    Intent intent = new Intent(GraphicNovelActivity.this,VideoFullScreenActivity.class);
                    intent.putExtra("url",url);
                    intent.putExtra("image",R.drawable.graphic);
                    startActivity(intent);
                }
            });
            exitfullscreenIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    exitFullScreen();
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

    private void init() {
        contactCard = findViewById(R.id.contactCard);
        linearLayout = findViewById(R.id.linearLayout);
        toolbar = findViewById(R.id.toolbar);
        relativeLayout = findViewById(R.id.relativeLayout);
        linearLayout2 = findViewById(R.id.linearLayout2);
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
                exitFullScreen();
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
        }, 1000);


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
                exitFullScreen();
            }
        });

    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void fullscreen() {


        Intent intent = new Intent(GraphicNovelActivity.this,VideoFullScreenActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void exitFullScreen() {


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) playerView.getLayoutParams();
        params.width=params.MATCH_PARENT;
        params.height=params.MATCH_PARENT;
        playerView.setLayoutParams(params);

        /*     playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);*/

        exoPlayerFullscreen.setVisibility(View.VISIBLE);
        exitfullscreenIv.setVisibility(GONE);

        contactCard.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        novelRecyclerView.setVisibility(View.VISIBLE);
        mujibRecyclerView.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);

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

    private void loadNovelsImage() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adapter = new NovelBooksAdapter(GraphicNovelActivity.this, novelArrayList);
        novelRecyclerView.setLayoutManager(layoutManager);
        novelRecyclerView.setAdapter(adapter);
    }

    private void loadMujibImage() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mujibBooksAdapter = new MujibBooksAdapter(GraphicNovelActivity.this, mujibList);
        mujibRecyclerView.setLayoutManager(layoutManager);
        mujibRecyclerView.setAdapter(mujibBooksAdapter);
    }


    private void getAllNovels() {
        novelArrayList.add(new Novel(R.drawable.g1));
        novelArrayList.add(new Novel(R.drawable.g2));
        novelArrayList.add(new Novel(R.drawable.g3));
        novelArrayList.add(new Novel(R.drawable.g4));
        novelArrayList.add(new Novel(R.drawable.g5));
        novelArrayList.add(new Novel(R.drawable.g6));
        adapter.notifyDataSetChanged();
    }

    private void getAllMujib() {
        mujibList.add(new Mujib(R.drawable.m1));
        mujibList.add(new Mujib(R.drawable.m2));
        mujibList.add(new Mujib(R.drawable.m3));
        mujibList.add(new Mujib(R.drawable.m4));
        adapter.notifyDataSetChanged();
    }



}
