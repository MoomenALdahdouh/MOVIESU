package com.moomen.movieyou.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moomen.movieyou.client.MoviesClient;
import com.moomen.movieyou.model.configuration.ConfigurationResults;
import com.moomen.movieyou.model.credits.CreditsModel;
import com.moomen.movieyou.model.credits.OverviewCreditModel;
import com.moomen.movieyou.model.image.ImageMovies;
import com.moomen.movieyou.model.movies.DetailMovie;
import com.moomen.movieyou.model.movies.MoviesModel;
import com.moomen.movieyou.model.movies.MoviesResults;
import com.moomen.movieyou.model.movies.TitleMovie;
import com.moomen.movieyou.model.tv.DetailTV;
import com.moomen.movieyou.model.tv.DetailTVEpisode;
import com.moomen.movieyou.model.video.VideoTrailerMovie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesViewModel extends ViewModel {

    //Get latest trailer
    public MutableLiveData<MoviesModel> latestTrailerMutableLiveData = new MutableLiveData<>();
    //Get popular movies
    public MutableLiveData<MoviesModel> moviesPopularMutableLiveData = new MutableLiveData<>();
    //Get top movies
    public MutableLiveData<MoviesModel> moviesTopRatedMutableLiveData = new MutableLiveData<>();
    //Get now playing movies
    public MutableLiveData<MoviesModel> moviesNowPlayingMutableLiveData = new MutableLiveData<>();
    //Get detail movie by id
    public MutableLiveData<DetailMovie> detailMovieMutableLiveData = new MutableLiveData<>();
    //Get images movie by id
    public MutableLiveData<ImageMovies> imageMovieMutableLiveData = new MutableLiveData<>();
    //Get video trailer movie by id
    public MutableLiveData<VideoTrailerMovie> videoTrailerMovieMutableLiveData = new MutableLiveData<>();
    //Get similar movies
    public MutableLiveData<MoviesModel> similarMovieMutableLiveData = new MutableLiveData<>();
    //Get Movies Of type by "Genres"
    public MutableLiveData<MoviesModel> moviesOfTypeMutableLiveData = new MutableLiveData<>();
    //Search Movies Or TV
    public MutableLiveData<MoviesModel> searchOnMoviesOrTVMutableLiveData = new MutableLiveData<>();
    //Get title movie by id
    public MutableLiveData<TitleMovie> titleMovieMutableLiveData = new MutableLiveData<>();
    //Get credits movie
    public MutableLiveData<CreditsModel> creditsMovieMutableLiveData = new MutableLiveData<>();
    //Get recommendations movie
    public MutableLiveData<MoviesModel> recommendationsMovieMutableLiveData = new MutableLiveData<>();
    /**
     * TV Part
     */
    //Get airing today tv
    public MutableLiveData<MoviesModel> airingTodayTvMutableLiveData = new MutableLiveData<>();
    //Get on the air tv
    public MutableLiveData<MoviesModel> onTheAirTvMutableLiveData = new MutableLiveData<>();
    //Get popular tv
    public MutableLiveData<MoviesModel> popularTvMutableLiveData = new MutableLiveData<>();
    //Get top rated tv
    public MutableLiveData<MoviesModel> topRatedTvMutableLiveData = new MutableLiveData<>();
    //Get detail TV by id
    public MutableLiveData<DetailTV> detailTVMutableLiveData = new MutableLiveData<>();
    //Get images TV by id
    public MutableLiveData<ImageMovies> imageTVMutableLiveData = new MutableLiveData<>();
    //Get video trailer TV by id
    public MutableLiveData<VideoTrailerMovie> videoTrailerTVMutableLiveData = new MutableLiveData<>();
    //Get Episode TV
    public MutableLiveData<DetailTVEpisode> episodeTVMutableLiveData = new MutableLiveData<>();
    //Get similar TV
    public MutableLiveData<MoviesModel> similarTVMutableLiveData = new MutableLiveData<>();
    //Get TV Of type by "Genres"
    public MutableLiveData<MoviesModel> tVOfTypeMutableLiveData = new MutableLiveData<>();
    //Get credits TV
    public MutableLiveData<CreditsModel> creditsTVMutableLiveData = new MutableLiveData<>();
    //Get recommendations TV
    public MutableLiveData<MoviesModel> recommendationsTVMutableLiveData = new MutableLiveData<>();
    /**
     * Credit part
     **/
    public MutableLiveData<OverviewCreditModel> creditMutableLiveData = new MutableLiveData<>();
    /**
     * Part Watch List
     **/
    //Get detail movie by id using MovieResult
    public MutableLiveData<MoviesResults> moviesResultsMutableLiveData = new MutableLiveData<>();
    //Get detail movie by id using MovieResult
    public MutableLiveData<MoviesResults> tvResultsMutableLiveData = new MutableLiveData<>();
    /**
     * Part hom fragment
     **/
    //Get Trending
    public MutableLiveData<MoviesModel> getTrendingMutableLiveData = new MutableLiveData<>();
    //Discover media with geners
    public MutableLiveData<MoviesModel> discoverMovieWithGenresMutableLiveData = new MutableLiveData<>();
    //Discover TV with geners
    public MutableLiveData<MoviesModel> discoverTVWithGenresMutableLiveData = new MutableLiveData<>();
    //configuration languages
    public MutableLiveData<ArrayList<ConfigurationResults>> configurationLanguagesMutableLiveData = new MutableLiveData<>();

    public void getLatestTrailer(String page) {
        MoviesClient.getINSTANCE().getLatestTrailer(page).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                Log.d("onResponse", response.toString());

                latestTrailerMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                t.printStackTrace();
                Log.d("onFailure", t.getMessage());
            }
        });
    }

    public void getMoviesPopular(String page) {
        MoviesClient.getINSTANCE().getMoviesPopular(page).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                moviesPopularMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getMoviesTopRated(String page) {
        MoviesClient.getINSTANCE().getMoviesTopRated(page).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                moviesTopRatedMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getMoviesNowPlaying(String page) {
        MoviesClient.getINSTANCE().getMoviesNowPlaying(page).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                moviesNowPlayingMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getDetailMovie(String idMovie) {
        MoviesClient.getINSTANCE().getDetailMovieById(idMovie).enqueue(new Callback<DetailMovie>() {
            @Override
            public void onResponse(Call<DetailMovie> call, Response<DetailMovie> response) {
                detailMovieMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<DetailMovie> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getImagesMovie(String idMovie) {
        MoviesClient.getINSTANCE().getImagesMovieById(idMovie).enqueue(new Callback<ImageMovies>() {
            @Override
            public void onResponse(Call<ImageMovies> call, Response<ImageMovies> response) {
                imageMovieMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ImageMovies> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getVideoTrailerMovie(String idMovie) {
        MoviesClient.getINSTANCE().getVideoTrailerMovieById(idMovie).enqueue(new Callback<VideoTrailerMovie>() {
            @Override
            public void onResponse(Call<VideoTrailerMovie> call, Response<VideoTrailerMovie> response) {
                videoTrailerMovieMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<VideoTrailerMovie> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getSimilarMovies(String idMovie) {
        MoviesClient.getINSTANCE().getSimilarMovies(idMovie).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                similarMovieMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getMoviesOfType(String sortBy, String genresId, String page) {
        MoviesClient.getINSTANCE().getMoviesOfType(sortBy, genresId, page).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                moviesOfTypeMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void searchOnMoviesOrTV(String textSearch, String page) {
        MoviesClient.getINSTANCE().searchOnMoviesOrTV(textSearch, page).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                searchOnMoviesOrTVMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void geTitleMovie(String idMovie) {
        MoviesClient.getINSTANCE().getTitleMovieById(idMovie).enqueue(new Callback<TitleMovie>() {
            @Override
            public void onResponse(Call<TitleMovie> call, Response<TitleMovie> response) {
                titleMovieMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TitleMovie> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getCreditsMovie(String idMovies) {
        MoviesClient.getINSTANCE().getCreditsMovie(idMovies).enqueue(new Callback<CreditsModel>() {
            @Override
            public void onResponse(Call<CreditsModel> call, Response<CreditsModel> response) {
                creditsMovieMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CreditsModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getRecommendationsMovie(String idMovie) {
        MoviesClient.getINSTANCE().getRecommendationsMovie(idMovie).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                recommendationsMovieMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getTvAiringToday(String page) {
        MoviesClient.getINSTANCE().getTvAiringToday(page).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                airingTodayTvMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {

            }
        });
    }

    public void getTvOnTheAir(String page) {
        MoviesClient.getINSTANCE().getTvOnTheAir(page).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                onTheAirTvMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getPopularTv(String page) {
        MoviesClient.getINSTANCE().getPopularTv(page).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                popularTvMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getTopRatedTv(String page) {
        MoviesClient.getINSTANCE().getTopRatedTv(page).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                topRatedTvMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getDetailTV(String idTV) {
        MoviesClient.getINSTANCE().getDetailTVById(idTV).enqueue(new Callback<DetailTV>() {
            @Override
            public void onResponse(Call<DetailTV> call, Response<DetailTV> response) {
                detailTVMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<DetailTV> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getImagesTV(String idTV) {
        MoviesClient.getINSTANCE().getImagesTVById(idTV).enqueue(new Callback<ImageMovies>() {
            @Override
            public void onResponse(Call<ImageMovies> call, Response<ImageMovies> response) {
                imageTVMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ImageMovies> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getVideoTrailerTV(String idTV) {
        MoviesClient.getINSTANCE().getVideoTrailerTVById(idTV).enqueue(new Callback<VideoTrailerMovie>() {
            @Override
            public void onResponse(Call<VideoTrailerMovie> call, Response<VideoTrailerMovie> response) {
                videoTrailerTVMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<VideoTrailerMovie> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getEpisodeTV(String idTV, String season_number, String episode_number) {
        MoviesClient.getINSTANCE().getEpisodeTV(idTV, season_number, episode_number).enqueue(new Callback<DetailTVEpisode>() {
            @Override
            public void onResponse(Call<DetailTVEpisode> call, Response<DetailTVEpisode> response) {
                episodeTVMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<DetailTVEpisode> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getSimilarTV(String idTV) {
        MoviesClient.getINSTANCE().getSimilarTV(idTV).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                similarTVMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getTVOfType(String sortBy, String genresId, String page) {
        MoviesClient.getINSTANCE().getTVOfType(sortBy, genresId, page).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                tVOfTypeMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getCreditsTV(String idTV) {
        MoviesClient.getINSTANCE().getCreditsTV(idTV).enqueue(new Callback<CreditsModel>() {
            @Override
            public void onResponse(Call<CreditsModel> call, Response<CreditsModel> response) {
                creditsTVMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CreditsModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getRecommendationsTV(String idTV) {
        MoviesClient.getINSTANCE().getRecommendationsTV(idTV).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                recommendationsTVMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getCredit(String idTV) {
        MoviesClient.getINSTANCE().getCredit(idTV).enqueue(new Callback<OverviewCreditModel>() {
            @Override
            public void onResponse(Call<OverviewCreditModel> call, Response<OverviewCreditModel> response) {
                creditMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<OverviewCreditModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getMovieResultsById(String idMovie) {
        MoviesClient.getINSTANCE().getMovieResultsById(idMovie).enqueue(new Callback<MoviesResults>() {
            @Override
            public void onResponse(Call<MoviesResults> call, Response<MoviesResults> response) {
                moviesResultsMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesResults> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getTVResultsById(String idTV) {
        MoviesClient.getINSTANCE().getTVResultsById(idTV).enqueue(new Callback<MoviesResults>() {
            @Override
            public void onResponse(Call<MoviesResults> call, Response<MoviesResults> response) {
                tvResultsMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesResults> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void getTrendingAllToDay(String page) {
        MoviesClient.getINSTANCE().getTrendingAllToDay(page).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                getTrendingMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void discoverMovieWithGeners(String page, String gneres, String sort_by, String with_original_language, String year, String vote_average_gte, String include_video) {
        MoviesClient.getINSTANCE().discoverMovieWithGeners(page, gneres, sort_by, with_original_language, year, vote_average_gte, include_video).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                discoverMovieWithGenresMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void discoverTVWithGeners(String gneres, String page, String sort_by, String with_original_language, String year, String vote_average_gte) {
        MoviesClient.getINSTANCE().discoverTVWithGeners(gneres, page, sort_by, with_original_language, year, vote_average_gte).enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                discoverTVWithGenresMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d("e2", t.getMessage());
            }
        });
    }

    public void configurationLanguages() {
        MoviesClient.getINSTANCE().configurationLanguages().enqueue(new Callback<ArrayList<ConfigurationResults>>() {
            @Override
            public void onResponse(Call<ArrayList<ConfigurationResults>> call, Response<ArrayList<ConfigurationResults>> response) {
                configurationLanguagesMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ConfigurationResults>> call, Throwable t) {

            }
        });
    }
}
