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
import com.moomen.movieyou.R;
import com.moomen.movieyou.adapter.MoviesRecycleAdapter;
import com.moomen.movieyou.model.movies.MoviesModel;
import com.moomen.movieyou.model.movies.MoviesResults;
import com.moomen.movieyou.ui.SeeAllMovies;
import com.moomen.movieyou.viewModel.MoviesViewModel;

import java.util.ArrayList;

public class AnimeFragment extends Fragment implements View.OnClickListener {
    public static final String TOTAL_PAGE_MOVIES_ANIMATION = "TOTAL_PAGE_MOVIES_ANIMATION";
    public static final String TOTAL_PAGE_TV_ANIMATION = "TOTAL_PAGE_TV_ANIMATION";
    private MoviesViewModel moviesViewModel;
    private View view;
    private MoviesRecycleAdapter moviesAnimationRecycleAdapter;
    private MoviesRecycleAdapter tVAnimationRecycleAdapter;
    private ArrayList<MoviesResults> moviesAnimationModelArrayList;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button button1;
    private Button button2;
    private Intent intent;
    private String totalPageMoviesAnimation = "1";
    private String totalPageTVAnimation = "1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        view = inflater.inflate(R.layout.fragment_anime, container, false);
        button1 = view.findViewById(R.id.see_all_movies_animation_button_id);
        button2 = view.findViewById(R.id.see_all_tv_animation_button_id);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_animation_movies_id);

        /**1-Banner ad**/
        //Banner ad
        AdView adView = new AdView(getContext());
        adView = view.findViewById(R.id.adView_banner_animeFragment);
        bannerAd(adView);
        ////Banner ad2
        AdView adView2 = new AdView(getContext());
        adView2 = view.findViewById(R.id.adView_banner2_animeFragment);
        bannerAd(adView2);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        getMoviesOfTypeAnimation();
        getTVOfTypeAnimation();

        swipeRefresh();
        return view;
    }

    //Get movies animation
    @SuppressLint("FragmentLiveDataObserve")
    private void getMoviesOfTypeAnimation() {
        moviesViewModel.getMoviesOfType("vote_count.desc", "16", "1");
        moviesAnimationRecycleAdapter = new MoviesRecycleAdapter(getContext());
        moviesViewModel.moviesOfTypeMutableLiveData.observe(AnimeFragment.this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                moviesAnimationModelArrayList = new ArrayList<>(moviesModel.getResults());
                totalPageMoviesAnimation = String.valueOf(moviesModel.getTotal_pages());
                moviesAnimationRecycleAdapter.setList(moviesAnimationModelArrayList);
            }
        });
        recyclerView = view.findViewById(R.id.recycleView_animation_movies_id);
        fillMoviesInHorizontalRecycle(recyclerView, moviesAnimationRecycleAdapter);
    }

    //Get TV animation
    @SuppressLint("FragmentLiveDataObserve")
    private void getTVOfTypeAnimation() {
        moviesViewModel.getTVOfType("vote_count.desc", "16", "1");
        tVAnimationRecycleAdapter = new MoviesRecycleAdapter(getContext());
        moviesViewModel.tVOfTypeMutableLiveData.observe(AnimeFragment.this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                moviesAnimationModelArrayList = new ArrayList<>(moviesModel.getResults());
                totalPageTVAnimation = String.valueOf(moviesModel.getTotal_pages());
                tVAnimationRecycleAdapter.setList(moviesAnimationModelArrayList);
            }
        });
        recyclerView = view.findViewById(R.id.recycleView_animation_tv_id);
        fillMoviesInHorizontalRecycle(recyclerView, tVAnimationRecycleAdapter);
    }

    //Swipe Refresh
    @SuppressLint("ResourceAsColor")
    private void swipeRefresh() {
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.yellow);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMoviesOfTypeAnimation();
                getTVOfTypeAnimation();
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
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.see_all_movies_animation_button_id:
                intent = new Intent(getContext(), SeeAllMovies.class);
                intent.putExtra(TOTAL_PAGE_MOVIES_ANIMATION, totalPageMoviesAnimation);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.see_all_tv_animation_button_id:
                intent = new Intent(getContext(), SeeAllMovies.class);
                intent.putExtra(TOTAL_PAGE_TV_ANIMATION, totalPageTVAnimation);
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
