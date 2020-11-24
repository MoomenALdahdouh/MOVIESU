package com.moomen.movieyou.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayout;
import com.moomen.movieyou.R;
import com.moomen.movieyou.adapter.MoviesRecycleAdapter;
import com.moomen.movieyou.adapter.SectionsPagerAdapter;
import com.moomen.movieyou.db.RateDB;
import com.moomen.movieyou.model.movies.MoviesModel;
import com.moomen.movieyou.model.movies.MoviesResults;
import com.moomen.movieyou.viewModel.MoviesViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    /***/
    private final MoviesRecycleAdapter recycleAdapter = new MoviesRecycleAdapter(this);
    private ArrayList<MoviesResults> moviesResultsArrayList = new ArrayList<>();
    private ArrayList<MoviesResults> moviesResultsArrayList1 = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesViewModel moviesViewModel;
    private int totalPages = 1;
    private int page = 1;
    private boolean isFirstBackPressed = false;
    private TabLayout tabLayout;
    //For internet
    private boolean isConnect = false;
    private ConstraintLayout constraintLayoutNoInternet;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());

        //reat app
        AppRater.app_launched(this);
        RateDB rateDB = new RateDB(this);
        rateDB.writeReatedStatuse("", "true", "");
        rateDB.writeReatedStatuse("", "", "true");
        //AppRater.showRateDialog(MainActivity.this, null);

        MobileAds.initialize(this);
        interstitialAd();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mInterstitialAd.isLoaded())
                            mInterstitialAd.show();
                        else
                            Log.d("ee", "not load");
                        interstitialAd();
                    }
                });
            }
        }, 5, 5, TimeUnit.MINUTES);

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        tabLayout = findViewById(R.id.tabs);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        recyclerView = findViewById(R.id.recycler_view_search);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        boolean changeWidthLayoutMovieItem = true;
        recycleAdapter.setChangeWidthLayoutMovieItem(changeWidthLayoutMovieItem);
        recycleAdapter.setLayoutId(R.layout.search_item);
        recycleAdapter.setList(moviesResultsArrayList1);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setHasFixedSize(true);
        //Check Internet
        constraintLayoutNoInternet = findViewById(R.id.constraint_check_internet_id);
        constraintLayoutNoInternet.setVisibility(View.GONE);
        checkConnection();
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
        if (isConnect || !moviesResultsArrayList1.isEmpty()) {
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

    //Search on Tv or movie
    private void searchOnMoviesOrTV(String textToSearch, int page) {
        moviesViewModel.searchOnMoviesOrTV(textToSearch, page + "");
        moviesViewModel.searchOnMoviesOrTVMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                totalPages = moviesModel.getTotal_pages();
                //moviesResultsArrayList1.clear();
                for (int i = 0; i < moviesResultsArrayList.size(); i++) {
                    if (!moviesResultsArrayList1.contains(moviesResultsArrayList.get(i)))
                        moviesResultsArrayList1.add(moviesResultsArrayList.get(i));
                }
                recycleAdapter.notifyDataSetChanged();
            }
        });
    }

    //Search view filter
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search_id);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //At click on search icon
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    tabLayout.setVisibility(View.VISIBLE);
                    moviesResultsArrayList1.clear();
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.GONE);
                    moviesResultsArrayList1.clear();
                    recycleAdapter.setLayoutId(R.layout.search_item);
                    searchOnMoviesOrTV(query, page);
                    recycleAdapter.getFilter().filter(query);
                }
                return false;
            }

            //When change on text search automatic
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    tabLayout.setVisibility(View.VISIBLE);
                    moviesResultsArrayList1.clear();
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.GONE);
                    moviesResultsArrayList1.clear();
                    recycleAdapter.setLayoutId(R.layout.search_item);
                    searchOnMoviesOrTV(newText, page);
                    recycleAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                recyclerView.setVisibility(View.GONE);
                tabLayout.setVisibility(View.VISIBLE);
                return false;
            }
        });
        return true;
    }

    //Menu item select
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search_id:
                Toast.makeText(this, R.string.search, Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_account:
                Toast.makeText(this, "account", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //If click back gone recycle search
    @Override
    public void onBackPressed() {
        //if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
           /* super.onBackPressed();
        } else {
            if (isFirstBackPressed) {
                super.onBackPressed();
            } else {
                isFirstBackPressed = true;*/
        RateDB rateDB = new RateDB(getApplicationContext());
        ArrayList<String> remindList = rateDB.readRemindStatuse();
        ArrayList<String> thanksList = rateDB.readThankStatuse();
        if (!rateDB.readReatedStatuse().equals("true")) { //If is not rated app yet
            if (remindList.size() % 7 == 1) {//If click on remind me 5 or dublcate 5 times show dialog
                AppRater.showRateDialog(MainActivity.this, null);
            } else if (thanksList.size() % 12 == 5) {//If click on no thanks 10 or dublcate 10 times show dialog
                AppRater.showRateDialog(MainActivity.this, null);
            } else
                super.onBackPressed();

               /* if (recyclerView.getVisibility() == View.VISIBLE) {
                    recyclerView.setVisibility(View.GONE);
                    moviesResultsArrayList1.clear();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isFirstBackPressed = false;
                    }
                }, 1500);*/
        }
        //}
    }

    /**
     * 4-Interstitial Ad
     **/
    private void interstitialAd() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public static class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(@NonNull Thread thread, Throwable ex) {
            if (ex.getClass().equals(OutOfMemoryError.class)) {
                try {
                    android.os.Debug.dumpHprofData("/sdcard/dump.hprof");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ex.printStackTrace();
        }

    }
}