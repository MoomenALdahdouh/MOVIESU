package com.moomen.movieyou.model.video;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VideoTrailerMovie {
    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private ArrayList<VideoTrailerMovieResults> results;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<VideoTrailerMovieResults> getResults() {
        return results;
    }

    public void setResults(ArrayList<VideoTrailerMovieResults> results) {
        this.results = results;
    }
}
