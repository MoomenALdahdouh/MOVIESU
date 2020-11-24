package com.moomen.movieyou.model.image;

import com.google.gson.annotations.SerializedName;

public class ImageMoviesBackdrops {
    @SerializedName("aspect_ratio")
    private double aspect_ratio;
    @SerializedName("file_path")
    private String file_path;
    @SerializedName("vote_average")
    private double vote_average;
    @SerializedName("width")
    private int width;
    @SerializedName("iso_639_1")
    private Object iso_639_1;
    @SerializedName("vote_count")
    private double vote_count;
    @SerializedName("height")
    private int height;

    public double getAspect_ratio() {
        return this.aspect_ratio;
    }

    public void setAspect_ratio(double aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }

    public String getFile_path() {
        return "https://image.tmdb.org/t/p/w780/" + this.file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Object getIso_639_1() {
        return this.iso_639_1;
    }

    public void setIso_639_1(Object iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public double getVote_count() {
        return vote_count;
    }

    public void setVote_count(double vote_count) {
        this.vote_count = vote_count;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
