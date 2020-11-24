package com.moomen.movieyou.model.movies;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MoviesResults {
    //@Keep

    @SerializedName("overview")
    private String overview;
    @SerializedName("original_language")
    private String original_language;
    @SerializedName("original_title")
    private String original_title;
    @SerializedName("video")
    private boolean video;
    @SerializedName("title")
    private String title;
    @SerializedName("genre_ids")
    private ArrayList<Integer> genre_ids = new ArrayList<>();
    @SerializedName("poster_path")
    private String poster_path;
    @SerializedName("backdrop_path")
    private String backdrop_path;
    @SerializedName("release_date")
    private String release_date;
    @SerializedName("popularity")
    private double popularity;
    @SerializedName("vote_average")
    private double vote_average;
    @SerializedName("id")
    private int id;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("vote_count")
    private int vote_count;
    //@Exclude
    @SerializedName("isSplshActivity")
    private boolean isSplshActivity;
    //@Exclude
    @SerializedName("name")
    private String name;
    //@Exclude
    @SerializedName("first_air_date")
    private String first_air_date;
    //@Exclude
    @SerializedName("profile_path")
    private String profile_path;
    //@Exclude
    @SerializedName("known_for_department")
    private String known_for_department;
    //@Exclude
    @SerializedName("keyVideo")
    private String keyVideo;


    public MoviesResults() {

    }

    public MoviesResults(String overview, String title, ArrayList<Integer> genre_ids, String poster_path, String release_date, double vote_average) {
        this.overview = overview;
        this.title = title;
        this.genre_ids = genre_ids;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_language() {
        return this.original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return this.original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public boolean getVideo() {
        return this.video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getPoster_path() {
        if (this.poster_path != null)
            if (isSplshActivity())
                return "https://image.tmdb.org/t/p/w780/" + this.poster_path;
            else
                return "https://image.tmdb.org/t/p/w342/" + this.poster_path;
        else
            return null;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return this.backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getRelease_date() {
        return this.release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date
        ;
    }

    public double getPopularity() {
        return this.popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getAdult() {
        return this.adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getVote_count() {
        return this.vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public String getKeyVideo() {
        return keyVideo;
    }

    public void setKeyVideo(String keyVideo) {
        this.keyVideo = keyVideo;
    }

    public boolean isSplshActivity() {
        return isSplshActivity;
    }

    public void setSplshActivity(boolean splshActivity) {
        isSplshActivity = splshActivity;
    }
}

