package com.moomen.movieyou.ui.fragment;

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
import android.view.ViewGroup;
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
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.efaso.admob_advanced_native_recyvlerview.AdmobNativeAdAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.moomen.movieyou.R;
import com.moomen.movieyou.adapter.MoviesRecycleAdapter;
import com.moomen.movieyou.model.configuration.ConfigurationResults;
import com.moomen.movieyou.model.movies.MoviesModel;
import com.moomen.movieyou.model.movies.MoviesResults;
import com.moomen.movieyou.ui.SeeAllMovies;
import com.moomen.movieyou.viewModel.MoviesViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    public static final String TOTAL_PAGE_TRENDING = "TOTAL_PAGE_TRENDING";
    private static int pageNum = 1;
    private View view;
    private MoviesViewModel moviesViewModel;
    //Trending
    private MoviesRecycleAdapter trendingMoviesRecycleAdapter;
    private ArrayList<MoviesResults> trendingMoviesArrayList = new ArrayList<>();
    private Button seeAllTrendingButton;
    private String totalPageTrending = "1";
    //Discover
    private MoviesRecycleAdapter discoverMediaRecycleAdapter;
    private ArrayList<MoviesResults> mediaArrayList = new ArrayList<>();
    private ArrayList<MoviesResults> discoverMediaArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int spanCount;
    //Discover media with gneres
    //Parameters to filter
    private String gneres = "";
    private String cuntry = "";
    private String sortBy = "popularity.desc";
    private String rated = "0";
    private String releaseDate = "122";
    //Components
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchMedia;
    private ImageView imageViewFilter;
    private String mediaType = "movie";
    private ImageView changeViewImageView;
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button10;
    private Button button11;
    private Button button12;
    private Button button13;
    private Button button14;
    private Button button15;
    private Button button16;
    private Button button17;
    private Button button18;
    private Button button19;
    private Button button20;
    private ArrayList<Button> buttonArrayList = new ArrayList<>();
    //For dialog filter
    private SeekBar seekBarRated;
    private SeekBar seekBarDateRealse;
    private TextView textSeekBarRadted;
    private TextView textSeekBarRealseDate;
    private Spinner spinnerSortBy;
    private Spinner spinnerCuntry;
    private int positionSellectedSpinnerCuntry = 0;
    private int positionSellectedSpinnerSortBy = 0;
    private AlertDialog alertDialog;
    //For configoration language
    private ArrayList<ConfigurationResults> configurationResultsArrayList;
    private ArrayList<String> languages = new ArrayList<>();
    private ArrayList<String> cuntryArray = new ArrayList<>();
    //To get more movies when scrolling
    private Boolean isScrolling = false;
    private int totalPages;
    private int currentItems, totalItems, scrollOutItems;
    private GridLayoutManager gridLayoutManager;
    private ProgressBar progressBar;
    private boolean search = false;
    private SearchView searchView;
    private boolean buttonIsOnClick = false;
    private int count = 0;
    private LinearLayoutManager linearLayoutManager;
    //Ad
    private RewardedAd rewardedAd;
    private ArrayList<Integer> span = new ArrayList<>();

    private boolean isChangeView = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        progressBar = view.findViewById(R.id.progressBar_home_fragment_id);
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_home_id);
        imageViewFilter = view.findViewById(R.id.imageView_filter_id);
        switchMedia = view.findViewById(R.id.switch1);
        searchView = view.findViewById(R.id.search_view_home_fragment_id);
        seeAllTrendingButton = view.findViewById(R.id.see_all_trending_home_button_id);
        //Image change view
        changeViewImageView = view.findViewById(R.id.change_view_id);
        changeViewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChangeView = !isChangeView;
                changeViewLayout();
            }
        });
        //Button Filter
        button0 = view.findViewById(R.id.radio_id0);
        button1 = view.findViewById(R.id.radio_id1);
        button2 = view.findViewById(R.id.radio_id2);
        button3 = view.findViewById(R.id.radio_id3);
        button4 = view.findViewById(R.id.radio_id4);
        button5 = view.findViewById(R.id.radio_id5);
        button6 = view.findViewById(R.id.radio_id6);
        button7 = view.findViewById(R.id.radio_id7);
        button8 = view.findViewById(R.id.radio_id8);
        button9 = view.findViewById(R.id.radio_id9);
        button10 = view.findViewById(R.id.radio_id10);
        button11 = view.findViewById(R.id.radio_id11);
        button12 = view.findViewById(R.id.radio_id12);
        button13 = view.findViewById(R.id.radio_id13);
        button14 = view.findViewById(R.id.radio_id14);
        button15 = view.findViewById(R.id.radio_id15);
        button16 = view.findViewById(R.id.radio_id16);
        button17 = view.findViewById(R.id.radio_id17);
        button18 = view.findViewById(R.id.radio_id18);
        button19 = view.findViewById(R.id.radio_id19);
        button20 = view.findViewById(R.id.radio_id20);


        onClickButtonGenres(button0);
        onClickButtonGenres(button1);
        onClickButtonGenres(button2);
        onClickButtonGenres(button3);
        onClickButtonGenres(button4);
        onClickButtonGenres(button5);
        onClickButtonGenres(button6);
        onClickButtonGenres(button7);
        onClickButtonGenres(button8);
        onClickButtonGenres(button9);
        onClickButtonGenres(button10);
        onClickButtonGenres(button11);
        onClickButtonGenres(button12);
        onClickButtonGenres(button13);
        onClickButtonGenres(button14);
        onClickButtonGenres(button15);
        onClickButtonGenres(button16);
        onClickButtonGenres(button17);
        onClickButtonGenres(button18);
        onClickButtonGenres(button19);
        onClickButtonGenres(button20);

        //Add on arry list
        buttonArrayList.add(button0);
        buttonArrayList.add(button1);
        buttonArrayList.add(button2);
        buttonArrayList.add(button3);
        buttonArrayList.add(button4);
        buttonArrayList.add(button5);
        buttonArrayList.add(button6);
        buttonArrayList.add(button7);
        buttonArrayList.add(button8);
        buttonArrayList.add(button9);
        buttonArrayList.add(button10);
        buttonArrayList.add(button11);
        buttonArrayList.add(button12);
        buttonArrayList.add(button13);
        buttonArrayList.add(button14);
        buttonArrayList.add(button15);
        buttonArrayList.add(button16);
        buttonArrayList.add(button17);
        buttonArrayList.add(button18);
        buttonArrayList.add(button19);
        buttonArrayList.add(button20);
        /*TextView textView = view.findViewById(R.id.textViewTrending);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeAd();
            }
        });*/
        //
        fillSpanArray();

        recyclerView = view.findViewById(R.id.recycler_view_all_movies_id);
        discoverMediaRecycleAdapter = new MoviesRecycleAdapter(getContext());
        MobileAds.initialize(getContext());
        checkOrientation();
        changeViewLayout();
        loadMoreMediaAtScroll(pageNum + "");
        checkSwitch(switchMedia);
        getTrendingMovie();
        getMoreMoviesAtScroll();
        swipeRefresh();
        searchOnClick();
        setSeeAllTrendingOnClick();
        showFilterDialog(imageViewFilter, savedInstanceState);

        /*//Save instance values
        if (savedInstanceState != null && alertDialog.getWindow() != null) {
            releaseDate = savedInstanceState.getString("releaseDate");
            rated = savedInstanceState.getString("rated");
            positionSellectedSpinnerCuntry = savedInstanceState.getInt("cuntry");
            spinnerCuntry.setSelection(positionSellectedSpinnerCuntry);
            positionSellectedSpinnerSortBy = savedInstanceState.getInt("sortBy");
            spinnerSortBy.setSelection(positionSellectedSpinnerSortBy);
        }*/
        return view;
    }

    private void changeViewLayout() {
        if (isChangeView) {
            changeViewImageView.setImageResource(R.drawable.ic_baseline_blur_on_24);
            pageNum = 1;
            linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            discoverMediaRecycleAdapter.setWatchListFragment(true);
            discoverMediaRecycleAdapter.setList(discoverMediaArrayList);
            discoverMediaRecycleAdapter.setLayoutId(R.layout.search_item);
            recyclerView.setLayoutManager(linearLayoutManager);
            discoverMediaRecycleAdapter.setIsHorizontalRecycle(true);
            discoverMediaRecycleAdapter.setPositionAd(0);
            /*discoverMediaRecycleAdapter.setPositionAd(1);
            AdmobNativeAdAdapter admobNativeAdAdapterHor = AdmobNativeAdAdapter.Builder
                    .with(
                            "ca-app-pub-6532316184285626/6171476354",//Create a native ad id from admob console
                            discoverMediaRecycleAdapter,//The adapter you would normally set to your recyClerView
                            "medium" //"small","medium"or"custom"
                    )
                    .adItemIterval(20)//native ad repeating interval in the recyclerview
                    .build();
            recyclerView.setAdapter(admobNativeAdAdapterHor);*/
            recyclerView.setAdapter(discoverMediaRecycleAdapter);
        } else {
            changeViewImageView.setImageResource(R.drawable.ic_baseline_list_24);
            pageNum = 1;
            discoverMediaRecycleAdapter.setChangeWidthLayoutMovieItem(true);
            discoverMediaRecycleAdapter.setIsHorizontalRecycle(false);
            gridLayoutManager = new GridLayoutManager(getContext(), spanCount);
            //TODO: The next comment code used it to make the ad full width that mean make span = spaneCount But you have a problem with that
            //TODO: When use it the position after add the ad on adapter it is changed so at click an item after the ad positopn on this adpter
            //TODO: on this recycle view will go to other item <will open next item for the item are cliced>
            //I'm fixed this problem by this line code -->discoverMediaRecycleAdapter.setPositionAd((position % 20) + 1);<--
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (span.contains(position)) {
                        discoverMediaRecycleAdapter.setPositionAd((position % 20) + 1);
                        return spanCount;
                    }
                    return 1;
                }
            });
            discoverMediaRecycleAdapter.setList(discoverMediaArrayList);
            discoverMediaRecycleAdapter.setLayoutId(R.layout.movie_item);
            recyclerView.setLayoutManager(gridLayoutManager);
            //Add Ad native on adapter and recycler view
            AdmobNativeAdAdapter admobNativeAdAdapter = AdmobNativeAdAdapter.Builder
                    .with(
                            "ca-app-pub-3940256099942544/2247696110",//Create a native ad id from admob console
                            discoverMediaRecycleAdapter,//The adapter you would normally set to your recyClerView
                            "medium" //"small","medium"or"custom"
                    )
                    .adItemIterval(20)//native ad repeating interval in the recyclerview
                    .build();
            recyclerView.setAdapter(admobNativeAdAdapter);
        }
        discoverMediaRecycleAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
    }

    //See all rending onClick
    private void setSeeAllTrendingOnClick() {
        RewardedAd rewardedAd = new RewardedAd(getContext(), "ca-app-pub-3940256099942544/5224354917");
        rewardedAd.loadAd(new AdRequest.Builder().build(), new RewardedAdLoadCallback());
        seeAllTrendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            Intent intent = new Intent(getContext(), SeeAllMovies.class);
                            intent.putExtra(TOTAL_PAGE_TRENDING, totalPageTrending);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                } else {
                    Intent intent = new Intent(getContext(), SeeAllMovies.class);
                    intent.putExtra(TOTAL_PAGE_TRENDING, totalPageTrending);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    //Show filter dialog when click on image filter
    public void showFilterDialog(ImageView imageView, Bundle savedInstanceState) {
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
                //TODO:If you want all cuntrtys and languages using -->configurationLanguages();<-- method, but I need some cuntry so I will use languages array
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View filterDialogView = LayoutInflater.from(getContext()).inflate(R.layout.filter_dialog, null);
                builder.setView(filterDialogView);
                alertDialog = builder.create();
                //Action seekBar rated filter in dialog
                seekBarRated = filterDialogView.findViewById(R.id.seekBar_rated_id);
                //seekBarRated.setProgress(Integer.parseInt(rated));
                textSeekBarRadted = filterDialogView.findViewById(R.id.text_view_seek_bar_rated_id);
                //TODO:1 The seekBarPopularty not avilabel yet will be do it soon
                seekBarDateRealse = filterDialogView.findViewById(R.id.seekBar_reales_date_id);
                textSeekBarRealseDate = filterDialogView.findViewById(R.id.text_view_seek_bar_reales_date_id);
                //seekBarDateRealse.setProgress(Integer.parseInt(releaseDate));
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
                spinnerCuntry = filterDialogView.findViewById(R.id.spinner_cuntry_id);
                ArrayAdapter<CharSequence> adapterSpinnerCuntry = ArrayAdapter.createFromResource(getContext(), R.array.country, R.layout.spinner_item_filter_dialog);
                adapterSpinnerCuntry.setDropDownViewResource(R.layout.spinner_item_dropdown_filter_dialog);
                spinnerCuntry.setAdapter(adapterSpinnerCuntry);
                //spinnerCuntry.setSelection(positionSellectedSpinnerCuntry);
                spinnerCuntry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position > 0) {
                            cuntry = languages.get(position);
                            positionSellectedSpinnerCuntry = position;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                //Action spinner sort By in dialog
                spinnerSortBy = filterDialogView.findViewById(R.id.spinner_sort_by_id);
                ArrayAdapter<CharSequence> adapterSpinnerSortBy = ArrayAdapter.createFromResource(getContext(), R.array.sortBy, R.layout.spinner_item_filter_dialog);
                adapterSpinnerSortBy.setDropDownViewResource(R.layout.spinner_item_dropdown_filter_dialog);
                spinnerSortBy.setAdapter(adapterSpinnerSortBy);
                //spinnerCuntry.setSelection(positionSellectedSpinnerSortBy);
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
                        positionSellectedSpinnerSortBy = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                /*//Save instance values
                if (savedInstanceState != null) {
                    releaseDate = savedInstanceState.getString("releaseDate");
                    rated = savedInstanceState.getString("rated");
                    positionSellectedSpinnerCuntry = savedInstanceState.getInt("cuntry");
                    spinnerCuntry.setSelection(positionSellectedSpinnerCuntry);
                    positionSellectedSpinnerSortBy = savedInstanceState.getInt("sortBy");
                    spinnerSortBy.setSelection(positionSellectedSpinnerSortBy);
                }
*/
                //Action button filter in dialog
                filterDialogView.findViewById(R.id.button_filter_id).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        buttonIsOnClick = true;
                        pageNum = 1;
                        discoverMediaRecycleAdapter.notifyDataSetChanged();
                        //
                        discoverMediaWithGeners(pageNum + "", gneres, sortBy, cuntry, releaseDate, rated, mediaType, "");
                        Toast.makeText(getContext(), R.string.filter, Toast.LENGTH_SHORT).show();
                    }
                });
                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                alertDialog.show();
            }
        });
    }

    //TODO:1
    private void configurationLanguages() {
        //languages.add("Country");
        moviesViewModel.configurationLanguages();
        moviesViewModel.configurationLanguagesMutableLiveData.observe(HomeFragment.this, new Observer<ArrayList<ConfigurationResults>>() {
            @Override
            public void onChanged(ArrayList<ConfigurationResults> configurationResults) {
                Log.d("testConf", "onChanged: " + configurationResults.size());
                configurationResultsArrayList = new ArrayList<>(configurationResults);
                String name = "";
                for (int i = 0; i < configurationResultsArrayList.size(); i++) {
                    name = configurationResultsArrayList.get(i).getEnglish_name();
                    if (!languages.contains(name) && !name.equals("No Language"))
                        languages.add(name);
                }
            }
        });
    }

    //Check media if movie or tv by switch
    private void checkSwitch(@SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchMedia) {
        /**4-Interstitial Ad**/
        InterstitialAd mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        /*mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });*/
        switchMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show ad
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                //switch to movie or tv
                if (switchMedia.isChecked()) {
                    mediaType = "tv";
                } else {
                    mediaType = "movie";
                }

                discoverMediaArrayList.clear();
                discoverMediaRecycleAdapter.notifyDataSetChanged();
                loadMoreMediaAtScroll(pageNum + "");
            }
        });
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

    //Get trending
    @SuppressLint("FragmentLiveDataObserve")
    private void getTrendingMovie() {
        moviesViewModel.getTrendingAllToDay("1");
        trendingMoviesRecycleAdapter = new MoviesRecycleAdapter(getContext());
        moviesViewModel.getTrendingMutableLiveData.observe(HomeFragment.this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                try {
                    totalPageTrending = String.valueOf(moviesModel.getTotal_pages());
                    trendingMoviesArrayList = new ArrayList<>(moviesModel.getResults());
                    trendingMoviesRecycleAdapter.setList(trendingMoviesArrayList);

                } catch (Exception e) {
                }
            }
        });
        RecyclerView trindingRecyclerView = view.findViewById(R.id.recycler_view_trending_id);
        fillMoviesInHorizontalRecycle(trindingRecyclerView, trendingMoviesRecycleAdapter);
    }

    //Get all movie
    private void discoverMediaWithGeners(String page, String gneres, String sort_by, String with_original_language, String year, String vote_average_gte, String mediaType, String include_video) {

        if (mediaType.equals("movie")) {
            moviesViewModel.discoverMovieWithGeners(page, gneres, sort_by, with_original_language, year, vote_average_gte, "");
            moviesViewModel.discoverMovieWithGenresMutableLiveData.observe(HomeFragment.this, new Observer<MoviesModel>() {
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
            moviesViewModel.discoverTVWithGenresMutableLiveData.observe(HomeFragment.this, new Observer<MoviesModel>() {
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

    //Get data from api and fill on discover array list
    private void getDataAndFill(MoviesModel moviesModel) {
        totalPages = moviesModel.getTotal_pages();
        if (buttonIsOnClick && count == 0) {
            count = count + 1;
            mediaArrayList = new ArrayList<>(moviesModel.getResults());
            for (int i = 0; i < mediaArrayList.size(); i++) {
                if (!discoverMediaArrayList.contains(mediaArrayList.get(i)))
                    discoverMediaArrayList.add(mediaArrayList.get(i));
            }
        } else if (buttonIsOnClick && count >= 1) {
            count = 0;
            discoverMediaArrayList.clear();
            mediaArrayList.clear();
            mediaArrayList = new ArrayList<>(moviesModel.getResults());
            for (int i = 0; i < mediaArrayList.size(); i++) {
                if (!discoverMediaArrayList.contains(mediaArrayList.get(i)))
                    discoverMediaArrayList.add(mediaArrayList.get(i));
            }
        } else {
            mediaArrayList = new ArrayList<>(moviesModel.getResults());
            for (int i = 0; i < mediaArrayList.size(); i++) {
                if (!discoverMediaArrayList.contains(mediaArrayList.get(i)))
                    discoverMediaArrayList.add(mediaArrayList.get(i));
            }
        }
        discoverMediaRecycleAdapter.notifyDataSetChanged();
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
        recyclerView.setHasFixedSize(true);
    }


    //On click an button type media to filter
    private void onClickButtonGenres(Button button) {
        /**4-Interstitial Ad**/
        InterstitialAd mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View v) {
                buttonIsOnClick = true;
                pageNum = 1;
                discoverMediaRecycleAdapter.notifyDataSetChanged();
                int id = button.getId();
                String gneresName = (String) button.getText();
                switch (id) {
                    case R.id.radio_id0:
                        gneres = "";
                        break;
                    case R.id.radio_id1:
                        gneres = "28";
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }
                        break;
                    case R.id.radio_id2:
                        gneres = "12";
                        break;
                    case R.id.radio_id3:
                        gneres = "16";
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }
                        break;
                    case R.id.radio_id4:
                        gneres = "35";
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }
                        break;
                    case R.id.radio_id5:
                        gneres = "80";
                        break;
                    case R.id.radio_id6:
                        gneres = "99";
                        //displayAd(rewardedAd3);
                        break;
                    case R.id.radio_id7:
                        gneres = "18";
                        break;
                    case R.id.radio_id8:
                        gneres = "10751";
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }
                        break;
                    case R.id.radio_id9:
                        gneres = "14";
                        break;
                    case R.id.radio_id10:
                        gneres = "36";
                        break;
                    case R.id.radio_id11:
                        gneres = "27";
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }
                        break;
                    case R.id.radio_id12:
                        gneres = "10402";
                        break;
                    case R.id.radio_id13:
                        gneres = "9648";
                        break;
                    case R.id.radio_id14:
                        gneres = "10749";
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }
                        break;
                    case R.id.radio_id15:
                        gneres = "878";
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }
                        break;
                    case R.id.radio_id16:
                        gneres = "10770";
                        break;
                    case R.id.radio_id17:
                        gneres = "53";
                        break;
                    case R.id.radio_id18:
                        gneres = "10752";
                        break;
                    case R.id.radio_id19:
                        gneres = "37";
                        break;
                    case R.id.radio_id20:
                        gneres = "";
                        sortBy = "popularity.desc";
                        cuntry = "";
                        releaseDate = "";
                        rated = "0";
                        pageNum = 1;
                        discoverMediaArrayList.clear();
                        discoverMediaRecycleAdapter.notifyDataSetChanged();
                        break;
                }
                //displayAd(rewardedAd);
                //Change color button when click
                for (int i = 0; i < buttonArrayList.size(); i++) {
                    if (buttonArrayList.get(i).getId() == id) {
                        buttonArrayList.get(i).setBackgroundResource(R.drawable.full_corner_background_black);
                        buttonArrayList.get(i).setTextColor(getResources().getColor(R.color.yellow));
                    } else {
                        buttonArrayList.get(i).setBackgroundResource(R.drawable.full_corner_background_yellow);
                        buttonArrayList.get(i).setTextColor(getResources().getColor(R.color.black2));
                    }
                }
                Toast.makeText(getContext(), gneresName, Toast.LENGTH_SHORT).show();
                discoverMediaWithGeners(pageNum + "", gneres, sortBy, cuntry, releaseDate, rated, mediaType, "");
            }
        });
    }

    //Get More media At Scroll
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
                        loadMoreMediaAtScroll(pageNum + "");
                    }
                }
            }
        });
    }

    //Get more at scroll
    private void loadMoreMediaAtScroll(String page) {
        buttonIsOnClick = false;
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                discoverMediaWithGeners(page, gneres, sortBy, cuntry, releaseDate, rated, mediaType, "");
                progressBar.setVisibility(View.GONE);
            }
        }, 1000);

    }

    //Swipe Refresh
    @SuppressLint("ResourceAsColor")
    private void swipeRefresh() {
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.black2);
        swipeRefreshLayout.setColorSchemeResources(R.color.yellow);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                gneres = "";
                sortBy = "popularity.desc";
                cuntry = "";
                releaseDate = "";
                rated = "0";
                pageNum = 1;
                //showFilterDialog(imageViewFilter);
                checkSwitch(switchMedia);
                checkOrientation();
                getTrendingMovie();
               /* if (discoverMediaArrayList.isEmpty())
                    loadMoreMediaAtScroll(pageNum + "");*/
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);
            }
        });
    }

    //Search
    private void searchOnClick() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                gneres = "";
                sortBy = "popularity.desc";
                cuntry = "";
                releaseDate = "";
                rated = "0";
                pageNum = 1;
                if (query.isEmpty()) {
                    search = false;
                    discoverMediaArrayList.clear();
                    discoverMediaWithGeners("1", gneres, sortBy, cuntry, releaseDate, rated, mediaType, "");
                } else {
                    search = true;
                    discoverMediaArrayList.clear();
                    searchOnMoviesOrTV(query, 1);
                    discoverMediaRecycleAdapter.getFilter().filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (discoverMediaRecycleAdapter == null)
                    discoverMediaRecycleAdapter = new MoviesRecycleAdapter(getContext());
                gneres = "";
                sortBy = "popularity.desc";
                cuntry = "";
                releaseDate = "";
                rated = "0";
                pageNum = 1;
                if (newText.isEmpty()) {
                    search = false;
                    discoverMediaArrayList.clear();
                    discoverMediaWithGeners("1", gneres, sortBy, cuntry, releaseDate, rated, mediaType, "");
                } else {
                    search = true;
                    discoverMediaArrayList.clear();
                    searchOnMoviesOrTV(newText, 1);
                    discoverMediaRecycleAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                search = false;
                discoverMediaArrayList.clear();
                discoverMediaWithGeners("1", gneres, sortBy, cuntry, releaseDate, rated, mediaType, "");
                return false;
            }
        });
    }

    //Search
    private void searchOnMoviesOrTV(String textToSearch, int page) {
        moviesViewModel.searchOnMoviesOrTV(textToSearch, page + "");
        moviesViewModel.searchOnMoviesOrTVMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                mediaArrayList = new ArrayList<>(moviesModel.getResults());
                totalPages = moviesModel.getTotal_pages();
                for (int i = 0; i < mediaArrayList.size(); i++) {
                    if (!discoverMediaArrayList.contains(mediaArrayList.get(i)))
                        discoverMediaArrayList.add(mediaArrayList.get(i));
                }
                discoverMediaRecycleAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("releaseDate", releaseDate);
        outState.putString("rated", rated);
        outState.putInt("cuntry", positionSellectedSpinnerCuntry);
        outState.putInt("sortBy", positionSellectedSpinnerSortBy);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

   /*
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("editTextShow", editTextShow);
        outState.putInt("count", count);
        outState.putFloat("num1", num1);
        outState.putFloat("num2", num2);
        outState.putInt("numOp", numOp);
        outState.putStringArrayList("opUsedArray", opUsedArray);
        outState.putFloat("result", result);
        outState.putBoolean("isNegative", isNegative);
        outState.putBoolean("radioStatus", radioStatus.isChecked());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }*/

    /**
     * Rewarded ad display
     **/
    private void openRewardedAd(RewardedAd rewardedAd) {
        rewardedAd = new RewardedAd(getContext(), "ca-app-pub-3940256099942544/5224354917");
        rewardedAd.loadAd(new AdRequest.Builder().build(), new RewardedAdLoadCallback());
        RewardedAd finalRewardedAd = rewardedAd;
        rewardedAd.show(getActivity(), new RewardedAdCallback() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                if (finalRewardedAd.isLoaded()) {

                } else {
                    Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                }
            }
        });
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

    @Override
    public void onPause() {
        super.onPause();
    }
}