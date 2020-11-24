package com.moomen.movieyou.interfaces;

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
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesInterface {
    @GET("movie/upcoming")
    Call<MoviesModel> getLatestTrailer(@Query("api_key") String api_key, @Query("page") String page, @Query("sort_by") String release_date);

    @GET("movie/popular")
    Call<MoviesModel> getMoviesPopular(@Query("api_key") String api_key, @Query("page") String page);

    @GET("movie/top_rated")
    Call<MoviesModel> getMoviesTopRated(@Query("api_key") String api_key, @Query("page") String page);

    @GET("movie/now_playing")
    Call<MoviesModel> getMoviesNowPlaying(@Query("api_key") String api_key, @Query("page") String page);

    //Get detail movie by id
    @GET("movie/{movie_id}")
    Call<DetailMovie> getDetailMovieById(@Path("movie_id") String movie_id, @Query("api_key") String api_key);

    //Get Images movie by id
    @GET("movie/{movie_id}/images")
    Call<ImageMovies> getImagesMovieById(@Path("movie_id") String movie_id, @Query("api_key") String api_key);

    //Get video trailer movie by id
    @GET("movie/{movie_id}/videos")
    Call<VideoTrailerMovie> getVideoTrailerMovieById(@Path("movie_id") String movie_id, @Query("api_key") String api_key);

    //Get similar movies
    @GET("movie/{movie_id}/similar")
    Call<MoviesModel> getSimilarMovies(@Path("movie_id") String movie_id, @Query("api_key") String api_key);

    //Discover "Filter" Movies Of type animation by "Genres"
    @GET("discover/movie")
    Call<MoviesModel> getMoviesOfType(@Query("api_key") String api_key, @Query("sort_by") String sort_by, @Query("with_genres") String with_genres, @Query("page") String page);

    //Search on movie ro tv
    @GET("search/multi")
    Call<MoviesModel> searchOnMoviesOrTV(@Query("api_key") String api_key, @Query("query") String typeMovie, @Query("page") String page);

    //Get title movie by id
    @GET("movie/{movie_id}/alternative_titles")
    Call<TitleMovie> getTitleMovieById(@Path("movie_id") String movie_id, @Query("api_key") String api_key);

    //Get credits movies
    @GET("movie/{movie_id}/credits")
    Call<CreditsModel> getCreditsMovie(@Path("movie_id") String movie_id, @Query("api_key") String api_key);

    //Get recommendations movies
    @GET("movie/{movie_id}/recommendations")
    Call<MoviesModel> getRecommendationsMovie(@Path("movie_id") String movie_id, @Query("api_key") String api_key);

    /**
     * Tv Part
     **/

    //Get airing today tv
    @GET("tv/airing_today")
    Call<MoviesModel> getTvAiringToday(@Query("api_key") String api_key, @Query("page") String page);

    //Get on the air tv
    @GET("tv/on_the_air")
    Call<MoviesModel> getTvOnTheAir(@Query("api_key") String api_key, @Query("page") String page);

    //Get popular tv
    @GET("tv/popular")
    Call<MoviesModel> getPopularTv(@Query("api_key") String api_key, @Query("page") String page);

    //Get top rated tv
    @GET("tv/top_rated")
    Call<MoviesModel> getTopRatedTv(@Query("api_key") String api_key, @Query("page") String page);

    //Get detail TV by id
    @GET("tv/{tv_id}")
    Call<DetailTV> getDetailTVById(@Path("tv_id") String tv_id, @Query("api_key") String api_key);

    //Get Images TV by id
    @GET("tv/{tv_id}/images")
    Call<ImageMovies> getImagesTVById(@Path("tv_id") String tv_id, @Query("api_key") String api_key);

    //Get video trailer TV by id
    @GET("tv/{tv_id}/videos")
    Call<VideoTrailerMovie> getVideoTrailerTVById(@Path("tv_id") String tv_id, @Query("api_key") String api_key);

    //Get Episode TV
    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}")
    Call<DetailTVEpisode> getEpisodeTV(@Path("tv_id") String tv_id, @Path("season_number") String season_number,
                                       @Path("episode_number") String episode_number, @Query("sort_by") String sort_by, @Query("api_key") String api_key);

    //Get similar TV
    @GET("tv/{tv_id}/similar")
    Call<MoviesModel> getSimilarTV(@Path("tv_id") String movie_id, @Query("api_key") String api_key);

    //Discover "Filter" TV Of type animation by "Genres"
    @GET("discover/tv")
    Call<MoviesModel> getTVOfType(@Query("api_key") String api_key, @Query("sort_by") String sort_by, @Query("with_genres") String with_genres, @Query("page") String page);

    //Get credits tv
    @GET("tv/{tv_id}/credits")
    Call<CreditsModel> getCreditsTV(@Path("tv_id") String tv_id, @Query("api_key") String api_key);

    //Get recommendations TV
    @GET("tv/{tv_id}/recommendations")
    Call<MoviesModel> getRecommendationsTV(@Path("tv_id") String tv_id, @Query("api_key") String api_key);

    /**
     * Credit part
     **/
    @GET("credit/{credit_id}")
    Call<OverviewCreditModel> getCredit(@Path("credit_id") String credit_id, @Query("api_key") String api_key);

    /**
     * Part Watch list
     **/
    //Get detail movie by id using moviesResult
    @GET("movie/{movie_id}")
    Call<MoviesResults> getMovieResultsById(@Path("movie_id") String movie_id, @Query("api_key") String api_key);

    @GET("tv/{tv_id}")
    Call<MoviesResults> getTVResultsById(@Path("tv_id") String tv_id, @Query("api_key") String api_key);

    /**
     * Part hom fragment
     **/
    //Get Trending
    @GET("trending/all/day")
    Call<MoviesModel> getTrendingAllToDay(@Query("api_key") String api_key, @Query("page") String page);

    //Discover movie with geners
    @GET("discover/movie")
    Call<MoviesModel> discoverMovieWithGeners(@Query("api_key") String api_key, @Query("page") String page, @Query("with_genres") String with_genres, @Query("sort_by") String sort_by, @Query("with_original_language") String with_original_language, @Query("year") String year, @Query("vote_average.gte") String vote_average_gte, @Query("include_video") String include_video);

    //Discover TV with geners
    @GET("discover/tv")
    Call<MoviesModel> discoverTVWithGeners(@Query("api_key") String api_key, @Query("page") String page, @Query("with_genres") String with_genres, @Query("sort_by") String sort_by, @Query("with_original_language") String with_original_language, @Query("year") String year, @Query("vote_average.gte") String vote_average_gte);

    //configuration languages
    @GET("configuration/languages")
    Call<ArrayList<ConfigurationResults>> configurationLanguages(@Query("api_key") String api_key);


}
