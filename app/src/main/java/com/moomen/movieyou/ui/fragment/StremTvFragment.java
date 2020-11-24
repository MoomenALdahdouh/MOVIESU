package com.moomen.movieyou.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.efaso.admob_advanced_native_recyvlerview.AdmobNativeAdAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.moomen.movieyou.R;
import com.moomen.movieyou.adapter.MoviesRecycleAdapter;
import com.moomen.movieyou.model.movies.MoviesModel;
import com.moomen.movieyou.model.movies.MoviesResults;
import com.moomen.movieyou.model.tv.TvModel;
import com.moomen.movieyou.ui.SeeAllMovies;
import com.moomen.movieyou.viewModel.MoviesViewModel;

import java.util.ArrayList;

public class StremTvFragment extends Fragment implements View.OnClickListener {
    public static final String TOTAL_PAGE_AIRING_TODAY_TV = "TOTAL_PAGE_AIRING_TODAY_TV";
    public static final String TOTAL_PAGE_POPULAR_TV = "TOTAL_PAGE_POPULAR_TV";
    public static final String TOTAL_PAGE_TOP_RATE_TV = "TOTAL_PAGE_TOP_RATE_TV";
    public static final String TOTAL_PAGE_ON_THE_AIR_TV = "TOTAL_PAGE_ON_THE_AIR_TV";
    private MoviesViewModel moviesViewModel;
    private View view;
    private MoviesRecycleAdapter latestMoviesRecycleAdapter;
    private MoviesRecycleAdapter popularMoviesRecycleAdapter;
    private MoviesRecycleAdapter topRatedMoviesRecycleAdapter;
    private MoviesRecycleAdapter nowPlayingMoviesRecycleAdapter;
    private ArrayList<MoviesResults> moviesResultsArrayList;
    private ArrayList<TvModel> moviesModelArrayList2;
    private RecyclerView recyclerViewMovies;
    private Intent intent;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String getTotalPageAiringTodayTv = "1";
    private String totalPagePopularTV = "1";
    private String totalPageTopRateTV = "1";
    private String getTotalPageOnTheAirTv = "1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        view = inflater.inflate(R.layout.fragment_strem_tv, container, false);
        MobileAds.initialize(getContext());
        /**1-Banner ad**/
        //Banner ad
        AdView adView = new AdView(getContext());
        adView = view.findViewById(R.id.adView_banner_tvFragment);
        bannerAd(adView);
        ////Banner ad2
        AdView adView2 = new AdView(getContext());
        adView2 = view.findViewById(R.id.adView_banner2_tvFragment);
        bannerAd(adView2);
        ////Banner ad3
        AdView adView3 = new AdView(getContext());
        adView3 = view.findViewById(R.id.adView_banner3_tvFragment);
        bannerAd(adView3);
        ////Banner ad4
        AdView adView4 = new AdView(getContext());
        adView4 = view.findViewById(R.id.adView_banner4_tvFragment);
        bannerAd(adView4);

        Button button1 = view.findViewById(R.id.see_all_airing_today_tv_button_id);
        Button button2 = view.findViewById(R.id.see_all_popular_tv_button_id);
        Button button3 = view.findViewById(R.id.see_all_top_rate_tv_button_id);
        Button button4 = view.findViewById(R.id.see_all_on_air_tv_button_id);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);


        getAiringTV();
        getPopularTV();
        getTopRatedTV();
        getOnTheAirTV();
        swipeRefresh();

        return view;
    }

    //Latest Trailer
    @SuppressLint("FragmentLiveDataObserve")
    private void getAiringTV() {
        moviesViewModel.getTvAiringToday("1");
        latestMoviesRecycleAdapter = new MoviesRecycleAdapter(getContext());
        moviesViewModel.airingTodayTvMutableLiveData.observe(StremTvFragment.this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                try {
                    getTotalPageAiringTodayTv = String.valueOf(moviesModel.getTotal_pages());
                    moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                    latestMoviesRecycleAdapter.setList(moviesResultsArrayList);
                } catch (Exception ex) {

                }
            }
        });
        recyclerViewMovies = view.findViewById(R.id.recycleView_airing_today_tv_id);
        fillMoviesInHorizontalRecycle(recyclerViewMovies, latestMoviesRecycleAdapter);
    }

    //Popular Movies
    @SuppressLint("FragmentLiveDataObserve")
    private void getPopularTV() {
        moviesViewModel.getPopularTv("1");
        popularMoviesRecycleAdapter = new MoviesRecycleAdapter(getContext());
        moviesViewModel.popularTvMutableLiveData.observe(StremTvFragment.this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                try {
                    totalPagePopularTV = String.valueOf(moviesModel.getTotal_pages());
                    moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                    popularMoviesRecycleAdapter.setList(moviesResultsArrayList);
                } catch (Exception ex) {

                }

            }
        });
        recyclerViewMovies = view.findViewById(R.id.recyclerView_popular_tv_id);
        fillMoviesInHorizontalRecycle(recyclerViewMovies, popularMoviesRecycleAdapter);
    }

    //Top Rated Movies
    @SuppressLint("FragmentLiveDataObserve")
    private void getTopRatedTV() {
        moviesViewModel.getTopRatedTv("1");
        topRatedMoviesRecycleAdapter = new MoviesRecycleAdapter(getContext());
        moviesViewModel.topRatedTvMutableLiveData.observe(StremTvFragment.this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                try {
                    totalPageTopRateTV = String.valueOf(moviesModel.getTotal_pages());
                    moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                    topRatedMoviesRecycleAdapter.setList(moviesResultsArrayList);
                } catch (Exception ex) {

                }

            }
        });
        recyclerViewMovies = view.findViewById(R.id.recyclerView_top_rated_tv_id);
        fillMoviesInHorizontalRecycle(recyclerViewMovies, topRatedMoviesRecycleAdapter);
    }

    //Now Playing
    @SuppressLint("FragmentLiveDataObserve")
    private void getOnTheAirTV() {
        moviesViewModel.getTvOnTheAir("1");
        nowPlayingMoviesRecycleAdapter = new MoviesRecycleAdapter(getContext());
        moviesViewModel.onTheAirTvMutableLiveData.observe(StremTvFragment.this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                try {
                    getTotalPageOnTheAirTv = String.valueOf(moviesModel.getTotal_pages());
                    moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                    nowPlayingMoviesRecycleAdapter.setList(moviesResultsArrayList);
                } catch (Exception ex) {

                }

            }
        });
        recyclerViewMovies = view.findViewById(R.id.recyclerView_on_the_air_tv_id);
        fillMoviesInHorizontalRecycle(recyclerViewMovies, nowPlayingMoviesRecycleAdapter);
    }

    //Swipe Refresh
    @SuppressLint("ResourceAsColor")
    private void swipeRefresh() {
        swipeRefreshLayout = view.findViewById(R.id.swipe_tv_id);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.yellow);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAiringTV();
                getPopularTV();
                getTopRatedTV();
                getOnTheAirTV();
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
            case R.id.see_all_airing_today_tv_button_id:
                intent = new Intent(getContext(), SeeAllMovies.class);
                intent.putExtra(TOTAL_PAGE_AIRING_TODAY_TV, getTotalPageAiringTodayTv);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.see_all_popular_tv_button_id:
                intent = new Intent(getContext(), SeeAllMovies.class);
                intent.putExtra(TOTAL_PAGE_POPULAR_TV, totalPagePopularTV);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.see_all_top_rate_tv_button_id:
                intent = new Intent(getContext(), SeeAllMovies.class);
                intent.putExtra(TOTAL_PAGE_TOP_RATE_TV, totalPageTopRateTV);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.see_all_on_air_tv_button_id:
                intent = new Intent(getContext(), SeeAllMovies.class);
                intent.putExtra(TOTAL_PAGE_ON_THE_AIR_TV, getTotalPageOnTheAirTv);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    /**
     * Banner ad
     **/
    private void bannerAd(AdView adView) {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
