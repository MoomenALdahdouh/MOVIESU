package com.moomen.movieyou.model.movies;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TitleMovie {
    @SerializedName("id")
    private int id;
    @SerializedName("titles")
    private ArrayList<TitleMovieTitles> titles;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<TitleMovieTitles> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<TitleMovieTitles> titles) {
        this.titles = titles;
    }
}
