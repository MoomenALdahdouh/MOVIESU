package com.moomen.movieyou.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.efaso.admob_advanced_native_recyvlerview.AdmobNativeAdAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.moomen.movieyou.R;
import com.moomen.movieyou.adapter.MoviesRecycleAdapter;
import com.moomen.movieyou.model.movies.MoviesModel;
import com.moomen.movieyou.model.movies.MoviesResults;
import com.moomen.movieyou.ui.fragment.AnimeFragment;
import com.moomen.movieyou.ui.fragment.HomeFragment;
import com.moomen.movieyou.ui.fragment.MoviesFragment;
import com.moomen.movieyou.ui.fragment.StremTvFragment;
import com.moomen.movieyou.viewModel.MoviesViewModel;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SeeAllMovies extends AppCompatActivity {

    private static int pageNum = 1;
    private static int numTypeData;
    private static int numTypeDataAfterFilter;
    private final MoviesRecycleAdapter recycleAdapter = new MoviesRecycleAdapter(this);
    private ArrayList<MoviesResults> moviesResultsArrayList = new ArrayList<>();
    private ArrayList<MoviesResults> moviesResultsArrayList1 = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesViewModel moviesViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Integer> span = new ArrayList<>();
    private boolean isChangeView = false;
    private ImageView changeViewImageView;
    //To get more movies when scrolling
    private Boolean isScrolling = false;
    private int totalPages;
    private int currentItems, totalItems, scrollOutItems;
    //Search
    private ProgressBar progressBar;
    private int spanCount;
    private boolean search = false;
    private SearchView searchView;
    //Filter and Discover media with gneres
    private boolean buttonIsOnClick = false;
    private int count = 0;
    //Parameters to filter
    private String gneres = "";
    private String cuntry = "";
    private String sortBy = "popularity.desc";
    private String rated = "";
    private String popularty = "";
    private String releaseDate = "";
    //Components filter
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchMedia;
    private ImageView imageViewFilter;
    private String mediaType = "movie";
    //For configoration language filter
    private ArrayList<String> languages = new ArrayList<>();
    private ArrayList<String> cuntryArray = new ArrayList<>();
    //For dialog filter
    private SeekBar seekBarRated;
    private SeekBar seekBarDateRealse;
    private TextView textSeekBarRadted;
    private TextView textSeekBarRealseDate;
    //Button filter
    private ArrayList<Integer> buttonArrayList = new ArrayList<>();
    //Ad
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_movies);
        //interstitial Ad
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
        //
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        searchView = findViewById(R.id.search_view_see_all_activity_id);
        imageViewFilter = findViewById(R.id.imageView_filter_see_all_id);
        //Image change view
        changeViewImageView = findViewById(R.id.change_view_see_all_id);
        changeViewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChangeView = !isChangeView;
                changeViewLayout();
            }
        });
        //Add button on arry list
        arrayButtonDiscover();

        fillSpanArray();
        //Full screen
        View decorViewFull = getWindow().getDecorView();
        fullScreen(decorViewFull);
        //API
        Intent totalPage = getIntent();
        if (totalPage != null && totalPage.hasExtra(MoviesFragment.TOTAL_PAGE_LATEST)) {
            totalPages = Integer.parseInt(totalPage.getStringExtra(MoviesFragment.TOTAL_PAGE_LATEST));
            numTypeDataAfterFilter = 0;
            numTypeData = 0;
        } else if (totalPage != null && totalPage.hasExtra(MoviesFragment.TOTAL_PAGE_POPULAR)) {
            totalPages = Integer.parseInt(totalPage.getStringExtra(MoviesFragment.TOTAL_PAGE_POPULAR));
            numTypeDataAfterFilter = 1;
            numTypeData = 1;
        } else if (totalPage != null && totalPage.hasExtra(MoviesFragment.TOTAL_PAGE_TOP_RATE)) {
            totalPages = Integer.parseInt(totalPage.getStringExtra(MoviesFragment.TOTAL_PAGE_TOP_RATE));
            numTypeDataAfterFilter = 2;
            numTypeData = 2;
        } else if (totalPage != null && totalPage.hasExtra(MoviesFragment.TOTAL_PAGE_NOW_PLAY)) {
            totalPages = Integer.parseInt(totalPage.getStringExtra(MoviesFragment.TOTAL_PAGE_NOW_PLAY));
            numTypeDataAfterFilter = 3;
            numTypeData = 3;
        } else if (totalPage != null && totalPage.hasExtra(StremTvFragment.TOTAL_PAGE_AIRING_TODAY_TV)) {
            totalPages = Integer.parseInt(totalPage.getStringExtra(StremTvFragment.TOTAL_PAGE_AIRING_TODAY_TV));
            numTypeDataAfterFilter = 4;
            numTypeData = 4;
        } else if (totalPage != null && totalPage.hasExtra(StremTvFragment.TOTAL_PAGE_POPULAR_TV)) {
            totalPages = Integer.parseInt(totalPage.getStringExtra(StremTvFragment.TOTAL_PAGE_POPULAR_TV));
            numTypeDataAfterFilter = 5;
            numTypeData = 5;
        } else if (totalPage != null && totalPage.hasExtra(StremTvFragment.TOTAL_PAGE_TOP_RATE_TV)) {
            totalPages = Integer.parseInt(totalPage.getStringExtra(StremTvFragment.TOTAL_PAGE_TOP_RATE_TV));
            numTypeDataAfterFilter = 6;
            numTypeData = 6;
        } else if (totalPage != null && totalPage.hasExtra(StremTvFragment.TOTAL_PAGE_ON_THE_AIR_TV)) {
            totalPages = Integer.parseInt(totalPage.getStringExtra(StremTvFragment.TOTAL_PAGE_ON_THE_AIR_TV));
            numTypeDataAfterFilter = 7;
            numTypeData = 7;
        } else if (totalPage != null && totalPage.hasExtra(AnimeFragment.TOTAL_PAGE_MOVIES_ANIMATION)) {
            totalPages = Integer.parseInt(totalPage.getStringExtra(AnimeFragment.TOTAL_PAGE_MOVIES_ANIMATION));
            numTypeDataAfterFilter = 8;
            numTypeData = 8;
        } else if (totalPage != null && totalPage.hasExtra(AnimeFragment.TOTAL_PAGE_TV_ANIMATION)) {
            totalPages = Integer.parseInt(totalPage.getStringExtra(AnimeFragment.TOTAL_PAGE_TV_ANIMATION));
            numTypeDataAfterFilter = 9;
            numTypeData = 9;
        } else if (totalPage != null && totalPage.hasExtra(HomeFragment.TOTAL_PAGE_TRENDING)) {
            totalPages = Integer.parseInt(totalPage.getStringExtra(HomeFragment.TOTAL_PAGE_TRENDING));
            numTypeDataAfterFilter = 11;
            numTypeData = 11;
        }
        //Check Orientation to change spanCount on gridLayoutManager
        recyclerView = findViewById(R.id.recyclerView_see_all_movies_id2);

        /*checkOrientation();
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (span.contains(position)) {
                    recycleAdapter.setPositionAd((position % 20) + 1);
                    return spanCount;
                }
                return 1;
            }
        });
        //Initialize data by loadMoreMoviesAtScroll
        loadMoreMoviesAtScroll("1", numTypeData);
        recycleAdapter.setList(moviesResultsArrayList1);
        recycleAdapter.setChangeWidthLayoutMovieItem(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        AdmobNativeAdAdapter admobNativeAdAdapter = AdmobNativeAdAdapter.Builder
                .with(
                        "ca-app-pub-6532316184285626/6171476354",//Create a native ad id from admob console
                        recycleAdapter,//The adapter you would normally set to your recyClerView
                        "medium" //"small","medium"or"custom"
                )
                .adItemIterval(20)//native ad repeating interval in the recyclerview
                .build();
        recyclerView.setAdapter(admobNativeAdAdapter);
        recyclerView.setHasFixedSize(true);*/
        loadMoreMoviesAtScroll("1", numTypeData);
        changeViewLayout();
        getMoreMoviesAtScroll();
        swipeRefresh(numTypeDataAfterFilter);
        searchOnClick();
        showFilterDialog(imageViewFilter);
    }

    private void changeViewLayout() {
        if (isChangeView) {
            changeViewImageView.setImageResource(R.drawable.ic_baseline_blur_on_24);
            linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recycleAdapter.setWatchListFragment(true);
            recycleAdapter.setLayoutId(R.layout.search_item);
            recycleAdapter.setIsHorizontalRecycle(true);
            recycleAdapter.setList(moviesResultsArrayList1);
            recycleAdapter.setPositionAd(0);
            //recycleAdapter.setPositionAd(1);
            /*AdmobNativeAdAdapter admobNativeAdAdapterHor = AdmobNativeAdAdapter.Builder
                    .with(
                            "ca-app-pub-6532316184285626/6171476354",//Create a native ad id from admob console
                            recycleAdapter,//The adapter you would normally set to your recyClerView
                            "medium" //"small","medium"or"custom"
                    )
                    .adItemIterval(20)//native ad repeating interval in the recyclerview
                    .build();
            recyclerView.setAdapter(admobNativeAdAdapterHor);*/
            recyclerView.setAdapter(recycleAdapter);

        } else {
            changeViewImageView.setImageResource(R.drawable.ic_baseline_list_24);
            recycleAdapter.setChangeWidthLayoutMovieItem(true);
            recycleAdapter.setIsHorizontalRecycle(false);
            checkOrientation();
            gridLayoutManager = new GridLayoutManager(this, spanCount);
            //TODO: The next comment code used it to make the ad full width that mean make span = spaneCount But you have a problem with that
            //TODO: When use it the position after add the ad on adapter it is changed so at click an item after the ad positopn on this adpter
            //TODO: on this recycle view will go to other item <will open next item for the item are cliced>
            //I'm fixed this problem by this line code -->recycleAdapter.setPositionAd((position % 20) + 1);<--
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (span.contains(position)) {
                        recycleAdapter.setPositionAd((position % 20) + 1);
                        return spanCount;
                    }
                    return 1;
                }
            });
            recycleAdapter.setList(moviesResultsArrayList1);
            recycleAdapter.setLayoutId(R.layout.movie_item);
            recyclerView.setLayoutManager(gridLayoutManager);
            //Add Ad native on adapter and recycler view
            AdmobNativeAdAdapter admobNativeAdAdapter = AdmobNativeAdAdapter.Builder
                    .with(
                            "ca-app-pub-3940256099942544/2247696110",//Create a native ad id from admob console
                            recycleAdapter,//The adapter you would normally set to your recyClerView
                            "medium" //"small","medium"or"custom"
                    )
                    .adItemIterval(20)//native ad repeating interval in the recyclerview
                    .build();
            recyclerView.setAdapter(admobNativeAdAdapter);
        }
        recycleAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
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

    //Check Orientation to select num raw on recycle
    private void checkOrientation() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 4;
        } else {
            spanCount = 2;
        }
    }

    //Get more movies when scroll
    private void getMoreMoviesAtScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && !search) {
                    if (isChangeView) {
                        currentItems = linearLayoutManager.getChildCount();
                        totalItems = linearLayoutManager.getItemCount();
                        scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();
                    } else {
                        currentItems = gridLayoutManager.getChildCount();
                        totalItems = gridLayoutManager.getItemCount();
                        scrollOutItems = gridLayoutManager.findFirstVisibleItemPosition();
                    }
                    if (isScrolling && ((currentItems + scrollOutItems) >= totalItems) && pageNum <= totalPages) {
                        isScrolling = false;
                        pageNum++;
                        loadMoreMoviesAtScroll(pageNum + "", numTypeData);
                    }
                }
            }
        });
    }

    /**
     * Part TV "Get data"
     */
    //Get latest Trailer
    private void getLatestTrailerMovies(String page) {
        moviesViewModel.getLatestTrailer(page);
        moviesViewModel.latestTrailerMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                for (int i = 0; i < moviesResultsArrayList.size(); i++) {
                    if (!moviesResultsArrayList1.contains(moviesResultsArrayList.get(i)))
                        moviesResultsArrayList1.add(moviesResultsArrayList.get(i));
                }
                recycleAdapter.notifyDataSetChanged();
            }
        });
    }

    //Get popular movies
    private void getPopularMovies(String page) {
        moviesViewModel.getMoviesPopular(page);
        moviesViewModel.moviesPopularMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                for (int i = 0; i < moviesResultsArrayList.size(); i++) {
                    if (!moviesResultsArrayList1.contains(moviesResultsArrayList.get(i)))
                        moviesResultsArrayList1.add(moviesResultsArrayList.get(i));
                }
                recycleAdapter.notifyDataSetChanged();
            }
        });
    }

    //Get top rated movies
    private void getTopRatedMovies(String page) {
        moviesViewModel.getMoviesTopRated(page);
        moviesViewModel.moviesTopRatedMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                for (int i = 0; i < moviesResultsArrayList.size(); i++) {
                    if (!moviesResultsArrayList1.contains(moviesResultsArrayList.get(i)))
                        moviesResultsArrayList1.add(moviesResultsArrayList.get(i));
                }
                recycleAdapter.notifyDataSetChanged();
            }
        });
    }

    //Get now playing movies
    private void getNowPlayingMovies(String page) {
        moviesViewModel.getMoviesNowPlaying(page);
        moviesViewModel.moviesNowPlayingMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                for (int i = 0; i < moviesResultsArrayList.size(); i++) {
                    if (!moviesResultsArrayList1.contains(moviesResultsArrayList.get(i)))
                        moviesResultsArrayList1.add(moviesResultsArrayList.get(i));
                }
                recycleAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Part TV "Get data"
     */

    private void getAiringTodayTV(String page) {
        moviesViewModel.getTvAiringToday(page);
        moviesViewModel.airingTodayTvMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                for (int i = 0; i < moviesResultsArrayList.size(); i++) {
                    if (!moviesResultsArrayList1.contains(moviesResultsArrayList.get(i)))
                        moviesResultsArrayList1.add(moviesResultsArrayList.get(i));
                }
                recycleAdapter.notifyDataSetChanged();
            }
        });
    }

    //Get popular TV
    private void getPopularTV(String page) {
        moviesViewModel.getPopularTv(page);
        moviesViewModel.popularTvMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                for (int i = 0; i < moviesResultsArrayList.size(); i++) {
                    if (!moviesResultsArrayList1.contains(moviesResultsArrayList.get(i)))
                        moviesResultsArrayList1.add(moviesResultsArrayList.get(i));
                }
                recycleAdapter.notifyDataSetChanged();
            }
        });
    }

    //Get top rated TV
    private void getTopRatedTV(String page) {
        moviesViewModel.getTopRatedTv(page);
        moviesViewModel.topRatedTvMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                for (int i = 0; i < moviesResultsArrayList.size(); i++) {
                    if (!moviesResultsArrayList1.contains(moviesResultsArrayList.get(i)))
                        moviesResultsArrayList1.add(moviesResultsArrayList.get(i));
                }
                recycleAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getOnTheAirTV(String page) {
        moviesViewModel.getTvOnTheAir(page);
        moviesViewModel.onTheAirTvMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                for (int i = 0; i < moviesResultsArrayList.size(); i++) {
                    if (!moviesResultsArrayList1.contains(moviesResultsArrayList.get(i)))
                        moviesResultsArrayList1.add(moviesResultsArrayList.get(i));
                }
                recycleAdapter.notifyDataSetChanged();
            }
        });
    }

    //Search
    private void searchOnMoviesOrTV(String textToSearch, int page) {
        moviesViewModel.searchOnMoviesOrTV(textToSearch, page + "");
        moviesViewModel.searchOnMoviesOrTVMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                totalPages = moviesModel.getTotal_pages();
                for (int i = 0; i < moviesResultsArrayList.size(); i++) {
                    if (!moviesResultsArrayList1.contains(moviesResultsArrayList.get(i)))
                        moviesResultsArrayList1.add(moviesResultsArrayList.get(i));
                }
                recycleAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getMoviesOfTypeAnimation(String page) {
        moviesViewModel.getMoviesOfType("vote_count.desc", "16", page);
        moviesViewModel.moviesOfTypeMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                for (int i = 0; i < moviesResultsArrayList.size(); i++) {
                    if (!moviesResultsArrayList1.contains(moviesResultsArrayList.get(i)))
                        moviesResultsArrayList1.add(moviesResultsArrayList.get(i));
                }
                recycleAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getTVOfTypeAnimation(String page) {
        moviesViewModel.getTVOfType("vote_count.desc", "16", page);
        moviesViewModel.tVOfTypeMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                for (int i = 0; i < moviesResultsArrayList.size(); i++) {
                    if (!moviesResultsArrayList1.contains(moviesResultsArrayList.get(i)))
                        moviesResultsArrayList1.add(moviesResultsArrayList.get(i));
                }
                recycleAdapter.notifyDataSetChanged();
            }
        });
    }

    //Get more latest Trailer at scroll
    private void loadMoreMoviesAtScroll(String page, int numTypeData) {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (numTypeData) {
                    case 0:
                        getLatestTrailerMovies(page);
                        break;
                    case 1:
                        getPopularMovies(page);
                        break;
                    case 2:
                        getTopRatedMovies(page);
                        break;
                    case 3:
                        getNowPlayingMovies(page);
                        break;
                    case 4:
                        getAiringTodayTV(page);
                        break;
                    case 5:
                        getPopularTV(page);
                        break;
                    case 8:
                        getMoviesOfTypeAnimation(page);
                        break;
                    case 9:
                        getTVOfTypeAnimation(page);
                        break;
                    case 10:
                        buttonIsOnClick = false;
                        discoverMediaWithGeners(page, gneres, sortBy, cuntry, releaseDate, rated, mediaType, "");
                        break;
                    case 11:
                        getTrendingMovie(page);
                        break;
                }
                progressBar.setVisibility(View.GONE);
            }
        }, 1000);
    }

    //Get trending
    private void getTrendingMovie(String page) {
        moviesViewModel.getTrendingAllToDay(page);
        moviesViewModel.getTrendingMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
                for (int i = 0; i < moviesResultsArrayList.size(); i++) {
                    if (!moviesResultsArrayList1.contains(moviesResultsArrayList.get(i)))
                        moviesResultsArrayList1.add(moviesResultsArrayList.get(i));
                }
                recycleAdapter.notifyDataSetChanged();
            }
        });
    }

    //Swipe Refresh
    @SuppressLint("ResourceAsColor")
    private void swipeRefresh(int numTypeData) {
        swipeRefreshLayout = findViewById(R.id.swipe_see_all_id);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.yellow);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkOrientation();
                if (moviesResultsArrayList1.isEmpty()) {
                    recycleAdapter.notifyDataSetChanged();
                    switch (numTypeData) {
                        case 0:
                            getLatestTrailerMovies("1");
                            break;
                        case 1:
                            getPopularMovies("1");
                            break;
                        case 2:
                            getTopRatedMovies("1");
                            break;
                        case 3:
                            getNowPlayingMovies("1");
                            break;
                        case 4:
                            getAiringTodayTV("1");
                            break;
                        case 5:
                            getPopularTV("1");
                            break;
                        case 6:
                            getTopRatedTV("1");
                            break;
                        case 7:
                            getOnTheAirTV("1");
                            break;
                        case 8:
                            getMoviesOfTypeAnimation("1");
                            break;
                        case 9:
                            getTVOfTypeAnimation("1");
                            break;
                        case 10:
                            discoverMediaWithGeners("1", gneres, sortBy, cuntry, releaseDate, rated, mediaType, "");
                            break;
                        case 11:
                            getTrendingMovie("1");
                            break;
                    }
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);
            }
        });
    }


    private void searchOnClick() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.isEmpty()) {
                    search = false;
                    moviesResultsArrayList1.clear();
                    getDataAtTypeIs(numTypeData);
                } else {
                    search = true;
                    moviesResultsArrayList1.clear();
                    searchOnMoviesOrTV(query, 1);
                    recycleAdapter.getFilter().filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    search = false;
                    moviesResultsArrayList1.clear();
                    getDataAtTypeIs(numTypeData);
                } else {
                    search = true;
                    moviesResultsArrayList1.clear();
                    searchOnMoviesOrTV(newText, 1);
                    recycleAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                search = false;
                moviesResultsArrayList1.clear();
                getDataAtTypeIs(numTypeData);
                return false;
            }
        });
    }


    private void getDataAtTypeIs(int numTypeData) {
        switch (numTypeData) {
            case 0:
                getLatestTrailerMovies("1");
                break;
            case 1:
                getPopularMovies("1");
                break;
            case 2:
                getTopRatedMovies("1");
                break;
            case 3:
                getNowPlayingMovies("1");
                break;
            case 4:
                getAiringTodayTV("1");
                break;
            case 5:
                getPopularTV("1");
                break;
            case 6:
                getTopRatedTV("1");
                break;
            case 7:
                getOnTheAirTV("1");
                break;
            case 8:
                getMoviesOfTypeAnimation("1");
                break;
            case 9:
                getTVOfTypeAnimation("1");
                break;
            case 10:
                discoverMediaWithGeners("1", gneres, sortBy, cuntry, releaseDate, rated, mediaType, "");
                break;
            case 11:
                getTrendingMovie("1");
                break;
        }
    }

    /**
     * Filter part
     **/
    public void onClickButtonTypeMedia(View view) {
        buttonIsOnClick = true;
        pageNum = 1;
        numTypeData = 10;
        recycleAdapter.notifyDataSetChanged();
        int id = view.getId();
        int text;
        switch (id) {
            case R.id.radio_id0:
                gneres = "";
                text = R.string.all;
                break;
            case R.id.radio_id1:
                gneres = "28";
                text = R.string.action;
                break;
            case R.id.radio_id2:
                gneres = "12";
                text = R.string.adventure;
                break;
            case R.id.radio_id3:
                gneres = "16";
                text = R.string.animation;
                break;
            case R.id.radio_id4:
                text = R.string.comedy;
                gneres = "35";
                break;
            case R.id.radio_id5:
                text = R.string.crime;
                gneres = "80";
                break;
            case R.id.radio_id6:
                text = R.string.documentary;
                gneres = "99";
                break;
            case R.id.radio_id7:
                text = R.string.drama;
                gneres = "18";
                break;
            case R.id.radio_id8:
                text = R.string.family;
                gneres = "10751";
                break;
            case R.id.radio_id9:
                text = R.string.fantasy;
                gneres = "14";
                break;
            case R.id.radio_id10:
                text = R.string.history;
                gneres = "36";
                break;
            case R.id.radio_id11:
                text = R.string.horror;
                gneres = "27";
                break;
            case R.id.radio_id12:
                text = R.string.music;
                gneres = "10402";
                break;
            case R.id.radio_id13:
                text = R.string.mystery;
                gneres = "9648";
                break;
            case R.id.radio_id14:
                text = R.string.romance;
                gneres = "10749";
                break;
            case R.id.radio_id15:
                text = R.string.science_fiction;
                gneres = "878";
                break;
            case R.id.radio_id16:
                text = R.string.tv_movie;
                gneres = "10770";
                break;
            case R.id.radio_id17:
                text = R.string.thriller;
                gneres = "53";
                break;
            case R.id.radio_id18:
                text = R.string.war;
                gneres = "10752";
                break;
            case R.id.radio_id19:
                text = R.string.western;
                gneres = "37";
                break;
            case R.id.radio_id20:
                text = R.string.clear_filter;
                gneres = "";
                sortBy = "popularity.desc";
                cuntry = "";
                releaseDate = "";
                rated = "";
                pageNum = 1;
                moviesResultsArrayList1.clear();
                recycleAdapter.notifyDataSetChanged();
                //loadMoreMoviesAtScroll(pageNum + "", numTypeDataAfterFilter);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
        //Change color button when click
        Button button;
        for (int i = 0; i < buttonArrayList.size(); i++) {
            button = findViewById(buttonArrayList.get(i));
            if (buttonArrayList.get(i) == id) {
                button.setBackgroundResource(R.drawable.full_corner_background_black);
                button.setTextColor(getResources().getColor(R.color.yellow));
            } else {
                button.setBackgroundResource(R.drawable.full_corner_background_yellow);
                button.setTextColor(getResources().getColor(R.color.black2));
            }
        }
        if (id != R.id.radio_id20)
            discoverMediaWithGeners(pageNum + "", gneres, sortBy, cuntry, releaseDate, rated, mediaType, "");
        else
            loadMoreMoviesAtScroll(pageNum + "", numTypeDataAfterFilter);
        Toast.makeText(SeeAllMovies.this, getString(text), Toast.LENGTH_SHORT).show();
    }

    // Get all movie
    private void discoverMediaWithGeners(String page, String gneres, String sort_by, String with_original_language,
                                         String year, String vote_average_gte, String mediaType, String include_video) {

        if (mediaType.equals("movie")) {
            moviesViewModel.discoverMovieWithGeners(page, gneres, sort_by, with_original_language, year, vote_average_gte, "");
            moviesViewModel.discoverMovieWithGenresMutableLiveData.observe(this, new Observer<MoviesModel>() {
                @Override
                public void onChanged(MoviesModel moviesModel) {
                    try {
                        getDataAndFill(moviesModel);
                    } catch (Exception e) {
                    }
                }
            });
        } else if (mediaType.equals("tv")) {
            moviesViewModel.discoverTVWithGeners(page, gneres, sort_by, with_original_language, year, vote_average_gte);
            moviesViewModel.discoverTVWithGenresMutableLiveData.observe(this, new Observer<MoviesModel>() {
                @Override
                public void onChanged(MoviesModel moviesModel) {
                    try {
                        getDataAndFill(moviesModel);
                    } catch (Exception e) {
                    }
                }
            });
        }

    }

    //Get data and fill dicover array list
    private void getDataAndFill(MoviesModel moviesModel) {
        totalPages = moviesModel.getTotal_pages();
        if (buttonIsOnClick && count == 0) {
            count = count + 1;
            moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
            for (int i = 0; i < moviesResultsArrayList.size(); i++) {
                if (!moviesResultsArrayList1.contains(moviesResultsArrayList.get(i)))
                    moviesResultsArrayList1.add(moviesResultsArrayList.get(i));
            }
        } else if (buttonIsOnClick && count >= 1) {
            count = 0;
            moviesResultsArrayList1.clear();
            moviesResultsArrayList.clear();
            moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
            for (int i = 0; i < moviesResultsArrayList.size(); i++) {
                if (!moviesResultsArrayList1.contains(moviesResultsArrayList.get(i)))
                    moviesResultsArrayList1.add(moviesResultsArrayList.get(i));
            }
        } else {
            moviesResultsArrayList = new ArrayList<>(moviesModel.getResults());
            for (int i = 0; i < moviesResultsArrayList.size(); i++) {
                if (!moviesResultsArrayList1.contains(moviesResultsArrayList.get(i)))
                    moviesResultsArrayList1.add(moviesResultsArrayList.get(i));
            }
        }
        recycleAdapter.notifyDataSetChanged();
    }

    //Show filter dialog when click on image filter
    public void showFilterDialog(ImageView imageView) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                gneres = "";
                sortBy = "";
                cuntry = "";
                releaseDate = "";
                rated = "";
                //configuration Languages from API to fill cuntry spinner
                //TODO:If you want all cuntrtys and languages using -->configurationLanguages();<-- method but I need some cuntry so I will use cuntryArray
                //Languages
                languages.add("");
                languages.add("ar");
                languages.add("zh");
                languages.add("en");
                languages.add("fr");
                languages.add("de");
                languages.add("hi");
                languages.add("it");
                languages.add("ja");
                languages.add("ko");
                languages.add("ru");
                languages.add("es");
                languages.add("tr");

                //Deffin dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(SeeAllMovies.this);
                View filterDialogView = LayoutInflater.from(SeeAllMovies.this).inflate(R.layout.filter_dialog, null);
                builder.setView(filterDialogView);
                final AlertDialog alertDialog = builder.create();
                //Action seekBar rated filter in dialog
                seekBarRated = filterDialogView.findViewById(R.id.seekBar_rated_id);
                seekBarRated.setProgress(0);
                textSeekBarRadted = filterDialogView.findViewById(R.id.text_view_seek_bar_rated_id);
                //TODO:1 The seekBarPopularty not avilabel yet will be do it soon
                seekBarDateRealse = filterDialogView.findViewById(R.id.seekBar_reales_date_id);
                textSeekBarRealseDate = filterDialogView.findViewById(R.id.text_view_seek_bar_reales_date_id);
                seekBarDateRealse.setProgress(122);
                seekBarRated.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        rated = "" + progress + "";
                        textSeekBarRadted.setText(rated);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                seekBarDateRealse.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        releaseDate = "" + (progress + 1900) + "";
                        textSeekBarRealseDate.setText(releaseDate);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                //Action spinner cuntry in dialog
                Spinner spinnerCuntry = filterDialogView.findViewById(R.id.spinner_cuntry_id);
                ArrayAdapter<CharSequence> adapterSpinnerCuntry = ArrayAdapter.createFromResource(SeeAllMovies.this, R.array.country, R.layout.spinner_item_filter_dialog);
                adapterSpinnerCuntry.setDropDownViewResource(R.layout.spinner_item_dropdown_filter_dialog);
                spinnerCuntry.setAdapter(adapterSpinnerCuntry);
                spinnerCuntry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position > 0) {
                            cuntry = languages.get(position);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                //Action spinner sort By in dialog
                Spinner spinnerSortBy = filterDialogView.findViewById(R.id.spinner_sort_by_id);
                ArrayAdapter<CharSequence> adapterSpinnerSortBy = ArrayAdapter.createFromResource(SeeAllMovies.this, R.array.sortBy, R.layout.spinner_item_filter_dialog);
                adapterSpinnerSortBy.setDropDownViewResource(R.layout.spinner_item_dropdown_filter_dialog);
                spinnerSortBy.setAdapter(adapterSpinnerSortBy);
                spinnerSortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                sortBy = "";
                                break;
                            case 1:
                                sortBy = "popularity.desc";
                                break;
                            case 2:
                                sortBy = "popularity.asc";
                                break;
                            case 3:
                                sortBy = "release_date.desc";
                                break;
                            case 4:
                                sortBy = "release_date.asc";
                                break;
                            case 5:
                                sortBy = "vote_average.desc";
                                break;
                            case 6:
                                sortBy = "vote_average.asc";
                                break;
                            case 7:
                                sortBy = "vout_count.desc";
                                break;
                            case 8:
                                sortBy = "vout_count.asc";
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                //Action button filter in dialog
                filterDialogView.findViewById(R.id.button_filter_id).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        buttonIsOnClick = true;
                        pageNum = 1;
                        recycleAdapter.notifyDataSetChanged();
                        //
                        numTypeData = 10;
                        discoverMediaWithGeners(pageNum + "", gneres, sortBy, cuntry, releaseDate, rated, mediaType, "");
                        Toast.makeText(SeeAllMovies.this, R.string.filter, Toast.LENGTH_SHORT).show();
                    }
                });
                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                alertDialog.show();
            }
        });
    }

    /**
     * 4-Interstitial Ad
     **/
    private void interstitialAd() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void fillSpanArray() {
        span.add(20);
        span.add(41);
        span.add(62);
        span.add(83);
        span.add(104);
        span.add(125);
        span.add(146);
        span.add(167);
        span.add(188);
        span.add(209);
        span.add(230);
        span.add(251);
        span.add(272);
        span.add(293);
        span.add(314);
        span.add(335);
        span.add(356);
        span.add(377);
        span.add(398);
        span.add(419);
        span.add(440);
        span.add(461);
        span.add(482);
        span.add(503);
        span.add(524);
        span.add(545);
        span.add(566);
        span.add(587);
        span.add(608);
        span.add(629);
        span.add(650);
        span.add(671);
        span.add(692);
        span.add(713);
        span.add(734);
        span.add(755);
        span.add(776);
        span.add(797);
        span.add(818);
        span.add(839);
        span.add(860);
        span.add(881);
        span.add(902);
        span.add(923);
        span.add(944);
        span.add(965);
        span.add(986);
        span.add(1007);
        span.add(1028);
        span.add(1049);
        span.add(1070);
        span.add(1091);
        span.add(1112);
        span.add(1133);
        span.add(1154);
        span.add(1175);
        span.add(1196);
        span.add(1217);
        span.add(1238);
        span.add(1259);
        span.add(1280);
        span.add(1301);
        span.add(1322);
        span.add(1343);
        span.add(1364);
        span.add(1385);
        span.add(1406);
        span.add(1427);
        span.add(1448);
        span.add(1469);
        span.add(1490);
        span.add(1511);
        span.add(1532);
    }

    private void arrayButtonDiscover() {
        buttonArrayList.add(R.id.radio_id0);
        buttonArrayList.add(R.id.radio_id1);
        buttonArrayList.add(R.id.radio_id2);
        buttonArrayList.add(R.id.radio_id3);
        buttonArrayList.add(R.id.radio_id4);
        buttonArrayList.add(R.id.radio_id5);
        buttonArrayList.add(R.id.radio_id6);
        buttonArrayList.add(R.id.radio_id7);
        buttonArrayList.add(R.id.radio_id8);
        buttonArrayList.add(R.id.radio_id9);
        buttonArrayList.add(R.id.radio_id10);
        buttonArrayList.add(R.id.radio_id11);
        buttonArrayList.add(R.id.radio_id12);
        buttonArrayList.add(R.id.radio_id13);
        buttonArrayList.add(R.id.radio_id14);
        buttonArrayList.add(R.id.radio_id15);
        buttonArrayList.add(R.id.radio_id16);
        buttonArrayList.add(R.id.radio_id17);
        buttonArrayList.add(R.id.radio_id18);
        buttonArrayList.add(R.id.radio_id19);
        buttonArrayList.add(R.id.radio_id20);
    }
}


