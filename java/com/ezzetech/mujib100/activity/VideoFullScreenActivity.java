package com.ezzetech.mujib100.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ezzetech.mujib100.R;
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
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import static android.view.View.GONE;
import static android.view.View.TEXT_ALIGNMENT_CENTER;

public class VideoFullScreenActivity extends AppCompatActivity {


    //custom media play--------start----------
    private PlayerView playerView;
    private SimpleExoPlayer player;
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
    String url;
    String thumbleIV;
    int  thumble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_full_screen);

        playerInit();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            url = getIntent().getStringExtra("url");
            thumbleIV = getIntent().getStringExtra("thumble");
          //  thumble = getIntent().getIntExtra("thum",0);
        }


        //---------mediaPlayer------start--------

        bandwidthMeter = new DefaultBandwidthMeter();
        trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        videoUri = Uri.parse(url);
        Picasso.get().load(thumbleIV).into(thumbnail);
      //  thumbnail.setImageResource(thumble);

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
     /*   exoPlayerFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullscreen();
            }
        });*/
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
               // fullscreen();
            }
        });
        exitfullscreenIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitFullScreen();
            }
        });

    }

    /*   @SuppressLint("SourceLockedOrientationActivity")
   /*  private void fullscreen() {

        *//*  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();
    *//**//*    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) playerView.getLayoutParams();
        params.width=params.MATCH_PARENT;
        params.height=600;
        playerView.setLayoutParams(params);*//**//*

        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        playerView.requestFocus();

        exoPlayerFullscreen.setVisibility(GONE);
        exitfullscreenIv.setVisibility(View.VISIBLE);*//*

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) playerView.getLayoutParams();
        params.width=params.MATCH_PARENT;
        params.height=params.MATCH_PARENT;
        playerView.setLayoutParams(params);

   *//*     playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        playerView.requestFocus();
*//*
        exoPlayerFullscreen.setVisibility(GONE);
        exitfullscreenIv.setVisibility(View.VISIBLE);

        // cardView2.setVisibility(GONE);
        layoutArticle.setVisibility(GONE);

    }
*/
    @SuppressLint("SourceLockedOrientationActivity")
    private void exitFullScreen() {

        onBackPressed();
        finish();
        //startActivity(new Intent(this,GraphicNovelActivity.class));

    /*  *//*  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        exoPlayerFullscreen.setVisibility(View.VISIBLE);
        exitfullscreenIv.setVisibility(GONE);*//*

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) playerView.getLayoutParams();
        params.width=params.MATCH_PARENT;
        params.height=params.MATCH_PARENT;
        playerView.setLayoutParams(params);

        *//*     playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);*//*

        exoPlayerFullscreen.setVisibility(View.VISIBLE);
        exitfullscreenIv.setVisibility(GONE);

        contactCard.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        novelRecyclerView.setVisibility(View.VISIBLE);
        mujibRecyclerView.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);

*/

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

        exitfullscreenIv.setVisibility(View.VISIBLE);
        exoPlayerFullscreen.setVisibility(View.INVISIBLE);
    }
    //---------------custom player---------end-------------
}
