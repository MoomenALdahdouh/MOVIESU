package com.moomen.movieyou.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.efaso.admob_advanced_native_recyvlerview.AdmobNativeAdAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moomen.movieyou.BuildConfig;
import com.moomen.movieyou.R;
import com.moomen.movieyou.adapter.CommentsAdapter;
import com.moomen.movieyou.adapter.CreditAdapter;
import com.moomen.movieyou.adapter.MoviesRecycleAdapter;
import com.moomen.movieyou.adapter.SeasonsTVAdapter;
import com.moomen.movieyou.adapter.VideoTrailerAdapter;
import com.moomen.movieyou.adapter.VideoTrailerViewPagerAdapter;
import com.moomen.movieyou.adapter.ViewPagerAdapter;
import com.moomen.movieyou.db.WatchListDB;
import com.moomen.movieyou.model.credits.CreditsModel;
import com.moomen.movieyou.model.credits.CreditsModelCast;
import com.moomen.movieyou.model.db.WatchListModel;
import com.moomen.movieyou.model.firebase.CommentsModel;
import com.moomen.movieyou.model.image.ImageMovies;
import com.moomen.movieyou.model.image.ImageMoviesBackdrops;
import com.moomen.movieyou.model.image.ImageMoviesPosters;
import com.moomen.movieyou.model.movies.DetailMovie;
import com.moomen.movieyou.model.movies.DetailMovieGenres;
import com.moomen.movieyou.model.movies.MoviesModel;
import com.moomen.movieyou.model.movies.MoviesResults;
import com.moomen.movieyou.model.tv.DetailTV;
import com.moomen.movieyou.model.tv.DetailTVGenres;
import com.moomen.movieyou.model.tv.DetailTVSeasons;
import com.moomen.movieyou.model.video.VideoTrailerMovie;
import com.moomen.movieyou.model.video.VideoTrailerMovieResults;
import com.moomen.movieyou.viewModel.MoviesViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OverviewMovie extends AppCompatActivity implements View.OnTouchListener {

    public static final String KEY_VIDEO = "KEY_VIDEO";
    private MoviesViewModel moviesViewModel;
    private String idMovie;
    private String type = "movie";
    private SwipeRefreshLayout swipeRefreshLayout;
    //Similar Movie
    private MoviesRecycleAdapter similarMoviesRecycleAdapter;
    private ArrayList<MoviesResults> similarMoviesArrayList;
    private ArrayList<MoviesResults> similarMoviesArrayList1 = new ArrayList<>();
    private RecyclerView recyclerView;
    //for detail movie
    private TextView titleMovie;
    private TextView genresMovie;
    private TextView tagLineMovie;
    private TextView voteMovie;
    private TextView overviewMovie;
    //for Image movie
    private ArrayList<String> imagesMovieArrayList = new ArrayList<>();
    private ArrayList<ImageMoviesPosters> postersMovieArrayList;
    private ArrayList<ImageMoviesBackdrops> backgroundsMovieArrayList;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private ImageView posterMovie;
    //For video trailer movie
    private ArrayList<VideoTrailerMovieResults> videoTrailerMovieResultsArrayList;
    private ArrayList<VideoTrailerMovieResults> videoTrailerMovieResultsArrayList1 = new ArrayList<>();
    private VideoTrailerAdapter videoTrailerAdapter;
    private RecyclerView videoTrailerRecyclerView;
    private String keyVideo;
    //For Seasons TV
    private SeasonsTVAdapter seasonsTVAdapter;
    private ArrayList<DetailTVSeasons> seasonsTVArrayList = new ArrayList<>();
    //For Credit
    private CreditAdapter creditAdapter;
    private ArrayList<CreditsModelCast> creditsModelArrayList, creditsModelArrayList1 = new ArrayList<>();
    //For Recommendation
    private ArrayList<MoviesResults> recommendationMoviesArrayList, recommendationMoviesArrayList1 = new ArrayList<>();
    private TextView textViewVideo, textViewSimilarMovies, textViewCredits, textViewRecommendation;
    //When add to watch list
    private String titleMedia = "", urlPoster = "";
    private boolean isCheck = false;
    private WatchListDB watchListDB;
    private ImageView imageViewAddToWatchList, imageViewShareMovie;
    //Firbase Comments
    //Commponents
    private EditText editTextUserName, editTextComment;
    private Button buttonComment;
    private ImageButton imageButtonAddComment;
    private LinearLayout linearLayoutComments;
    private TextView textViewNoComments;
    //Variabl
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference commentsRef = database.getReference();
    private String name, date, comment, previousName = "", previousComment = "";
    private CommentsModel comments;
    //Adapter
    private ArrayList<CommentsModel> commentsModelArrayListDB = new ArrayList<>();
    private ArrayList<CommentsModel> commentsModelRevers = new ArrayList<>();
    private CommentsAdapter commentsAdapter = new CommentsAdapter(this);
    private RecyclerView commentRecyclerView;
    // Show image on full screen
    private AlertDialog alertDialog;
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;
    private ImageView imageViewFull;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_movie);
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        posterMovie = findViewById(R.id.poster_movie_overview_id);
        titleMovie = findViewById(R.id.title_movie_overview_id);
        tagLineMovie = findViewById(R.id.tag_line_movie_overview_id);
        genresMovie = findViewById(R.id.genres_movie_overview_id);
        voteMovie = findViewById(R.id.popularity_movie_overview_id);
        overviewMovie = findViewById(R.id.overview_movie_overview_id);
        viewPager = findViewById(R.id.viewPager_movie_overview_id);
        viewPagerAdapter = new ViewPagerAdapter(this, imagesMovieArrayList);
        viewPagerAdapter.setOnItemClickListener(new ViewPagerAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(ViewGroup view, int position) {
                String currentImage = imagesMovieArrayList.get(position);
                showpopup(currentImage);
            }
        });
        //watch list
        imageViewAddToWatchList = findViewById(R.id.imageView_add_to_watch_list_id);
        imageViewShareMovie = findViewById(R.id.imageView_share_movie_id);
        //For video trailer
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_overview_id);
        //Seasons TV
        seasonsTVAdapter = new SeasonsTVAdapter(getApplicationContext());
        //Item to gone If TV
        textViewVideo = findViewById(R.id.textView2);
        textViewSimilarMovies = findViewById(R.id.textView3);
        //For Gone items
        TextView textViewSeason = findViewById(R.id.textView4);
        textViewCredits = findViewById(R.id.textView10);
        textViewRecommendation = findViewById(R.id.textView13);
        //Firbase
        imageButtonAddComment = findViewById(R.id.image_button_add_comment_id);
        imageButtonAddComment.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_infinit));
        linearLayoutComments = findViewById(R.id.linearLayout_comments_id);
        linearLayoutComments.setVisibility(View.GONE);
        commentsAdapter = new CommentsAdapter(getApplicationContext());

        MobileAds.initialize(this);
        /**1-Banner ad**/
        AdView adView = new AdView(this);
        adView = findViewById(R.id.adView_banner_overview);
        bannerAd(adView);

        /**2-Native ad**/
        NativeExpressAdView adViewNative = (NativeExpressAdView) findViewById(R.id.adView_native_overview);
        AdRequest adRequestNative = new AdRequest.Builder().build();
        adViewNative.loadAd(adRequestNative);


        Intent movie = getIntent();
        if (movie != null && movie.hasExtra(MoviesRecycleAdapter.ID_MOVIE) && movie.hasExtra(MoviesRecycleAdapter.TYPE)) {
            idMovie = movie.getStringExtra(MoviesRecycleAdapter.ID_MOVIE);
            type = movie.getStringExtra(MoviesRecycleAdapter.TYPE);
        } else if (movie != null && movie.hasExtra(VideoTrailerViewPagerAdapter.ID_MEDIA) && movie.hasExtra(VideoTrailerViewPagerAdapter.TYPE_MEDIA)) {
            idMovie = movie.getStringExtra(VideoTrailerAdapter.ID_MEDIA);
            type = movie.getStringExtra(VideoTrailerAdapter.TYPE_MEDIA);
        }
        assert type != null;
        // Select the media if movie or tv
        if (idMovie != null && type.equals("movie")) {
            textViewSeason.setVisibility(View.GONE);
            getImagesMovie(idMovie);
            getDetailMovie(idMovie);
            getVideoTrailerMovie(idMovie);
            getSimilarMovie(idMovie);
            getCreditMovie(idMovie);
            getRecommendationsMovie(idMovie);
        } else if (idMovie != null && type.equals("tv")) {
            seasonsTVAdapter.setIdTV(idMovie);
            getImagesTV(idMovie);
            getDetailTV(idMovie);
            getVideoTrailerTV(idMovie);
            getSimilarTV(idMovie);
            getCreditTV(idMovie);
            getRecommendationsTV(idMovie);
        }

        shareMovieOnClick();
        addToWatchListOnClick();
        swipeRefresh();
        addCommentOnClick();
    }

    // Show image on full screen
    private void showpopup(String currentPage) {
        //Deffin dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_view_image, null);
        builder.setView(dialogView);
        alertDialog = builder.create();
        ProgressBar progressBar = dialogView.findViewById(R.id.progress_bar_image_dialog);
        imageViewFull = dialogView.findViewById(R.id.imageView_full_id);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        Picasso.get()
                .load(currentPage)
                .into(imageViewFull, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("e", e.getMessage());
                    }
                });
        if (alertDialog.getWindow() != null) {
            //make dialog without background
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            /*//Change size dialog to match parent
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(alertDialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            alertDialog.getWindow().setAttributes(lp);*/
        }
        alertDialog.show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return scaleGestureDetector.onTouchEvent(event);
    }

    //Get details for this movie
    private void getDetailMovie(String idMovie) {
        moviesViewModel.getDetailMovie(idMovie);
        moviesViewModel.detailMovieMutableLiveData.observe(this, new Observer<DetailMovie>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(DetailMovie detailMovie) {
                try {
                    urlPoster = "https://image.tmdb.org/t/p/w780/" + detailMovie.getPoster_path();
                    Picasso.get()
                            .load(urlPoster)
                            .into(posterMovie);
                    String dateRelease = "";
                    if (detailMovie.getRelease_date().length() >= 4)
                        dateRelease = "(" + detailMovie.getRelease_date().substring(0, 4) + ")";
                    titleMedia = detailMovie.getTitle();
                    titleMovie.setText(titleMedia + " " + dateRelease);
                    ArrayList<DetailMovieGenres> detailMovieGenresArrayList = new ArrayList<>(detailMovie.getGenres());
                    String genres = ",";
                    for (int i = 0; i < detailMovieGenresArrayList.size(); i++) {
                        if (i != 0)
                            genres = genres + ", " + detailMovieGenresArrayList.get(i).getName();
                        else
                            genres = detailMovieGenresArrayList.get(i).getName();
                    }
                    genresMovie.setText(genres);
                    tagLineMovie.setText(detailMovie.getTagline());
                    int voteAverage = (int) (detailMovie.getVote_average() * 10);
                    voteMovie.setText(voteAverage + "%");
                    overviewMovie.setText(detailMovie.getOverview());
                } catch (NullPointerException e) {
                    Log.d("e", e.getMessage());
                }
            }
        });
    }

    //Get Image movie
    private void getImagesMovie(String idMovie) {
        moviesViewModel.getImagesMovie(idMovie);
        viewPager.setAdapter(viewPagerAdapter);
        //timer.scheduleAtFixedRate(new TimeTask(),2000,    4000);
        moviesViewModel.imageMovieMutableLiveData.observe(this, new Observer<ImageMovies>() {
            @Override
            public void onChanged(ImageMovies imageMovies) {
                try {
                    postersMovieArrayList = new ArrayList<>(imageMovies.getPosters());
                    backgroundsMovieArrayList = new ArrayList<>(imageMovies.getBackdrops());
                    //fill image "Posters" & "Background" in view pager
                    for (int i = 0; i < backgroundsMovieArrayList.size(); i++)
                        imagesMovieArrayList.add(backgroundsMovieArrayList.get(i).getFile_path());
                    if (!postersMovieArrayList.isEmpty())
                        imagesMovieArrayList.add(postersMovieArrayList.get(0).getFile_path());
                    //Auto Image Slider with ViewPager
                    viewPagerAdapter.setTimer(viewPager, 5);
                    viewPagerAdapter.notifyDataSetChanged();
                } catch (Exception ex) {
                }

            }
        });
    }

    //Get video trailer movie
    private void getVideoTrailerMovie(String idMovie) {
        moviesViewModel.getVideoTrailerMovie(idMovie);
        videoTrailerAdapter = new VideoTrailerAdapter(this);
        videoTrailerAdapter.setVideoTrailerMovieResultsArrayList(videoTrailerMovieResultsArrayList1);
        moviesViewModel.videoTrailerMovieMutableLiveData.observe(this, new Observer<VideoTrailerMovie>() {
            @Override
            public void onChanged(VideoTrailerMovie videoTrailerMovie) {
                try {
                    videoTrailerMovieResultsArrayList = new ArrayList<>(videoTrailerMovie.getResults());
                    videoTrailerMovieResultsArrayList1.clear();
                    videoTrailerMovieResultsArrayList1.addAll(videoTrailerMovieResultsArrayList);
                    videoTrailerAdapter.notifyDataSetChanged();
                    if (!videoTrailerMovieResultsArrayList.isEmpty())
                        keyVideo = videoTrailerMovieResultsArrayList.get(0).getKey();
                    else
                        textViewVideo.setVisibility(View.GONE);
                    goneVideoTextIfNotFoundData();
                } catch (Exception ex) {
                }
            }
        });
        videoTrailerRecyclerView = findViewById(R.id.recyclerView_video_trailer_id);
        videoTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        videoTrailerRecyclerView.setAdapter(videoTrailerAdapter);

    }

    //Get similar movies
    private void getSimilarMovie(String idMovie) {
        moviesViewModel.getSimilarMovies(idMovie);
        similarMoviesRecycleAdapter = new MoviesRecycleAdapter(this);
        similarMoviesRecycleAdapter.setList(similarMoviesArrayList1);
        moviesViewModel.similarMovieMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                try {
                    similarMoviesArrayList = new ArrayList<>(moviesModel.getResults());
                    similarMoviesArrayList1.addAll(similarMoviesArrayList);
                    similarMoviesRecycleAdapter.notifyDataSetChanged();
                    goneSimilarTextIfNotFoundData();
                } catch (Exception ex) {
                }

            }
        });
        recyclerView = findViewById(R.id.recyclerView_similar_movies_overview_id);
        fillMoviesInHorizontalRecycle(recyclerView, similarMoviesRecycleAdapter);
    }

    //Get credit movies
    private void getCreditMovie(String idMovie) {
        moviesViewModel.getCreditsMovie(idMovie);
        creditAdapter = new CreditAdapter(this);
        creditAdapter.setCreditsModelArrayList(creditsModelArrayList1);
        moviesViewModel.creditsMovieMutableLiveData.observe(this, new Observer<CreditsModel>() {
            @Override
            public void onChanged(CreditsModel creditsModel) {
                try {
                    creditsModelArrayList = new ArrayList<>(creditsModel.getCast());
                    creditsModelArrayList1.addAll(creditsModelArrayList);
                    creditsModelArrayList = new ArrayList<>(creditsModel.getCrew());
                    creditsModelArrayList1.addAll(creditsModelArrayList);
                    creditAdapter.notifyDataSetChanged();
                    goneCreditTextIfNotFoundData();
                } catch (Exception ex) {
                }

            }
        });
        recyclerView = findViewById(R.id.recyclerView_credit_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(creditAdapter);
    }

    //Get recommendations movies
    private void getRecommendationsMovie(String idMovie) {
        moviesViewModel.getRecommendationsMovie(idMovie);
        similarMoviesRecycleAdapter = new MoviesRecycleAdapter(this);
        similarMoviesRecycleAdapter.setList(recommendationMoviesArrayList1);
        moviesViewModel.recommendationsMovieMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                try {
                    recommendationMoviesArrayList = new ArrayList<>(moviesModel.getResults());
                    recommendationMoviesArrayList1.addAll(recommendationMoviesArrayList);
                    similarMoviesRecycleAdapter.notifyDataSetChanged();
                    goneRecommendationTextIfNotFoundData();
                } catch (Exception ex) {
                }
            }
        });
        recyclerView = findViewById(R.id.recyclerView_recommendation_id);
        fillMoviesInHorizontalRecycle(recyclerView, similarMoviesRecycleAdapter);
    }

    /**
     * TV part
     **/
    //Get details for this TV
    private void getDetailTV(String idTV) {
        moviesViewModel.getDetailTV(idTV);
        moviesViewModel.detailTVMutableLiveData.observe(this, new Observer<DetailTV>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(DetailTV detailTV) {
                try {
                    urlPoster = "https://image.tmdb.org/t/p/w780/" + detailTV.getPoster_path();
                    Picasso.get()
                            .load(urlPoster)
                            .into(posterMovie);
                    String dateRelease = "";
                    if (detailTV.getFirst_air_date().length() >= 4)
                        dateRelease = "(" + detailTV.getFirst_air_date().substring(0, 4) + ")";
                    String name = detailTV.getName();
                    titleMovie.setText(name + " " + dateRelease);
                    tagLineMovie.setVisibility(View.GONE);
                    int voteAverage = (int) (detailTV.getVote_average() * 10);
                    voteMovie.setText(voteAverage + "%");
                    overviewMovie.setText(detailTV.getOverview());
                    //Get Genres TV
                    ArrayList<DetailTVGenres> detailMovieGenresArrayList = new ArrayList<>(detailTV.getGenres());
                    String genres = ",";
                    for (int i = 0; i < detailMovieGenresArrayList.size(); i++) {
                        if (i != 0)
                            genres = genres + ", " + detailMovieGenresArrayList.get(i).getName();
                        else
                            genres = detailMovieGenresArrayList.get(i).getName();
                    }
                    genresMovie.setText(genres);
                    //Get Seasons TV
                    seasonsTVArrayList = new ArrayList<>(detailTV.getSeasons());
                    seasonsTVAdapter.setDetailTVSeasonsArrayList(seasonsTVArrayList);
                    recyclerView = findViewById(R.id.recyclerView_seasons_tv_id);
                    recyclerView.setAdapter(seasonsTVAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    recyclerView.setHasFixedSize(true);
                } catch (NullPointerException e) {
                    Log.d("e", e.getMessage());
                }
            }
        });
    }

    //Get Image TV
    private void getImagesTV(String idTV) {
        moviesViewModel.getImagesTV(idTV);
        viewPagerAdapter = new ViewPagerAdapter(this, imagesMovieArrayList);
        viewPager.setAdapter(viewPagerAdapter);
        moviesViewModel.imageTVMutableLiveData.observe(this, new Observer<ImageMovies>() {
            @Override
            public void onChanged(ImageMovies imageMovies) {
                try {
                    postersMovieArrayList = new ArrayList<>(imageMovies.getPosters());
                    backgroundsMovieArrayList = new ArrayList<>(imageMovies.getBackdrops());
                    //fill image "Posters" & "Background" in view pager
                    for (int i = 0; i < backgroundsMovieArrayList.size(); i++) {
                        imagesMovieArrayList.add(backgroundsMovieArrayList.get(i).getFile_path());
                    }
                    if (!postersMovieArrayList.isEmpty())
                        imagesMovieArrayList.add(postersMovieArrayList.get(0).getFile_path());
                    //Auto Image Slider with ViewPager
                    viewPagerAdapter.setTimer(viewPager, 5);
                    viewPagerAdapter.notifyDataSetChanged();
                } catch (Exception ex) {

                }
            }
        });
    }

    //Get video trailer TV
    private void getVideoTrailerTV(String idTV) {
        moviesViewModel.getVideoTrailerTV(idTV);
        videoTrailerAdapter = new VideoTrailerAdapter(this);
        videoTrailerAdapter.setVideoTrailerMovieResultsArrayList(videoTrailerMovieResultsArrayList1);
        moviesViewModel.videoTrailerTVMutableLiveData.observe(this, new Observer<VideoTrailerMovie>() {
            @Override
            public void onChanged(VideoTrailerMovie videoTrailerMovie) {
                try {
                    videoTrailerMovieResultsArrayList = new ArrayList<>(videoTrailerMovie.getResults());
                    videoTrailerMovieResultsArrayList1.clear();
                    videoTrailerMovieResultsArrayList1.addAll(videoTrailerMovieResultsArrayList);
                    videoTrailerAdapter.notifyDataSetChanged();
                    if (!videoTrailerMovieResultsArrayList.isEmpty())
                        keyVideo = videoTrailerMovieResultsArrayList.get(0).getKey();
                    else
                        textViewVideo.setVisibility(View.GONE);
                    goneVideoTextIfNotFoundData();
                } catch (Exception ex) {

                }
            }
        });
        videoTrailerRecyclerView = findViewById(R.id.recyclerView_video_trailer_id);
        videoTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        videoTrailerRecyclerView.setAdapter(videoTrailerAdapter);
    }

    //Get similar movies
    private void getSimilarTV(String idTV) {
        moviesViewModel.getSimilarTV(idTV);
        similarMoviesRecycleAdapter = new MoviesRecycleAdapter(this);
        similarMoviesRecycleAdapter.setList(similarMoviesArrayList1);
        moviesViewModel.similarTVMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                try {
                    similarMoviesArrayList = new ArrayList<>(moviesModel.getResults());
                    similarMoviesArrayList1.addAll(similarMoviesArrayList);
                    similarMoviesRecycleAdapter.notifyDataSetChanged();
                    goneSimilarTextIfNotFoundData();
                } catch (Exception ex) {
                }
            }
        });
        recyclerView = findViewById(R.id.recyclerView_similar_movies_overview_id);
        fillMoviesInHorizontalRecycle(recyclerView, similarMoviesRecycleAdapter);
    }

    //Get credit movies
    private void getCreditTV(String idTV) {
        moviesViewModel.getCreditsTV(idTV);
        creditAdapter = new CreditAdapter(this);
        creditAdapter.setCreditsModelArrayList(creditsModelArrayList1);
        moviesViewModel.creditsTVMutableLiveData.observe(this, new Observer<CreditsModel>() {
            @Override
            public void onChanged(CreditsModel creditsModel) {
                try {
                    creditsModelArrayList = new ArrayList<>(creditsModel.getCast());
                    creditsModelArrayList1.addAll(creditsModelArrayList);
                    creditsModelArrayList = new ArrayList<>(creditsModel.getCrew());
                    creditsModelArrayList1.addAll(creditsModelArrayList);
                    creditAdapter.notifyDataSetChanged();
                    goneCreditTextIfNotFoundData();
                } catch (Exception ex) {

                }
            }
        });
        recyclerView = findViewById(R.id.recyclerView_credit_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(creditAdapter);
    }

    //Get recommendations tv
    private void getRecommendationsTV(String idMovie) {
        moviesViewModel.getRecommendationsTV(idMovie);
        similarMoviesRecycleAdapter = new MoviesRecycleAdapter(this);
        similarMoviesRecycleAdapter.setList(recommendationMoviesArrayList1);
        moviesViewModel.recommendationsTVMutableLiveData.observe(this, new Observer<MoviesModel>() {
            @Override
            public void onChanged(MoviesModel moviesModel) {
                try {
                    recommendationMoviesArrayList = new ArrayList<>(moviesModel.getResults());
                    recommendationMoviesArrayList1.addAll(recommendationMoviesArrayList);
                    similarMoviesRecycleAdapter.notifyDataSetChanged();
                    goneRecommendationTextIfNotFoundData();
                } catch (Exception ex) {

                }
            }
        });
        recyclerView = findViewById(R.id.recyclerView_recommendation_id);
        fillMoviesInHorizontalRecycle(recyclerView, similarMoviesRecycleAdapter);
    }

    //Fill data on Horizontal recycler view
    private void fillMoviesInHorizontalRecycle(RecyclerView recyclerView, MoviesRecycleAdapter moviesRecycleAdapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
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
                if (idMovie != null && type.equals("MOVIE")) {
                    getDetailMovie(idMovie);
                    getImagesMovie(idMovie);
                    getVideoTrailerMovie(idMovie);
                    getSimilarMovie(idMovie);
                    getCreditMovie(idMovie);
                    getRecommendationsMovie(idMovie);
                } else if (idMovie != null && type.equals("TV")) {
                    getDetailTV(idMovie);
                    getImagesTV(idMovie);
                    getVideoTrailerTV(idMovie);
                    getSimilarTV(idMovie);
                    getCreditTV(idMovie);
                    getRecommendationsTV(idMovie);
                }
                shareMovieOnClick();
                addToWatchListOnClick();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);
            }
        });
    }

    //On click image video to run
    public void onclickPlayVideo(View view) {
        if (!videoTrailerMovieResultsArrayList.isEmpty()) {
            Intent intent = new Intent(this, PlayVideoTrailerYouTube.class);
            intent.putExtra(KEY_VIDEO, keyVideo);
            startActivity(intent);
        } else
            Toast.makeText(getApplicationContext(), R.string.the_trailer_is_not_available_now, Toast.LENGTH_SHORT).show();
    }

    //Gone Text view if not found data
    private void goneRecommendationTextIfNotFoundData() {
        if (recommendationMoviesArrayList1.isEmpty())
            textViewRecommendation.setVisibility(View.GONE);
        else
            textViewRecommendation.setVisibility(View.VISIBLE);
    }

    private void goneVideoTextIfNotFoundData() {
        if (videoTrailerMovieResultsArrayList1.isEmpty())
            textViewVideo.setVisibility(View.GONE);
        else
            textViewVideo.setVisibility(View.VISIBLE);
    }

    private void goneCreditTextIfNotFoundData() {
        if (creditsModelArrayList1.isEmpty())
            textViewCredits.setVisibility(View.GONE);
        else
            textViewCredits.setVisibility(View.VISIBLE);
    }

    private void goneSimilarTextIfNotFoundData() {
        if (similarMoviesArrayList1.isEmpty())
            textViewSimilarMovies.setVisibility(View.GONE);
        else
            textViewSimilarMovies.setVisibility(View.VISIBLE);
    }

    //Click on image view add to watch list
    private void addToWatchListOnClick() {
        imageViewAddToWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCheck) {
                    isCheck = true;
                    imageViewAddToWatchList.setImageResource(R.drawable.ic_baseline_playlist_add_check_24);
                    watchListDB = new WatchListDB(getApplicationContext());
                    int size = (watchListDB.getSize() / 10) + 1;
                    watchListDB.addMovieTOWatchList(size, idMovie, "true", titleMedia, type.toLowerCase());
                    Toast.makeText(getApplicationContext(), R.string.add_to_watch_list, Toast.LENGTH_SHORT).show();
                } else {
                    isCheck = false;
                    watchListDB.deleteMovieFromWatchList(type.toLowerCase() + idMovie);
                    imageViewAddToWatchList.setImageResource(R.drawable.ic_baseline_playlist_add_24);
                    Toast.makeText(getApplicationContext(), R.string.remove_from_list, Toast.LENGTH_SHORT).show();
                }
                ArrayList<WatchListModel> arrayList = new ArrayList<>(watchListDB.getAllMovieAreChecking());
                int s = arrayList.size();
            }
        });
    }

    //Share an movie
    private void shareMovieOnClick() {
        imageViewShareMovie.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint({"SetWorldReadable", "WrongConstant"})
            @Override
            public void onClick(View v) {
                Drawable drawable = posterMovie.getDrawable(); //posterMovie is image view you will be shared
                if (drawable instanceof BitmapDrawable) {
                    ((BitmapDrawable) drawable).getBitmap();
                }
                try {
                    File file = new File(getApplicationContext().getExternalCacheDir(), File.separator + titleMedia + ".jpg");
                    FileOutputStream fOut = new FileOutputStream(file);
                    int width = drawable.getIntrinsicWidth();
                    width = width > 0 ? width : 1;
                    int height = drawable.getIntrinsicHeight();
                    height = height > 0 ? height : 1;
                    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    drawable.draw(canvas);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true, false);
                    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    //intent image
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);
                    intent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    //intent text
                    String nameMedia = titleMedia;
                    String appURL = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, appURL + "\n" + nameMedia);
                    intent.setType("image/jpg");
                    intent.setType("text/plain");
                    startActivity(Intent.createChooser(intent, "Share image via"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //Firbase write and read firbase database
    private void writeCommentsOnFirbaseDB() {
        buttonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = String.valueOf(editTextUserName.getText());
                date = DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
                comment = String.valueOf(editTextComment.getText());
                comments = new CommentsModel(name, date, idMovie, type, comment);
                if (!name.equals(previousName) || !comment.equals(previousComment)) {
                    commentsRef.child("comments").push().setValue(comments);
                    previousName = name;
                    previousComment = comment;
                    Toast.makeText(getApplicationContext(), R.string.added_comment, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), R.string.write_new_comment, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void readCommentsFromFirbaseDB() {
        commentsRef.child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentsModelArrayListDB.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CommentsModel comment = snapshot.getValue(CommentsModel.class);
                    assert comment != null;
                    if (comment.getIdMedia().equals(idMovie) && comment.getTypeMedia().equals(type))
                        commentsModelRevers.add(comment);
                }
                for (int i = commentsModelRevers.size() - 1; i >= 0; i--) {
                    commentsModelArrayListDB.add(commentsModelRevers.get(i));
                }
                if (commentsModelArrayListDB.isEmpty())
                    textViewNoComments.setVisibility(View.VISIBLE);
                else
                    textViewNoComments.setVisibility(View.GONE);
                commentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        commentsAdapter.setCommentsModelArrayList(commentsModelArrayListDB);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        commentRecyclerView.setAdapter(commentsAdapter);
        commentRecyclerView.setHasFixedSize(true);
    }

    private void addCommentOnClick() {
        imageButtonAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Deffin dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(OverviewMovie.this);
                View filterDialogView = LayoutInflater.from(OverviewMovie.this).inflate(R.layout.comment_layout_includ, null);
                builder.setView(filterDialogView);
                final AlertDialog alertDialog = builder.create();
                commentRecyclerView = filterDialogView.findViewById(R.id.recycler_view_comments_id);
                editTextUserName = filterDialogView.findViewById(R.id.edit_text_user_name_id);
                //To show kebord
                /*editTextUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        }
                    }
                });*/
                editTextComment = filterDialogView.findViewById(R.id.edit_text_user_comment_id);
                textViewNoComments = filterDialogView.findViewById(R.id.textView_no_comment_id);
                readCommentsFromFirbaseDB();
                filterDialogView.findViewById(R.id.button_comment_id).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name = String.valueOf(editTextUserName.getText());
                        date = DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
                        comment = String.valueOf(editTextComment.getText());
                        comments = new CommentsModel(name, date, idMovie, type, comment);
                        if (!name.isEmpty() && !comment.isEmpty()) {
                            if (!name.equals(previousName) || !comment.equals(previousComment)) {
                                commentsRef.child("comments").push().setValue(comments);
                                previousName = name;
                                previousComment = comment;
                                Toast.makeText(getApplicationContext(), R.string.added_comment, Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(getApplicationContext(), R.string.write_new_comment, Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getApplicationContext(), R.string.write_name_and_comment, Toast.LENGTH_SHORT).show();
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
     * Banner ad
     **/
    private void bannerAd(AdView adView) {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    //Change scale image when tuch and swipe by tow fingures
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= scaleGestureDetector.getScaleFactor();
            imageViewFull.setScaleX(scaleFactor);
            imageViewFull.setScaleY(scaleFactor);
            return true;
        }
    }
}