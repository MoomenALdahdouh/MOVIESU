package com.moomen.movieyou.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.moomen.movieyou.R;
import com.moomen.movieyou.adapter.VideoTrailerAdapter;

public class PlayVideoTrailerYouTube extends YouTubeBaseActivity {
    private static final String ApiKeyYouTube = "AIzaSyBnsaEGpBi3p66-2BZEvRzyhTwX1uZyE6E";
    private String keyVideo;
    private boolean isOrientation = false;
    //private RewardedAd rewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video_trailer_you_tube);
        //Full screen
        View decorViewFull = getWindow().getDecorView();
        //fullScreen(decorViewFull);
        //checkOrientation();
        //Play ads
        /*MobileAds.initialize(this);
         *//**1-Banner ad**//*
        AdView adView = findViewById(R.id.adView_banner_playe_video_id);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        *//**Native ad**//*
        if (!isOrientation) {
            rewardedAd();
            ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (rewardedAd.isLoaded()) {
                                displayAd();
                            } else
                                Log.d("TAG1", "The rewarded ad wasn't loaded yet.");
                            rewardedAd();
                        }
                    });
                }
            }, 2, 20, TimeUnit.MINUTES);
        }*/
        //Generate a videos
        Intent keyVideoMovie = getIntent();
        if (keyVideoMovie != null && keyVideoMovie.hasExtra(VideoTrailerAdapter.KEY_VIDEO)) {
            keyVideo = keyVideoMovie.getStringExtra(VideoTrailerAdapter.KEY_VIDEO);
        }
        YouTubePlayerView youTubePlayerView = findViewById(R.id.video_trailer_movie_id);
        ImageView imageViewPlayVideo = findViewById(R.id.imageView_play_video_id);
        imageViewPlayVideo.setVisibility(View.GONE);
        YouTubePlayer.OnInitializedListener onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("OverviewMovie", "onClick: Done Initializing.");
                youTubePlayer.loadVideo(keyVideo);
                //youTubePlayer.loadPlaylist();
                youTubePlayer.play();

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("OverviewMovie", "onClick: Failed to Initializing.");
            }
        };
        youTubePlayerView.initialize(ApiKeyYouTube, onInitializedListener);

    }

    //Check Orientation to select num raw on recycle
    private void checkOrientation() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isOrientation = true;
        }
    }

    //Full Screen
    private void fullScreen(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }

    /**
     * 4-Interstitial Ad
     **/
    /*private void rewardedAd() {
        rewardedAd = new RewardedAd(this, "ca-app-pub-6532316184285626/4701153023");
        rewardedAd.loadAd(new AdRequest.Builder().build(), new RewardedAdLoadCallback());
    }

    private void displayAd() {
        rewardedAd.show(this, new RewardedAdCallback() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

            }
        });
    }*/
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        /*try {
            Class.forName("com.moomen.movieyou.ui.PlayVideoTrailerYouTube")
                    .getDeclaredMethod("onPause", (Class[]) null)
                    .invoke(youTubePlayerView, (Object[]) null);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException cnfe) {
        }*/
    }
}
