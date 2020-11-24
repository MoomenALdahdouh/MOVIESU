package com.moomen.movieyou.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.moomen.movieyou.R;
import com.moomen.movieyou.adapter.SplashAdapter;
import com.moomen.movieyou.model.movies.MoviesModel;
import com.moomen.movieyou.model.movies.MoviesResults;
import com.moomen.movieyou.viewModel.MoviesViewModel;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SplashActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    boolean isConnect = false;
    private MoviesViewModel moviesViewModel;
    //SplashActivity adapter
    private SplashAdapter splashAdapter;
    private ArrayList<MoviesResults> moviesResultsArrayList;
    private ArrayList<MoviesResults> moviesResultsArrayList1 = new ArrayList<>();
    private ViewPager viewPager;
    //
    private ConstraintLayout constraintLayoutNoInternet;
    private ProgressBar progressBar;
    private Button buttonOpenApp;
    //Ad
    private RewardedAd rewardedAd;
    private InterstitialAd mInterstitialAd;
    private int countAd = 0;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        moviesViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);

        //Spinner adapter
        Spinner spinner = findViewById(R.id.spinner);
        constraintLayoutNoInternet = findViewById(R.id.constraint_check_internet_id);
        constraintLayoutNoInternet.setVisibility(View.GONE);
        progressBar = findViewById(R.id.progress_bar_splash_id);
        progressBar.setVisibility(View.GONE);
        buttonOpenApp = findViewById(R.id.button);
        buttonOpenApp.setAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_infinit));
        buttonOpenApp.setVisibility(View.GONE);
        //Underline try again text
        TextView textView = (TextView) findViewById(R.id.textView18);
        SpannableString content = new SpannableString("Try Again");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);
        //Full screen
        View decorViewFull = getWindow().getDecorView();
        fullScreen(decorViewFull);
        //Spinner
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this, R.array.media, R.layout.spinner_item);
        adapterSpinner.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spinner.setAdapter(adapterSpinner);
        spinner.setOnItemSelectedListener(this);
        //Check Internet
        checkConnection();
        //open App
        openAppOnClick();
    }

    private void openAppOnClick() {
        //Rewarded ad initialize
        openRewardedAd();
        buttonOpenApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardedAd.isLoaded())
                    displayAd(rewardedAd);
                else {
                    openRewardedAd();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
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

    //Latest Trailer movies
    private void getLatestTrailerMovies() {
        splashAdapter = new SplashAdapter(this);
        splashAdapter.setSplashActivity(true);
        splashAdapter.setMoviesResultsArrayList(moviesResultsArrayList1);
        moviesViewModel.latestTrailerMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                Log.d("test", "onChanged: " + moviesModel.getTotal_pages());
                ArrayList<MoviesResults> movies = moviesModel.getResults();
                if (movies != null) {
                    moviesResultsArrayList1.addAll(movies);
                    splashAdapter.notifyDataSetChanged();
                    checkArray();
                    MobileAds.initialize(getApplicationContext());
                    interstitialAd();
                    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mInterstitialAd.isLoaded() && countAd < 1)
                                        mInterstitialAd.show();
                                    else
                                        Log.d("ee", "not load");
                                    interstitialAd();
                                }
                            });
                        }
                    }, 1, 3, TimeUnit.MINUTES);
                }
            }
        });
        moviesViewModel.getLatestTrailer("1");
        viewPager = findViewById(R.id.view_pager_splash_id);
        viewPager.setAdapter(splashAdapter);
    }

    //Top rated TV
    private void getPopularTv() {
        moviesViewModel.getPopularTv("1");
        splashAdapter = new SplashAdapter(this);
        splashAdapter.setMoviesResultsArrayList(moviesResultsArrayList1);
        moviesViewModel.popularTvMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                moviesResultsArrayList1.addAll(moviesResultsArrayList);
                splashAdapter.notifyDataSetChanged();
                checkArray();
            }
        });
        viewPager = findViewById(R.id.view_pager_splash_id);
        viewPager.setAdapter(splashAdapter);

    }

    //Get TV animation
    private void getTVOfTypeAnimation() {
        moviesViewModel.getTVOfType("vote_count.desc", "16", "1");
        splashAdapter = new SplashAdapter(this);
        splashAdapter.setMoviesResultsArrayList(moviesResultsArrayList1);
        moviesViewModel.tVOfTypeMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                moviesResultsArrayList1.addAll(moviesResultsArrayList);
                splashAdapter.notifyDataSetChanged();
                checkArray();
            }
        });
        viewPager = findViewById(R.id.view_pager_splash_id);
        viewPager.setAdapter(splashAdapter);
    }

    //Get Movies animation
    private void getMoviesOfTypeAnimation() {
        moviesViewModel.getMoviesOfType("vote_count.desc", "16", "1");
        splashAdapter = new SplashAdapter(this);
        splashAdapter.setMoviesResultsArrayList(moviesResultsArrayList1);
        moviesViewModel.moviesOfTypeMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
//                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
//                moviesResultsArrayList1.addAll(moviesResultsArrayList);
//                splashAdapter.notifyDataSetChanged();
//                checkArray();
            }
        });
        viewPager = findViewById(R.id.view_pager_splash_id);
        viewPager.setAdapter(splashAdapter);
    }

    //Spinner controller
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        checkConnection();
        //String text = parent.getItemAtPosition(position).toString();
        if (isConnect) {
            switch (position) {
                case 0:
                    moviesResultsArrayList1.clear();
                    getLatestTrailerMovies();
                    break;
                case 1:
                    moviesResultsArrayList1.clear();
                    getPopularTv();
                    break;
                case 2:
                    moviesResultsArrayList1.clear();
                    getMoviesOfTypeAnimation();
                    break;
                case 3:
                    moviesResultsArrayList1.clear();
                    getTVOfTypeAnimation();
                    break;
            }
        } else checkConnection();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void checkArray() {
        if (!moviesResultsArrayList1.isEmpty()) {
            buttonOpenApp.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    //WIFI not Active click button turn on wifi
    public void onClickButtonTurnOnWifi(View view) {
        if (!isConnect) {
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            assert wifiManager != null;
            wifiManager.setWifiEnabled(true);
            startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), 0);
        }
        checkConnection();
    }

    //Click on try again
    public void onClickTryAgain(View view) {
        checkConnection();
        if (isConnect && moviesResultsArrayList1.isEmpty()) {
            getLatestTrailerMovies();
            Toast.makeText(getApplicationContext(), R.string.loading, Toast.LENGTH_LONG).show();
            constraintLayoutNoInternet.setVisibility(View.GONE);
        } else if (isConnect || !moviesResultsArrayList1.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.connect, Toast.LENGTH_SHORT).show();
            constraintLayoutNoInternet.setVisibility(View.GONE);
        }
    }

    //Check internet if connect or not
    private void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //When no connect
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            constraintLayoutNoInternet.setVisibility(View.VISIBLE);
        }
        //When connected
        else {
            constraintLayoutNoInternet.setVisibility(View.GONE);
            isConnect = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        checkConnection();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        checkConnection();
        //interstitialAd();
    }

    /**
     * 4-Interstitial Ad
     **/
    private void interstitialAd() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                countAd = countAd + 1;
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    /**
     * Rewarded ad display
     **/
    //load ad
    private void openRewardedAd() {
        rewardedAd = new RewardedAd(this, "ca-app-pub-3940256099942544/5224354917");
        rewardedAd.loadAd(new AdRequest.Builder().build(), new RewardedAdLoadCallback());
    }

    //Rewarded ad display
    private void displayAd(RewardedAd rewardedAd) {
        rewardedAd.show(this, new RewardedAdCallback() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                if (rewardedAd.isLoaded()) {

                } else {
                    Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                }
            }

            @Override
            public void onRewardedAdClosed() {
                super.onRewardedAdClosed();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
