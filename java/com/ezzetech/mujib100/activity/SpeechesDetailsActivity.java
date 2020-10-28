package com.ezzetech.mujib100.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ezzetech.mujib100.MujibApp;
import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.speechesApiModel.Datum;
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
import com.squareup.picasso.Picasso;

import java.util.Locale;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static android.view.View.GONE;

public class SpeechesDetailsActivity extends AppCompatActivity {

    MujibApp mujibApp = new MujibApp();

    Datum speeches;
    TextView captionTV;


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
        setContentView(R.layout.activity_speeches_details);


        colorChangeStatusBar();
        init();

        Intent intent = getIntent();
        speeches = intent.getParcelableExtra("speeches");

        if (mujibApp.getSharedPrefValue()==0){
            captionTV.setText(speeches.getCaptionEn());
        }else if (mujibApp.getSharedPrefValue()==1){
            captionTV.setText(speeches.getCaptionBn());
        }


        //---------mediaPlayer------start--------
        playerInit();

        bandwidthMeter = new DefaultBandwidthMeter();
        trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        videoUri = Uri.parse(speeches.getVLink());
        Picasso.get().load(speeches.getLink()).into(thumbnail);

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


    private void init() {
        captionTV = findViewById(R.id.captionsTV);
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
       /* playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

        exoPlayerFullscreen.setVisibility(GONE);
        exitfullscreenIv.setVisibility(View.VISIBLE);*/

       /* setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        //getSupportActionBar().hide();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) playerView.getLayoutParams();
        params.width= ViewGroup.LayoutParams.MATCH_PARENT;
        params.height=ViewGroup.LayoutParams.MATCH_PARENT;
        playerView.setLayoutParams(params);

        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        playerView.requestFocus();


        exoPlayerFullscreen.setVisibility(GONE);
        exitfullscreenIv.setVisibility(View.VISIBLE);*/

        Intent intent = new Intent(this,VideoFullScreenActivity.class);
        intent.putExtra("url",speeches.getVLink());
        intent.putExtra("thumble",speeches.getLink());
        startActivity(intent);


    }


    private void exitfullscreen() {
       /* playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        exoPlayerFullscreen.setVisibility(View.VISIBLE);
        exitfullscreenIv.setVisibility(GONE);*/

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) playerView.getLayoutParams();
        params.width=params.MATCH_PARENT;
        params.height=params.WRAP_CONTENT;
        playerView.setLayoutParams(params);

        /*     playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);*/

        exoPlayerFullscreen.setVisibility(View.VISIBLE);
        exitfullscreenIv.setVisibility(GONE);

    }

    @Override
    protected void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
        pauseIv.setVisibility(GONE);
        playIv.setVisibility(View.VISIBLE);
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
