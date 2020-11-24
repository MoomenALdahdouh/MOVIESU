package com.moomen.movieyou.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moomen.movieyou.R;
import com.moomen.movieyou.adapter.VideoTrailerAdapter;
import com.moomen.movieyou.adapter.VideoTrailerViewPagerAdapter;
import com.moomen.movieyou.model.firebase.VideosModel;
import com.moomen.movieyou.model.movies.MoviesModel;
import com.moomen.movieyou.model.movies.MoviesResults;
import com.moomen.movieyou.model.video.VideoTrailerMovie;
import com.moomen.movieyou.model.video.VideoTrailerMovieResults;
import com.moomen.movieyou.viewModel.MoviesViewModel;

import java.util.ArrayList;
import java.util.Collections;

public class LiveFragment extends Fragment {

    private MoviesViewModel moviesViewModel;
    private View view;
    //For live stream videos
    private ArrayList<VideoTrailerMovieResults> videoLiveMovieResultsArrayList = new ArrayList<>();
    private VideoTrailerAdapter videoLiveAdapter;
    private RecyclerView videoLiveRecyclerView;
    //For video trailer
    private ArrayList<VideoTrailerMovieResults> videoTrailerMovieResultsArrayList;
    private ArrayList<VideoTrailerMovieResults> videoTrailerMovieResultsArrayList1 = new ArrayList<>();
    //private VideoTrailerAdapter videoAdapter;
    private VideoTrailerViewPagerAdapter videoTrailerViewPagerAdapter;
    private ViewPager viewPager;
    //private RecyclerView videoRecyclerView;

    //Discover media
    private ArrayList<MoviesResults> discoverAllMediaArrayList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    //To get more movies when scrolling
    private int page = 1;
    private int totalPage;

    private int pageLive = 1;
    private int totalPageLive;
    private boolean isScrolling = false;
    private int currentMedia = 0;
    private int currentItems, totalItems, scrollOutItems;
    private ProgressBar progressBar;
    //
    private ArrayList<String> videoKey = new ArrayList<>();
    private ArrayList<MoviesResults> moviesResultsArrayList = new ArrayList<>();
    private ArrayList<MoviesResults> moviesResultsArrayList1 = new ArrayList<>();
    private VideoTrailerMovieResults videoTrailerMovieResults;
    private MoviesResults moviesResults;
    private String mediaType = "movie";

    //
    private String gneres = "";
    private Switch switchTypeMedia;
    private boolean isClickedButton = false;
    private int i = 0;
    private int counter = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9,
            button10, button11, button12, button13, button14, button15, button16, button17, button18, button19, button20;
    private ArrayList<Button> buttonArrayList = new ArrayList<>();
    private ArrayList<Button> buttonFreeArrayList = new ArrayList<>();

    //Button for Live stream
    private Button buttonUS;
    private Button buttonKids;
    private Button buttonTranslated;
    private int numTypeLive = 0;

    private ImageButton imageButtonRefresh;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference videosRef = database.getReference();
    //private DatabaseReference videos2Ref = database.getReference();
    private VideoTrailerMovieResults videosFirbase;
    private VideosModel videos2Firbase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_live, container, false);
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        /**1-Banner ad**/
        AdView adView = view.findViewById(R.id.adView_banner_liveFragment_id);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        progressBar = view.findViewById(R.id.progressBar_video_trailer_id);
        switchTypeMedia = view.findViewById(R.id.switch_video_id);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_live_id);
        button0 = view.findViewById(R.id.radio_id_f0);
        button1 = view.findViewById(R.id.radio_id_f1);
        button2 = view.findViewById(R.id.radio_id_f2);
        button3 = view.findViewById(R.id.radio_id_f3);
        button4 = view.findViewById(R.id.radio_id_f4);
        button5 = view.findViewById(R.id.radio_id_f5);
        button6 = view.findViewById(R.id.radio_id_f6);
        button7 = view.findViewById(R.id.radio_id_f7);
        button8 = view.findViewById(R.id.radio_id_f8);
        button9 = view.findViewById(R.id.radio_id_f9);
        button10 = view.findViewById(R.id.radio_id_f10);
        button11 = view.findViewById(R.id.radio_id_f11);
        button12 = view.findViewById(R.id.radio_id_f12);
        button13 = view.findViewById(R.id.radio_id_f13);
        button14 = view.findViewById(R.id.radio_id_f14);
        button15 = view.findViewById(R.id.radio_id_f15);
        button16 = view.findViewById(R.id.radio_id_f16);
        button17 = view.findViewById(R.id.radio_id_f17);
        button18 = view.findViewById(R.id.radio_id_f18);
        button19 = view.findViewById(R.id.radio_id_f19);
        button20 = view.findViewById(R.id.radio_id_f20);
        //add on array list
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
        //Button for Live stream
        buttonUS = view.findViewById(R.id.button_us_id);
        buttonUS.setBackgroundResource(R.drawable.full_corner_background_yellow);
        buttonUS.setTextColor(getResources().getColor(R.color.black2));
        buttonKids = view.findViewById(R.id.button_kids_live_id);
        buttonTranslated = view.findViewById(R.id.buttpn_translated_id);

        buttonFreeArrayList.add(buttonUS);
        buttonFreeArrayList.add(buttonKids);
        buttonFreeArrayList.add(buttonTranslated);


        //Button for trailer media
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
        //Button for Live stream
        onClickButtonFromFree(buttonUS);
        onClickButtonFromFree(buttonKids);
        onClickButtonFromFree(buttonTranslated);

        //Image button refresh to shuffle free movies on recycler view
        imageButtonRefresh = view.findViewById(R.id.imageButton_refresh_free_movie_id);
        imageButtonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.shuffle(videoLiveMovieResultsArrayList);
                videoLiveAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), R.string.shuffling, Toast.LENGTH_SHORT).show();
            }
        });

        //loadMoreMoviesAtScroll(page+"",mediaType);
        checkSwitch(switchTypeMedia);
        getLiveMovies(); //TODO: Active this method to get a live video
        readVideosFromFirbaseDB("us", pageLive);
        swipeRefresh();
        getMoreVedioLiveAtScroll();
        return view;
    }

    //Get all movie
    private void discoverMedia() {
        if (mediaType.equals("movie"))
            discoverMovies(page + "", gneres, "popularity.desc", "", "", "", "true");
        else if (mediaType.equals("tv"))
            discoverTV(page + "", gneres, "popularity.desc", "", "", "", "true");
    }

    private void discoverMovies(String page, String gneres, String sort_by, String with_original_language, String year, String vote_average_gte, String include_video) {
        moviesViewModel.discoverMovieWithGeners(page, gneres, sort_by, with_original_language, year, vote_average_gte, include_video);
        moviesViewModel.discoverMovieWithGenresMutableLiveData.observe(LiveFragment.this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                getDataForType(moviesModel);
            }
        });
    }

    private void discoverTV(String page, String gneres, String sort_by, String with_original_language, String year, String vote_average_gte, String include_video) {
        moviesViewModel.discoverTVWithGeners(page, gneres, sort_by, with_original_language, year, vote_average_gte);
        moviesViewModel.discoverTVWithGenresMutableLiveData.observe(LiveFragment.this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                getDataForType(moviesModel);
            }
        });
    }

    private void getDataForType(MoviesModel moviesModel) {
        totalPage = moviesModel.getTotal_pages();
        if (isClickedButton) {
            moviesResultsArrayList.clear();
            videoTrailerMovieResultsArrayList1.clear();
            isClickedButton = false;
        }
        //for (int i = 0; i < moviesModel.getResults().size(); i++) {
        if (!moviesResultsArrayList.contains(moviesModel.getResults().get(0))) {
            moviesResultsArrayList.addAll(moviesModel.getResults());
            getVideoTrailers(moviesResultsArrayList.get(0).getId() + "", mediaType);
        }
        // }
    }

    //Get video trailer movie
    private void getVideoTrailers(String idMedia, @NonNull String mediaType) {
        videoTrailerViewPagerAdapter = new VideoTrailerViewPagerAdapter(getContext());
        videoTrailerViewPagerAdapter.setVideoTrailerMovieResultsArrayList(videoTrailerMovieResultsArrayList1);
        if (mediaType.equals("movie")) {
            moviesViewModel.getVideoTrailerMovie(idMedia);
            moviesViewModel.videoTrailerMovieMutableLiveData.observe(LiveFragment.this, new Observer<VideoTrailerMovie>() {
                @Override
                public void onChanged(VideoTrailerMovie videoTrailerMovie) {
                    try {
                        getDataAndFill(videoTrailerMovie);
                    } catch (Exception ex) {
                    }
                }
            });
        } else if (mediaType.equals("tv")) {
            moviesViewModel.getVideoTrailerTV(idMedia);
            moviesViewModel.videoTrailerTVMutableLiveData.observe(LiveFragment.this, new Observer<VideoTrailerMovie>() {
                @Override
                public void onChanged(VideoTrailerMovie videoTrailerMovie) {
                    try {
                        getDataAndFill(videoTrailerMovie);
                    } catch (Exception ex) {
                    }

                }
            });
        }
        viewPager = view.findViewById(R.id.viewPager_video_trailer_id);
        viewPager.setAdapter(videoTrailerViewPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int ii) {
                if (ii == videoTrailerMovieResultsArrayList1.size() - 4) {
                    page++;
                    loadMoreMoviesAtScroll();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void getDataAndFill(VideoTrailerMovie videoTrailerMovie) {
        videoTrailerMovieResultsArrayList = new ArrayList<>(videoTrailerMovie.getResults());
        videoTrailerMovieResults = new VideoTrailerMovieResults();
        videoTrailerMovieResults = videoTrailerMovieResultsArrayList.get(0);
        if (!videoTrailerMovieResultsArrayList1.contains(videoTrailerMovieResults) && i < moviesResultsArrayList.size()) {
            videoTrailerMovieResults.setMoviesResults(moviesResultsArrayList.get(i));
            videoTrailerMovieResultsArrayList1.add(videoTrailerMovieResults);
            i++;
            getVideoTrailers(moviesResultsArrayList.get(i).getId() + "", mediaType);
        }
        //Collections.shuffle(videoTrailerMovieResultsArrayList1);
        videoTrailerViewPagerAdapter.notifyDataSetChanged();
    }

    //Get more latest Trailer at scroll
    private void loadMoreMoviesAtScroll() {
        isClickedButton = false;
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                discoverMedia();
                progressBar.setVisibility(View.GONE);
            }
        }, 1000);
    }

    //On click an button type media to filter
    private void onClickButtonGenres(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = button.getId();
                String gneresName = (String) button.getText();
                switch (id) {
                    case R.id.radio_id_f0:
                        gneres = "";
                        break;
                    case R.id.radio_id_f1:
                        gneres = "28";
                        break;
                    case R.id.radio_id_f2:
                        gneres = "12";
                        break;
                    case R.id.radio_id_f3:
                        gneres = "16";
                        break;
                    case R.id.radio_id_f4:
                        gneres = "35";
                        break;
                    case R.id.radio_id_f5:
                        gneres = "80";
                        break;
                    case R.id.radio_id_f6:
                        gneres = "99";
                        break;
                    case R.id.radio_id_f7:
                        gneres = "18";
                        break;
                    case R.id.radio_id_f8:
                        gneres = "10751";
                        break;
                    case R.id.radio_id_f9:
                        gneres = "14";
                        break;
                    case R.id.radio_id_f10:
                        gneres = "36";
                        break;
                    case R.id.radio_id_f11:
                        gneres = "27";
                        break;
                    case R.id.radio_id_f12:
                        gneres = "10402";
                        break;
                    case R.id.radio_id_f13:
                        gneres = "9648";
                        break;
                    case R.id.radio_id_f14:
                        gneres = "10749";
                        break;
                    case R.id.radio_id_f15:

                        gneres = "878";
                        break;
                    case R.id.radio_id_f16:
                        gneres = "10770";
                        break;
                    case R.id.radio_id_f17:
                        gneres = "53";
                        break;
                    case R.id.radio_id_f18:
                        gneres = "10752";
                        break;
                    case R.id.radio_id_f19:
                        gneres = "37";
                        break;
                    case R.id.radio_id_f20:
                        gneres = "";
                        i = 0;
                        break;
                }
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
                isClickedButton = true;
                page = 1;
                i = 0;
                discoverMedia();
                Toast.makeText(getContext(), gneresName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Check media if movie or tv by switch
    private void checkSwitch(@SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchMedia) {
        switchMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                i = 0;
                isClickedButton = true;
                if (switchMedia.isChecked()) {
                    mediaType = "tv";
                    discoverTV(page + "", gneres, "popularity.desc", "", "", "", "true");
                } else {
                    mediaType = "movie";
                    discoverMovies(page + "", gneres, "popularity.desc", "", "", "", "true");
                }
               /* viewPager.setCurrentItem(0);
                videoTrailerMovieResultsArrayList1.clear();
                moviesResultsArrayList.clear();
                videoTrailerViewPagerAdapter.notifyDataSetChanged();*/
            }
        });
    }

    /**
     * Live Part
     */
    private void getLiveMovies() {
        videoLiveMovieResultsArrayList = new ArrayList<>();
        videoLiveRecyclerView = view.findViewById(R.id.recyclerView_live_movie_home_id);
        videoLiveRecyclerView.setLayoutManager(linearLayoutManager);
        videoLiveAdapter = new VideoTrailerAdapter(getContext());
        videoLiveAdapter.setIsFreeMovie(true);
        videoLiveAdapter.setVideoTrailerMovieResultsArrayList(videoLiveMovieResultsArrayList);
        /*AdmobNativeAdAdapter admobNativeAdAdapterHor = AdmobNativeAdAdapter.Builder
                .with(
                        "ca-app-pub-6532316184285626/6171476354",//Create a native ad id from admob console
                        videoLiveAdapter,//The adapter you would normally set to your recyClerView
                        "medium" //"small","medium"or"custom"
                )
                .adItemIterval(10)//native ad repeating interval in the recyclerview
                .build();
        videoLiveRecyclerView.setAdapter(admobNativeAdAdapterHor);*/
        videoLiveRecyclerView.setAdapter(videoLiveAdapter);
        videoLiveRecyclerView.setHasFixedSize(true);
        //
        loadMoreMoviesAtScroll();

    }

    //TODO:Run this method to add new video if full all objects on firbase
    //Firbase write and read data
    /*private void writeVideosOnFirbaseDB() {
        int page = 1;
        String name = "";
        String key = "";
        String type = "";
        String status = "false";
        String id;
        int count = 20;
        for (int j = 0; j < videoLiveMovieResultsArrayList.size(); j++) {
            //id = j + 5 + "";
            name = videoLiveMovieResultsArrayList.get(j).getName();
            key = videoLiveMovieResultsArrayList.get(j).getKey();
            type = videoLiveMovieResultsArrayList.get(j).getType();
            status = videoLiveMovieResultsArrayList.get(j).getStatus();
            id = videoLiveMovieResultsArrayList.get(j).getId();
            videos2Firbase = new VideosModel(page + "", name, key, type, status, id);
            videosRef.child("videos2").push().setValue(videos2Firbase);
            if (j == count - 1) {
                count = count * 2;
                page++;
            }
        }
    }*/

    private void readVideosFromFirbaseDB(String type, int pageLive) {
        videosRef.child("videos2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                videoLiveMovieResultsArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    VideoTrailerMovieResults videoFirbase = snapshot.getValue(VideoTrailerMovieResults.class);
                    assert videoFirbase != null;
                    if (videoFirbase.getStatus().equals("true") && videoFirbase.getType().equals(type) && !videoLiveMovieResultsArrayList.contains(videoFirbase))
                        videoLiveMovieResultsArrayList.add(videoFirbase);
                }
                //Collections.shuffle(videoLiveMovieResultsArrayList);
                videoLiveAdapter.notifyDataSetChanged();
                //writeVideosOnFirbaseDB();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    //Get more movies when scroll
    private void getMoreVedioLiveAtScroll() {
        videoLiveRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                if (dy > 0) {
                    currentItems = linearLayoutManager.getChildCount();
                    totalItems = linearLayoutManager.getItemCount();
                    scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (isScrolling && ((currentItems + scrollOutItems) >= totalItems)) {
                        isScrolling = false;
                        pageLive++;
                        // loadMoreMoviesAtScroll(pageLive, numTypeLive);
                    }
                }
            }
        });
    }

    //Get more latest Trailer at scroll
    private void loadMoreMoviesAtScroll(int page, int numTypeLive) {
        //progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (numTypeLive) {
                    case 0:
                        readVideosFromFirbaseDB("us", page);
                        break;
                    case 1:
                        readVideosFromFirbaseDB("kids", page);
                        break;
                    case 2:
                        readVideosFromFirbaseDB("tr", page);//translated
                        break;
                }
                //progressBar.setVisibility(View.GONE);
            }
        }, 1000);
    }


    private void onClickButtonFromFree(Button button) {
        //load Interstitial Ad
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
        //
        pageLive = 1;
        if (!videoLiveMovieResultsArrayList.isEmpty())
            videoLiveMovieResultsArrayList.clear();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = button.getId();
                switch (id) {
                    case R.id.button_us_id:
                        numTypeLive = 0;
                        readVideosFromFirbaseDB("us", pageLive);
                        break;
                    case R.id.button_kids_live_id:
                        numTypeLive = 1;
                        readVideosFromFirbaseDB("kids", pageLive);
                        break;
                    case R.id.buttpn_translated_id:
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }
                        numTypeLive = 2;
                        readVideosFromFirbaseDB("tr", pageLive);//translated
                        break;
                }
                //change color button
                for (int i = 0; i < buttonFreeArrayList.size(); i++) {
                    if (buttonFreeArrayList.get(i).getId() == id) {
                        buttonFreeArrayList.get(i).setBackgroundResource(R.drawable.full_corner_background_yellow);
                        buttonFreeArrayList.get(i).setTextColor(getResources().getColor(R.color.black2));
                    } else {
                        buttonFreeArrayList.get(i).setBackgroundResource(R.drawable.full_corner_background_black1);
                        buttonFreeArrayList.get(i).setTextColor(getResources().getColor(R.color.yellow));
                    }
                }
            }
        });
    }

    //Swipe Refresh
    @SuppressLint("ResourceAsColor")
    private void swipeRefresh() {
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.yellow);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                viewPager.setCurrentItem(0);
                videoTrailerMovieResultsArrayList1.clear();
                moviesResultsArrayList.clear();
                videoTrailerViewPagerAdapter.notifyDataSetChanged();
                getLiveMovies(); //TODO: Active this method to get a live video
                readVideosFromFirbaseDB("us", pageLive);
                //Reset color button when refresh page and set defult color for US button
                buttonUS.setBackgroundResource(R.drawable.full_corner_background_yellow);
                buttonUS.setTextColor(getResources().getColor(R.color.black2));
                buttonKids.setBackgroundResource(R.drawable.full_corner_background_black1);
                buttonKids.setTextColor(getResources().getColor(R.color.yellow));
                buttonTranslated.setBackgroundResource(R.drawable.full_corner_background_black1);
                buttonTranslated.setTextColor(getResources().getColor(R.color.yellow));
                /*//make shufling for array
                Collections.shuffle(videoLiveMovieResultsArrayList);*/
                pageLive = 1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);
            }
        });
    }
}
