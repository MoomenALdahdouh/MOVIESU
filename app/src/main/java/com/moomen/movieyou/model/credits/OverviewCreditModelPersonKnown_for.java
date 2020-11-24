package com.moomen.movieyou.model.credits;

import com.moomen.movieyou.model.tv.DetailTVGenres;

import java.util.ArrayList;

public class OverviewCreditModelPersonKnown_for {
    private String overview;
    private String original_language;
    private String original_title;
    private boolean video;
    private String title;
    private ArrayList<DetailTVGenres> genre_ids;
    private String poster_path;
    private String backdrop_path;
    private String media_type;
    private String release_date;
    private double popularity;
    private double vote_average;
    private int id;
    private boolean adult;
    private int vote_count;

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

    public ArrayList<DetailTVGenres> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<DetailTVGenres> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getPoster_path() {
        return this.poster_path;
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

    public String getMedia_type() {
        return this.media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getRelease_date() {
        return this.release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
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
}
