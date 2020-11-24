package com.moomen.movieyou.model.image;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ImageMovies {
    @SerializedName("backdrops")
    private ArrayList<ImageMoviesBackdrops> backdrops;
    @SerializedName("posters")
    private ArrayList<ImageMoviesPosters> posters;
    @SerializedName("id")
    private int id;

    public ArrayList<ImageMoviesBackdrops> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(ArrayList<ImageMoviesBackdrops> backdrops) {
        this.backdrops = backdrops;
    }

    public ArrayList<ImageMoviesPosters> getPosters() {
        return posters;
    }

    public void setPosters(ArrayList<ImageMoviesPosters> posters) {
        this.posters = posters;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
