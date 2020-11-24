package com.moomen.movieyou.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.efaso.admob_advanced_native_recyvlerview.AdmobNativeAdAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.moomen.movieyou.R;
import com.moomen.movieyou.adapter.MoviesRecycleAdapter;
import com.moomen.movieyou.model.movies.MoviesModel;
import com.moomen.movieyou.model.movies.MoviesResults;
import com.moomen.movieyou.ui.SeeAllMovies;
import com.moomen.movieyou.viewModel.MoviesViewModel;

import java.util.ArrayList;

public class MoviesFragment extends Fragment implements View.OnClickListener {

    public static final String TOTAL_PAGE_LATEST = "TOTAL_PAGE_LATEST";
    public static final String TOTAL_PAGE_POPULAR = "TOTAL_PAGE_POPULAR";
    public static final String TOTAL_PAGE_TOP_RATE = "TOTAL_PAGE_TOP_RATE";
    public static final String TOTAL_PAGE_NOW_PLAY = "TOTAL_PAGE_NOW_PLAY";
    private MoviesViewModel moviesViewModel;
    private View view;
    private MoviesRecycleAdapter latestMoviesRecycleAdapter;
    private MoviesRecycleAdapter popularMoviesRecycleAdapter;
    private MoviesRecycleAdapter topRatedMoviesRecycleAdapter;
    private MoviesRecycleAdapter nowPlayingMoviesRecycleAdapter;
    private ArrayList<MoviesResults> moviesResultsArrayList;
    private RecyclerView recyclerViewMovies;
    private Intent intent;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String totalPageLastMovies = "1";
    private String totalPagePopularMovies = "1";
    private String totalPageNowPlayingMovies = "1";
    private String totalPageTopRateMovies = "1";
    //Ad
    private RewardedAd rewardedAd;
    private InterstitialAd mInterstitialAd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        view = inflater.inflate(R.layout.fragment_movies, container, false);
        MobileAds.initialize(getContext());
        /**1-Banner ad**/
        //Banner ad
        AdView adView = new AdView(getContext());
        adView = view.findViewById(R.id.adView_banner);
        bannerAd(adView);
        ////Banner ad2
        AdView adView2 = new AdView(getContext());
        adView2 = view.findViewById(R.id.adView_banner2);
        bannerAd(adView2);
        ////Banner ad3
        AdView adView3 = new AdView(getContext());
        adView3 = view.findViewById(R.id.adView_banner3);
        bannerAd(adView3);
        ////Banner ad4
        AdView adView4 = new AdView(getContext());
        adView4 = view.findViewById(R.id.adView_banner4);
        bannerAd(adView4);

        /**InterstitialAd**/
        initializeInterstitialAd();
        Button button1 = view.findViewById(R.id.see_all_latest_movies_button_id);
        Button button2 = view.findViewById(R.id.see_all_popular_button_id);
        Button button3 = view.findViewById(R.id.see_all_top_rate_button_id);
        Button button4 = view.findViewById(R.id.see_all_now_playing_button_id);


        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        rewardedAd = new RewardedAd(getContext(), "ca-app-pub-3940256099942544/5224354917");
        rewardedAd.loadAd(new AdRequest.Builder().build(), new RewardedAdLoadCallback());

        getLatestTrailerMovies();
        getPopularMovies();
        getTopRatedMovies();
        getNowPlayingMovies();
        swipeRefresh();
        return view;
    }

    //Latest Trailer
    @SuppressLint("FragmentLiveDataObserve")
    private void getLatestTrailerMovies() {
        moviesViewModel.getLatestTrailer("1");
        latestMoviesRecycleAdapter = new MoviesRecycleAdapter(getContext());
        moviesViewModel.latestTrailerMutableLiveData.observe(MoviesFragment.this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                totalPageLastMovies = String.valueOf(moviesModel.getTotal_pages());
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                latestMoviesRecycleAdapter.setList(moviesResultsArrayList);
            }
        });
        recyclerViewMovies = view.findViewById(R.id.recycleView_latest_trailers_id);
        fillMoviesInHorizontalRecycle(recyclerViewMovies, latestMoviesRecycleAdapter);
    }

    //Popular Movies
    @SuppressLint("FragmentLiveDataObserve")
    private void getPopularMovies() {
        moviesViewModel.getMoviesPopular("1");
        popularMoviesRecycleAdapter = new MoviesRecycleAdapter(getContext());
        moviesViewModel.moviesPopularMutableLiveData.observe(MoviesFragment.this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                totalPagePopularMovies = String.valueOf(moviesModel.getTotal_pages());
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                popularMoviesRecycleAdapter.setList(moviesResultsArrayList);
            }
        });
        recyclerViewMovies = view.findViewById(R.id.recyclerView_popular_movies_id);
        fillMoviesInHorizontalRecycle(recyclerViewMovies, popularMoviesRecycleAdapter);
    }

    //Top Rated Movies
    @SuppressLint("FragmentLiveDataObserve")
    private void getTopRatedMovies() {
        moviesViewModel.getMoviesTopRated("1");
        topRatedMoviesRecycleAdapter = new MoviesRecycleAdapter(getContext());
        moviesViewModel.moviesTopRatedMutableLiveData.observe(MoviesFragment.this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                totalPageTopRateMovies = String.valueOf(moviesModel.getTotal_pages());
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                topRatedMoviesRecycleAdapter.setList(moviesResultsArrayList);
            }
        });
        recyclerViewMovies = view.findViewById(R.id.recyclerView_top_rated_move_id);
        fillMoviesInHorizontalRecycle(recyclerViewMovies, topRatedMoviesRecycleAdapter);
    }

    //Now Playing
    @SuppressLint("FragmentLiveDataObserve")
    private void getNowPlayingMovies() {
        moviesViewModel.getMoviesNowPlaying("1");
        nowPlayingMoviesRecycleAdapter = new MoviesRecycleAdapter(getContext());
        moviesViewModel.moviesNowPlayingMutableLiveData.observe(MoviesFragment.this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                totalPageNowPlayingMovies = String.valueOf(moviesModel.getTotal_pages());
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                nowPlayingMoviesRecycleAdapter.setList(moviesResultsArrayList);
            }
        });
        recyclerViewMovies = view.findViewById(R.id.recyclerView_now_playing_movies_id);
        fillMoviesInHorizontalRecycle(recyclerViewMovies, nowPlayingMoviesRecycleAdapter);
    }

    //Swipe Refresh
    @SuppressLint("ResourceAsColor")
    private void swipeRefresh() {
        swipeRefreshLayout = view.findViewById(R.id.swipe_main_id);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.yellow);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLatestTrailerMovies();
                getPopularMovies();
                getTopRatedMovies();
                getNowPlayingMovies();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);
            }
        });
    }

    //Fill data on Horizontal recycler view
    private void fillMoviesInHorizontalRecycle(RecyclerView recyclerView, MoviesRecycleAdapter moviesRecycleAdapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        //Create a native ad id from admob console
        moviesRecycleAdapter.setIsHorizontalRecycle(true);
        moviesRecycleAdapter.setPositionAd(1);
        AdmobNativeAdAdapter admobNativeAdAdapterHor = AdmobNativeAdAdapter.Builder
                .with(
                        "ca-app-pub-3940256099942544/2247696110",//Create a native ad id from admob console
                        moviesRecycleAdapter,//The adapter you would normally set to your recyClerView
                        "medium" //"small","medium"or"custom"
                )
                .adItemIterval(10)//native ad repeating interval in the recyclerview
                .build();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(admobNativeAdAdapterHor);
    }

    //See All movies for this part after a click on button See All >>
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.see_all_latest_movies_button_id:
                openRewardedAd();
                break;
            case R.id.see_all_popular_button_id:
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                intent = new Intent(getContext(), SeeAllMovies.class);
                intent.putExtra(TOTAL_PAGE_POPULAR, totalPagePopularMovies);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.see_all_top_rate_button_id:
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                intent = new Intent(getContext(), SeeAllMovies.class);
                intent.putExtra(TOTAL_PAGE_TOP_RATE, totalPageTopRateMovies);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.see_all_now_playing_button_id:
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                intent = new Intent(getContext(), SeeAllMovies.class);
                intent.putExtra(TOTAL_PAGE_NOW_PLAY, totalPageNowPlayingMovies);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    private void openRewardedAd() {
        if (rewardedAd.isLoaded()) {
            rewardedAd.show(getActivity(), new RewardedAdCallback() {
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
                    intent = new Intent(getContext(), SeeAllMovies.class);
                    intent.putExtra(TOTAL_PAGE_LATEST, totalPageLastMovies);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        } else {
            intent = new Intent(getContext(), SeeAllMovies.class);
            intent.putExtra(TOTAL_PAGE_LATEST, totalPageLastMovies);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    /**
     * 4-Interstitial Ad
     **/
    private void initializeInterstitialAd() {
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    /**
     * Banner ad
     **/
    private void bannerAd(AdView adView) {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
