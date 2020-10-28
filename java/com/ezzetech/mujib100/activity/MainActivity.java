package com.ezzetech.mujib100.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ezzetech.mujib100.ConnectivityHelper;
import com.ezzetech.mujib100.CustomVisibility;
import com.ezzetech.mujib100.MujibApp;
import com.ezzetech.mujib100.NetworkChangeReceiver;
import com.ezzetech.mujib100.R;
import com.ezzetech.mujib100.adapter.CustomExpandableListAdapter;
import com.ezzetech.mujib100.adapter.RecognitionAdapter;
import com.ezzetech.mujib100.adapter.SliderAdapterExample;
import com.ezzetech.mujib100.adapter.SliderAdapterForPhoto;
import com.ezzetech.mujib100.apiAdapter.NewsFeedForMailActivityAdapter;
import com.ezzetech.mujib100.common.Common;
import com.ezzetech.mujib100.config;
import com.ezzetech.mujib100.datasource.ExpandableListDataSource;

import com.ezzetech.mujib100.interfaces.ApiInterface;
import com.ezzetech.mujib100.interfaces.OnNetworkStateChangeListener;
import com.ezzetech.mujib100.model.Recognize;
import com.ezzetech.mujib100.newsFeedApiModel.Datum;
import com.ezzetech.mujib100.newsFeedApiModel.NewsFeedResponse;
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
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class MainActivity extends YouTubeBaseActivity implements CustomExpandableListAdapter.OnExpandableListener,
        OnNetworkStateChangeListener,YouTubePlayer.OnInitializedListener {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    //social feed
    private RecyclerView socialFeedsRV;
    List<Datum> newsFeedList = new ArrayList<>();
    NewsFeedForMailActivityAdapter newsFeedAdapter;
    private ApiInterface apiService;


    TextView virtualTourTV;

    TextView noInternetTVED;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] items;
    private String[] mujibItem;

    private RecyclerView recognizeRecyclerView;
    private ArrayList<Recognize> recognizes = new ArrayList<>();
    private RecognitionAdapter recognitionAdapter;


    private Toolbar toolbar;
    private RelativeLayout playViewRL;

    private ExpandableListView mExpandableListView;
    private ExpandableListAdapter mExpandableListAdapter;

    private List<String> mExpandableListTitle;
    private List<String> mExpandableTitle;


    private Map<String, List<String>> mExpandableListData;

    // for image Slider
    private SliderView sliderView;
    private SliderAdapterExample sliderAdapterExample;

    //for internet
    private int networkStateChangeCount = 0;
    private NetworkChangeReceiver mNetworkReceiver;
    private SwipeRefreshLayout swipeRefreshLayout;


    //for photo Slider
    private SliderView photoSliderView;
    private SliderAdapterForPhoto sliderAdapterForPhoto;

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
    //custom media play--------end----------

    //Youtube Video
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    String videoUrl;
    YouTubePlayer.OnInitializedListener initializedListener;


    MujibApp mujibApp = new MujibApp();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Loadlanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //init sharePre
        sharedPreferences = this.getSharedPreferences("languageState", Context.MODE_PRIVATE);


        virtualTourTV = findViewById(R.id.virtualTourTV);

        playViewRL = findViewById(R.id.playVideoRL);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawers();
        mActivityTitle = getTitle().toString();
        mExpandableListView = (ExpandableListView) findViewById(R.id.navList);
        //FragmentNavigationManager  mNavigationManager = FragmentNavigationManager.obtain(MainActivity.this);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
       /* setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        initSwipeLayout();
        initImageSlider();
        initPhotoSlider();
        initItems();

        init();

        loadDataFromAPI();

        //changeLang();

        virtualTourTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VirtualTourActivity.class));
            }
        });

        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.nav_header, null, false);
        mExpandableListView.addHeaderView(listHeaderView);

        mExpandableListData = ExpandableListDataSource.getData(this);
        mExpandableListTitle = new ArrayList(mExpandableListData.keySet());
        mExpandableTitle = new ArrayList(mExpandableListData.keySet());

        addDrawerItems();
        setupDrawer();

        loadRecognize();
        getAllRecognize();
        colorChangeStatusBar();

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


        playViewRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, YoutubeActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(intent);
            }
        });

    }





    private void loadDataFromAPI() {
        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);

        apiService.getNewsFeedResponse().enqueue(new Callback<NewsFeedResponse>() {
            @Override
            public void onResponse(Call<NewsFeedResponse> call, Response<NewsFeedResponse> response) {
                if (response.isSuccessful()){
                    NewsFeedResponse nwesFeedResponse = response.body();

                        newsFeedList =  nwesFeedResponse.getData();
                        loadNewsFeeds();



                    swipeRefreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call<NewsFeedResponse> call, Throwable t) {

            }
        });
    }

    private void loadNewsFeeds() {
        newsFeedAdapter = new NewsFeedForMailActivityAdapter(this, newsFeedList);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        socialFeedsRV.setLayoutManager(layoutManager);
        socialFeedsRV.setAdapter(newsFeedAdapter);
        swipeRefreshLayout.setRefreshing(false);
        newsFeedAdapter.notifyDataSetChanged();
    }

    private void init() {
        socialFeedsRV = findViewById(R.id.socialFeedRV);
        noInternetTVED = findViewById(R.id.noInternetTVE);
        mNetworkReceiver = new NetworkChangeReceiver(this);
        registerNetworkBroadcast();

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_player);



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
                    loadNewsFeeds();
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

    public void colorChangeStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white));
        }
    }


    private void loadRecognize() {
        recognizeRecyclerView = findViewById(R.id.recognizeRecyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recognitionAdapter = new RecognitionAdapter(this, recognizes);
        recognizeRecyclerView.setLayoutManager(layoutManager);
        recognizeRecyclerView.setAdapter(recognitionAdapter);
    }

    private void getAllRecognize() {
        recognizes.add(new Recognize(R.drawable.indira_gandh, getString(R.string.designation_india), getString(R.string.gandi),
                getString(R.string.gandhi_say)));

        recognizes.add(new Recognize(R.drawable.fidel_castro, getString(R.string.designation_cuda), getString(R.string.fidel),
                getString(R.string.fidel_say)));

        recognizes.add(new Recognize(R.drawable.annada_shankar_ray, getString(R.string.designation_annada), getString(R.string.annada_shanaka_ray),
                getString(R.string.annada_shanaka_say)));

        recognitionAdapter.notifyDataSetChanged();
    }

    public void initImageSlider() {
        sliderView = findViewById(R.id.imageSlider);
        sliderAdapterExample = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(sliderAdapterExample);

        /*-------------initBanner---start----------*/
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
       /* sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);*/
        sliderView.setScrollTimeInSec(3); //set scroll delay in seconds :
        sliderView.startAutoCycle();
        /*-------------initBanner---end----------*/
    }

    private void initPhotoSlider() {
        photoSliderView = findViewById(R.id.photoSlider);
        sliderAdapterForPhoto = new SliderAdapterForPhoto(this);
        photoSliderView.setSliderAdapter(sliderAdapterForPhoto);

        /*-------------initBanner---start----------*/
        photoSliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        photoSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        photoSliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        photoSliderView.setIndicatorSelectedColor(Color.WHITE);
        photoSliderView.setIndicatorUnselectedColor(Color.GRAY);
        photoSliderView.setScrollTimeInSec(3); //set scroll delay in seconds :
        photoSliderView.startAutoCycle();
        /*-------------initBanner---end----------*/
    }

    //font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    private void initItems() {
        items = getResources().getStringArray(R.array.all_title);
        mujibItem = getResources().getStringArray(R.array.mujib);
    }

    private void addDrawerItems() {
        mExpandableListAdapter = new CustomExpandableListAdapter(this, mExpandableListTitle, mExpandableListData, this);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (groupPosition == 5) {
                    startActivity(new Intent(MainActivity.this, CommitteeActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else if (groupPosition == 6) {
                    startActivity(new Intent(MainActivity.this, WishesActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else if (groupPosition == 7) {
                    startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                } else if (groupPosition == 8) {
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }
                return false;
            }
        });
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {


                //   getSupportActionBar().setTitle(mExpandableListTitle.get(groupPosition).toString());
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

                // getSupportActionBar().setTitle(R.string.film_genres);
                // Toast.makeText(MainActivity.this, groupPosition+"", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {


            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                /*    getSupportActionBar().setTitle(R.string.film_genres);*/
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGroupItemListener(int position) {

    }


    @Override
    public boolean onChildItemListener(int groupPosition, int childPosition) {
        // Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
        if (groupPosition == 0) {
            switch (childPosition) {
                case 0:
                    startActivity(new Intent(MainActivity.this, TimeLineActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 1:
                    startActivity(new Intent(MainActivity.this, SpeechesActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, RecognitionsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 3:
                    startActivity(new Intent(MainActivity.this, PhotoArchiveActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 4:
                    startActivity(new Intent(MainActivity.this, GraphicNovelActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                default:
                    return false;
            }

        } else if (groupPosition == 1) {

            switch (childPosition) {
                case 0:
                    startActivity(new Intent(MainActivity.this, NewsFeedActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    // startActivity(new Intent(MainActivity.this, Media.class));
                    break;
                case 1:
                    startActivity(new Intent(MainActivity.this, StampActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 2:
                    break;

                default:
                    return false;
            }

        } else if (groupPosition == 2) {
            switch (childPosition) {
                case 0:
                    startActivity(new Intent(MainActivity.this, BooksAndPublicationActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 1:
                    startActivity(new Intent(MainActivity.this, DocumentariesActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, LettersActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 3:
                    startActivity(new Intent(MainActivity.this, LogoAndGuidelineActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 4:
                    startActivity(new Intent(MainActivity.this, PosterAndPublicationsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 5:
                    startActivity(new Intent(MainActivity.this, CommitteeActionPlanActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;

            }
        } else if (groupPosition == 3) {
            switch (childPosition) {
                case 0:
                    startActivity(new Intent(MainActivity.this, EventsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 1:
                    startActivity(new Intent(MainActivity.this, EventsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, InternationalCelebrationsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;

            }
        } else if (groupPosition == 4) {
            switch (childPosition) {
                case 0:
                    startActivity(new Intent(MainActivity.this, PressReleaseActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case 1:
                    startActivity(new Intent(MainActivity.this, NoticeActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
            }


        }
       /* else  if (groupPosition ==6){
            startActivity(new Intent(MainActivity.this, CommitteeActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }*/
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void clickTimeline(View view) {
        startActivity(new Intent(MainActivity.this, TimeLineActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void showRecognize(View view) {
        startActivity(new Intent(MainActivity.this, RecognitionsActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void showPhotoArchive(View view) {
        startActivity(new Intent(MainActivity.this, PhotoArchiveActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void showGraphicNovel(View view) {
        startActivity(new Intent(MainActivity.this, GraphicNovelActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

        Intent intent = new Intent(this,VideoFullScreenActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);

    }


    @SuppressLint("SourceLockedOrientationActivity")
    private void exitfullscreen() {

     /*   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        exoPlayerFullscreen.setVisibility(View.VISIBLE);
        exitfullscreenIv.setVisibility(GONE);*/

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) playerView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        playerView.setLayoutParams(params);
        //showSystemUI();
        /*     playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);*/

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

    //-------------for internet check------start---------
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
    //-------------for internet check-------end-----------------

    public void goNewsDetails(View view) {
        startActivity(new Intent(MainActivity.this, NewsFeedActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }



    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubeView.initialize(config.YOUTUBE_API_KEY, this);
        youTubePlayer.loadVideo(videoUrl);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
