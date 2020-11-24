package com.moomen.movieyou.model.movies;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MoviesModel {
    @SerializedName("dates")
    private NowPlayingDates dates;
    @SerializedName("page")
    private int page;
    @SerializedName("total_pages")
    private int total_pages;
    @SerializedName("results")
    private ArrayList<MoviesResults> results;
    @SerializedName("total_results")
    private int total_results;

    public MoviesModel() {
    }

    public NowPlayingDates getDates() {
        return dates;
    }

    public void setDates(NowPlayingDates dates) {
        this.dates = dates;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return this.total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<MoviesResults> getResults() {
        return results;
    }

    public void setResults(ArrayList<MoviesResults> results) {
        this.results = results;
    }

    public int getTotal_results() {
        return this.total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
