package com.moomen.movieyou.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.efaso.admob_advanced_native_recyvlerview.AdmobNativeAdAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.moomen.movieyou.R;
import com.moomen.movieyou.adapter.MoviesRecycleAdapter;
import com.moomen.movieyou.db.WatchListDB;
import com.moomen.movieyou.model.db.WatchListModel;
import com.moomen.movieyou.model.movies.MoviesModel;
import com.moomen.movieyou.model.movies.MoviesResults;
import com.moomen.movieyou.viewModel.MoviesViewModel;

import java.util.ArrayList;

public class WatchListFragment extends Fragment {

    private static ArrayList<MoviesResults> moviesResultsArrayList = new ArrayList<>();
    private static String idMediaSimilar = "5";
    private MoviesRecycleAdapter recycleAdapter;
    private MoviesRecycleAdapter recycleAdapterSimilarMovie;
    private ArrayList<MoviesResults> similarMoviesArrayList;
    private ArrayList<MoviesResults> similarMoviesArrayList1 = new ArrayList<>();
    private MoviesViewModel moviesViewModel;
    private RecyclerView recyclerView;
    private WatchListDB watchListDB;
    private TextView emptyWatchList;
    private View view;

    private Boolean isScrolling = false;
    private int totalPages;
    private int currentItems, totalItems, scrollOutItems;
    private int page = 1;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<WatchListModel> watchListModelArrayList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private InterstitialAd mInterstitialAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_watch_list, container, false);
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        emptyWatchList = view.findViewById(R.id.textView_empty_watch_list_id);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_watch_list_id);
        recyclerView = view.findViewById(R.id.recycler_view_my_watch_list_id);
        progressBar = view.findViewById(R.id.progressBar_watch_list_id);
        watchListDB = new WatchListDB(getContext());
        totalPages = (watchListDB.getSize() / 10) + 1;

        //initializeInterstitialAd();

        /**1-Banner ad**/
        AdView adView = view.findViewById(R.id.adView_banner_watchList);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        getMediaForWatchList(page + "");
        //getSimilarMovieForYou(idMediaSimilar);
        checkSizeArray(moviesResultsArrayList);
        swipeRefresh();
        //deleteMedia();
        getMoreMoviesAtScroll();
        return view;
    }

    //Get movie by id from api
    private void getMediaForWatchList(String page) {
        //TODO: use coment code line after solve the problem but now use next line to get Movie from DB without page
        //watchListModelArrayList.addAll(watchListDB.getAllMovieForPage(page));
        watchListModelArrayList.addAll(watchListDB.getAllMovieAreChecking());
        recycleAdapter = new MoviesRecycleAdapter(getContext());
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recycleAdapter.setChangeWidthLayoutMovieItem(true);
        recycleAdapter.setWatchListFragment(true);
        recycleAdapter.setLayoutId(R.layout.search_item);
        //Get similer for your fivouret movie
        if (!watchListModelArrayList.isEmpty()) {
            if (watchListModelArrayList.size() > 1)
                idMediaSimilar = watchListModelArrayList.get(watchListModelArrayList.size() - 1).getIdMovieWatchList();
            else
                idMediaSimilar = watchListModelArrayList.get(0).getIdMovieWatchList();
        }
        getSimilarMovieForYou(idMediaSimilar);

        for (int i = 0; i < watchListModelArrayList.size(); i++) {
            String mediaType = watchListModelArrayList.get(i).getMediaType();
            String idMedia = watchListModelArrayList.get(i).getIdMovieWatchList();
            if (mediaType.equals("movie")) {
                getDetailMovie(idMedia);
            } else if (mediaType.equals("tv")) {
                getDetailTV(idMedia);
            }
        }
        recycleAdapter.setIsHorizontalRecycle(true);
        //recycleAdapter.setPositionAd(1);
        recycleAdapter.setList(moviesResultsArrayList);
        recycleAdapter.setPositionAd(0);
        /*AdmobNativeAdAdapter admobNativeAdAdapterHor = AdmobNativeAdAdapter.Builder
                .with(
                        "ca-app-pub-6532316184285626/6171476354",//Create a native ad id from admob console
                        recycleAdapter,//The adapter you would normally set to your recyClerView
                        "medium" //"small","medium"or"custom"
                )
                .adItemIterval(10)//native ad repeating interval in the recyclerview
                .build();
        recyclerView.setAdapter(admobNativeAdAdapterHor);*/
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @SuppressLint("FragmentLiveDataObserve")
    private void getDetailMovie(String idMovie) {
        moviesViewModel.getMovieResultsById(idMovie);
        moviesViewModel.moviesResultsMutableLiveData.observe(WatchListFragment.this, new Observer<MoviesResults>() {
            @Override
            public void onChanged(MoviesResults moviesResults) {
                if (!moviesResultsArrayList.contains(moviesResults)) {
                    moviesResultsArrayList.add(moviesResults);
                    for (int i = 0; i < moviesResultsArrayList.size(); i++) {
                        String title = moviesResultsArrayList.get(i).getTitle();
                        int count = 0;
                        if (title != null) {
                            for (int j = 0; j < moviesResultsArrayList.size(); j++) {
                                if (moviesResultsArrayList.get(i).getTitle().equals(moviesResultsArrayList.get(j).getTitle())) {
                                    count++;
                                    if (count > 1)
                                        moviesResultsArrayList.remove(i);
                                }
                            }
                        }
                    }
                    checkSizeArray(moviesResultsArrayList);
                    recycleAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @SuppressLint("FragmentLiveDataObserve")
    private void getDetailTV(String idTV) {
        moviesViewModel.getTVResultsById(idTV);
        moviesViewModel.tvResultsMutableLiveData.observe(WatchListFragment.this, new Observer<MoviesResults>() {
            @Override
            public void onChanged(MoviesResults moviesResults) {
                if (!moviesResultsArrayList.contains(moviesResults)) {
                    moviesResultsArrayList.add(moviesResults);
                    for (int i = 0; i < moviesResultsArrayList.size(); i++) {
                        String name = moviesResultsArrayList.get(i).getName();
                        int count = 0;
                        if (name != null) {
                            for (int j = 0; j < moviesResultsArrayList.size(); j++) {
                                if (moviesResultsArrayList.get(i).getName().equals(moviesResultsArrayList.get(j).getName())) {
                                    count++;
                                    if (count > 1)
                                        moviesResultsArrayList.remove(i);
                                }
                            }
                        }
                    }
                    checkSizeArray(moviesResultsArrayList);
                    recycleAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    //Recommend for you
    //Get similar movies
    @SuppressLint("FragmentLiveDataObserve")
    private void getSimilarMovieForYou(String idMovie) {
        moviesViewModel.getSimilarMovies(idMovie);
        recycleAdapterSimilarMovie = new MoviesRecycleAdapter(getContext());
        moviesViewModel.similarMovieMutableLiveData.observe(WatchListFragment.this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                similarMoviesArrayList = new ArrayList<>(moviesModel.getResults());
                recycleAdapterSimilarMovie.setList(similarMoviesArrayList);
            }
        });
        RecyclerView recyclerViewSimilar = view.findViewById(R.id.recycler_view_for_you_watch_list_id);
        fillMoviesInHorizontalRecycle(recyclerViewSimilar, recycleAdapterSimilarMovie);
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

    //Swipe Refresh
    @SuppressLint("ResourceAsColor")
    private void swipeRefresh() {
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.yellow);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMediaForWatchList(page + "");
                getSimilarMovieForYou(idMediaSimilar);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);
            }
        });
    }

    //TODO: If you need delet movie from watch list by swipe movie active this method
    //Delete select media from array
    private void deleteMedia() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                MoviesResults moviesResults = moviesResultsArrayList.get(position);
                String name = moviesResults.getTitle();
                String type = "movie";
                String id = String.valueOf(moviesResults.getId());
                if (name == null)
                    type = "tv";

                watchListDB.deleteMovieFromWatchList(type + id);
                moviesResultsArrayList.remove(position);
                Toast.makeText(getContext(), R.string.remove_from_list, Toast.LENGTH_SHORT).show();
                recycleAdapter.notifyDataSetChanged();
                checkSizeArray(moviesResultsArrayList);
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void checkSizeArray(ArrayList<MoviesResults> moviesResultsArrayList) {
        if (moviesResultsArrayList.isEmpty())
            emptyWatchList.setVisibility(View.VISIBLE);
        else
            emptyWatchList.setVisibility(View.GONE);
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
                if (dy > 0) {
                    currentItems = linearLayoutManager.getChildCount();
                    totalItems = linearLayoutManager.getItemCount();
                    scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (isScrolling && ((currentItems + scrollOutItems) >= totalItems) && page <= totalPages) {
                        isScrolling = false;
                        page++;
                        loadMoreMoviesAtScroll(page + "");
                    }
                }
            }
        });
    }

    //Get more latest Trailer at scroll
    private void loadMoreMoviesAtScroll(String page) {
        //progressBar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getMediaForWatchList(page + "");
                //progressBar.setVisibility(View.GONE);
            }
        }, 1000);
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
}
