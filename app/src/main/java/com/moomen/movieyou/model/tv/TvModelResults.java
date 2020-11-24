package com.moomen.movieyou.model.tv;

import com.google.gson.annotations.SerializedName;

public class TvModelResults {
    @SerializedName("first_air_date")
    private String first_air_date;
    @SerializedName("overview")
    private String overview;
    @SerializedName("original_language")
    private String original_language;
    @SerializedName("genre_ids")
    private int[] genre_ids;
    @SerializedName("poster_path")
    private String poster_path;
    @SerializedName("origin_country")
    private String[] origin_country;
    @SerializedName("backdrop_path")
    private String backdrop_path;
    @SerializedName("original_name")
    private String original_name;
    @SerializedName("popularity")
    private double popularity;
    @SerializedName("vote_average")
    private double vote_average;
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private int id;
    @SerializedName("vote_count")
    private double vote_count;

    public String getFirst_air_date() {
        return this.first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
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

    public int[] getGenre_ids() {
        return this.genre_ids;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getPoster_path() {
        return this.poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String[] getOrigin_country() {
        return this.origin_country;
    }

    public void setOrigin_country(String[] origin_country) {
        this.origin_country = origin_country;
    }

    public String getBackdrop_path() {
        return this.backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOriginal_name() {
        return this.original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public double getPopularity() {
        return this.popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getVote_average() {
        return this.vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVote_count() {
        return vote_count;
    }

    public void setVote_count(double vote_count) {
        this.vote_count = vote_count;
    }
}
