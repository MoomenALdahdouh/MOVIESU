package com.moomen.movieyou.client;

import com.moomen.movieyou.BuildConfig;
import com.moomen.movieyou.interfaces.MoviesInterface;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesClient {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static MoviesClient INSTANCE;
    private final MoviesInterface moviesInterface;

    public MoviesClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        moviesInterface = retrofit.create(MoviesInterface.class);

    }

    public static MoviesClient getINSTANCE() {
        if (null == INSTANCE) {
            INSTANCE = new MoviesClient();
        }
        return INSTANCE;
    }

    public Call<MoviesModel> getLatestTrailer(String page) {
        return moviesInterface.getLatestTrailer(BuildConfig.THE_MOBIE_DB_API_TOKEN, page, "release_date.desc");
    }

    public Call<MoviesModel> getMoviesPopular(String page) {
        return moviesInterface.getMoviesPopular(BuildConfig.THE_MOBIE_DB_API_TOKEN, page);
    }

    public Call<MoviesModel> getMoviesTopRated(String page) {
        return moviesInterface.getMoviesTopRated(BuildConfig.THE_MOBIE_DB_API_TOKEN, page);
    }

    public Call<MoviesModel> getMoviesNowPlaying(String page) {
        return moviesInterface.getMoviesNowPlaying(BuildConfig.THE_MOBIE_DB_API_TOKEN, page);
    }

    //Get detail movie by id
    public Call<DetailMovie> getDetailMovieById(String idMovie) {
        return moviesInterface.getDetailMovieById(idMovie, BuildConfig.THE_MOBIE_DB_API_TOKEN);
    }

    //Get images movie by id
    public Call<ImageMovies> getImagesMovieById(String idMovie) {
        return moviesInterface.getImagesMovieById(idMovie, BuildConfig.THE_MOBIE_DB_API_TOKEN);
    }

    //Get video trailer by id
    public Call<VideoTrailerMovie> getVideoTrailerMovieById(String idMovie) {
        return moviesInterface.getVideoTrailerMovieById(idMovie, BuildConfig.THE_MOBIE_DB_API_TOKEN);
    }

    //Get similar movies
    public Call<MoviesModel> getSimilarMovies(String idMovie) {
        return moviesInterface.getSimilarMovies(idMovie, BuildConfig.THE_MOBIE_DB_API_TOKEN);
    }

    //Discover "Filter" Movies Of type animation by "Genres"
    public Call<MoviesModel> getMoviesOfType(String sortBy, String genresId, String page) {
        return moviesInterface.getMoviesOfType(BuildConfig.THE_MOBIE_DB_API_TOKEN, sortBy, genresId, page);
    }

    //Get credits movies
    public Call<CreditsModel> getCreditsMovie(String idMovie) {
        return moviesInterface.getCreditsMovie(idMovie, BuildConfig.THE_MOBIE_DB_API_TOKEN);
    }


    //Get recommendations movie
    public Call<MoviesModel> getRecommendationsMovie(String idMovie) {
        return moviesInterface.getRecommendationsMovie(idMovie, BuildConfig.THE_MOBIE_DB_API_TOKEN);
    }


    /**
     * Search part
     */

    //search Movies Or TV
    public Call<MoviesModel> searchOnMoviesOrTV(String typeMovies, String page) {
        return moviesInterface.searchOnMoviesOrTV(BuildConfig.THE_MOBIE_DB_API_TOKEN, typeMovies, page);
    }

    //Get title movie by id
    public Call<TitleMovie> getTitleMovieById(String idMovie) {
        return moviesInterface.getTitleMovieById(idMovie, BuildConfig.THE_MOBIE_DB_API_TOKEN);
    }

    /**
     * Tv Part
     */
    //Get airing today tv
    public Call<MoviesModel> getTvAiringToday(String page) {
        return moviesInterface.getTvAiringToday(BuildConfig.THE_MOBIE_DB_API_TOKEN, page);
    }

    //Get on the air tv
    public Call<MoviesModel> getTvOnTheAir(String page) {
        return moviesInterface.getTvOnTheAir(BuildConfig.THE_MOBIE_DB_API_TOKEN, page);
    }

    //Get popular tv
    public Call<MoviesModel> getPopularTv(String page) {
        return moviesInterface.getPopularTv(BuildConfig.THE_MOBIE_DB_API_TOKEN, page);
    }

    //Get top rated tv
    public Call<MoviesModel> getTopRatedTv(String page) {
        return moviesInterface.getTopRatedTv(BuildConfig.THE_MOBIE_DB_API_TOKEN, page);
    }

    //Get detail tv by id
    public Call<DetailTV> getDetailTVById(String idTV) {
        return moviesInterface.getDetailTVById(idTV, BuildConfig.THE_MOBIE_DB_API_TOKEN);
    }

    //Get images tv by id
    public Call<ImageMovies> getImagesTVById(String idTV) {
        return moviesInterface.getImagesTVById(idTV, BuildConfig.THE_MOBIE_DB_API_TOKEN);
    }

    //Get video trailer tv by id
    public Call<VideoTrailerMovie> getVideoTrailerTVById(String idTV) {
        return moviesInterface.getVideoTrailerTVById(idTV, BuildConfig.THE_MOBIE_DB_API_TOKEN);
    }

    //Get Episode TV
    public Call<DetailTVEpisode> getEpisodeTV(String idTV, String season_number, String episode_number) {
        return moviesInterface.getEpisodeTV(idTV, season_number, episode_number, "release_date.asc", BuildConfig.THE_MOBIE_DB_API_TOKEN);
    }

    //Get similar tv
    public Call<MoviesModel> getSimilarTV(String idTV) {
        return moviesInterface.getSimilarTV(idTV, BuildConfig.THE_MOBIE_DB_API_TOKEN);
    }

    //Discover TV Of type animation by "Genres"
    public Call<MoviesModel> getTVOfType(String sortBy, String genresId, String page) {
        return moviesInterface.getTVOfType(BuildConfig.THE_MOBIE_DB_API_TOKEN, sortBy, genresId, page);
    }

    //Get credits tv
    public Call<CreditsModel> getCreditsTV(String idTV) {
        return moviesInterface.getCreditsTV(idTV, BuildConfig.THE_MOBIE_DB_API_TOKEN);
    }

    //Get recommendations tv
    public Call<MoviesModel> getRecommendationsTV(String idTV) {
        return moviesInterface.getRecommendationsTV(idTV, BuildConfig.THE_MOBIE_DB_API_TOKEN);
    }

    /**
     * Credit part
     **/
    //Get credit
    public Call<OverviewCreditModel> getCredit(String idCredit) {
        return moviesInterface.getCredit(idCredit, BuildConfig.THE_MOBIE_DB_API_TOKEN);
    }

    /**
     * Part Watch list
     **/
    //Get detail movie by id using moviesResult
    public Call<MoviesResults> getMovieResultsById(String idMovie) {
        return moviesInterface.getMovieResultsById(idMovie, BuildConfig.THE_MOBIE_DB_API_TOKEN);
    }

    //Get detail movie by id using moviesResult
    public Call<MoviesResults> getTVResultsById(String idTV) {
        return moviesInterface.getTVResultsById(idTV, BuildConfig.THE_MOBIE_DB_API_TOKEN);
    }

    /**
     * Part hom fragment
     **/
    //Get Trending
    public Call<MoviesModel> getTrendingAllToDay(String page) {
        return moviesInterface.getTrendingAllToDay(BuildConfig.THE_MOBIE_DB_API_TOKEN, page);
    }

    //Discover Movie with geners
    public Call<MoviesModel> discoverMovieWithGeners(String page, String gneres, String sort_by, String with_original_language, String year, String vote_average_gte, String include_video) {
        return moviesInterface.discoverMovieWithGeners(BuildConfig.THE_MOBIE_DB_API_TOKEN, page, gneres, sort_by, with_original_language, year, vote_average_gte, include_video);//vote_average.desc//"popularity.desc"
    }

    //Discover TV with geners
    public Call<MoviesModel> discoverTVWithGeners(String page, String gneres, String sort_by, String with_original_language, String year, String vote_average_gte) {
        return moviesInterface.discoverTVWithGeners(BuildConfig.THE_MOBIE_DB_API_TOKEN, page, gneres, sort_by, with_original_language, year, vote_average_gte);//vote_average.desc
    }

    //configuration languages
    public Call<ArrayList<ConfigurationResults>> configurationLanguages() {
        return moviesInterface.configurationLanguages(BuildConfig.THE_MOBIE_DB_API_TOKEN);
    }
}
