package com.moomen.movieyou.model.movies;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DetailMovie {
    @SerializedName("original_language")
    private String original_language;
    @SerializedName("imdb_id")
    private String imdb_id;
    @SerializedName("video")
    private boolean video;
    @SerializedName("title")
    private String title;
    @SerializedName("backdrop_path")
    private String backdrop_path;
    @SerializedName("revenue")
    private int revenue;
    @SerializedName("genres")
    private ArrayList<DetailMovieGenres> genres;
    @SerializedName("popularity")
    private double popularity;
    @SerializedName("production_countries")
    private DetailMovieProduction_countries[] production_countries;
    @SerializedName("id")
    private int id;
    @SerializedName("vote_count")
    private int vote_count;
    @SerializedName("budget")
    private int budget;
    @SerializedName("overview")
    private String overview;
    @SerializedName("original_title")
    private String original_title;
    @SerializedName("runtime")
    private int runtime;
    @SerializedName("poster_path")
    private String poster_path;
    @SerializedName("spoken_languages")
    private DetailMovieSpoken_languages[] spoken_languages;
    @SerializedName("production_companies")
    private DetailMovieProduction_companies[] production_companies;
    @SerializedName("release_date")
    private String release_date;
    @SerializedName("vote_average")
    private double vote_average;
    @SerializedName("belongs_to_collection")
    private Object belongs_to_collection;
    @SerializedName("tagline")
    private String tagline;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("status")
    private String status;

    public String getOriginal_language() {
        return this.original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getImdb_id() {
        return this.imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
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

    public String getBackdrop_path() {
        return this.backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public int getRevenue() {
        return this.revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public ArrayList<DetailMovieGenres> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<DetailMovieGenres> genres) {
        this.genres = genres;
    }

    public double getPopularity() {
        return this.popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public DetailMovieProduction_countries[] getProduction_countries() {
        return this.production_countries;
    }

    public void setProduction_countries(DetailMovieProduction_countries[] production_countries) {
        this.production_countries = production_countries;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVote_count() {
        return this.vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public int getBudget() {
        return this.budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_title() {
        return this.original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public int getRuntime() {
        return this.runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getPoster_path() {
        return this.poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public DetailMovieSpoken_languages[] getSpoken_languages() {
        return this.spoken_languages;
    }

    public void setSpoken_languages(DetailMovieSpoken_languages[] spoken_languages) {
        this.spoken_languages = spoken_languages;
    }

    public DetailMovieProduction_companies[] getProduction_companies() {
        return this.production_companies;
    }

    public void setProduction_companies(DetailMovieProduction_companies[] production_companies) {
        this.production_companies = production_companies;
    }

    public String getRelease_date() {
        return this.release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getVote_average() {
        return this.vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public Object getBelongs_to_collection() {
        return this.belongs_to_collection;
    }

    public void setBelongs_to_collection(Object belongs_to_collection) {
        this.belongs_to_collection = belongs_to_collection;
    }

    public String getTagline() {
        return this.tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public boolean getAdult() {
        return this.adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getHomepage() {
        return this.homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
