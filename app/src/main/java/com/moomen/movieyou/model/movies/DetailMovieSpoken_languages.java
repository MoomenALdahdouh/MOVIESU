package com.moomen.movieyou.model.movies;

import com.google.gson.annotations.SerializedName;

public class DetailMovieSpoken_languages {
    @SerializedName("name")
    private String name;
    @SerializedName("iso_639_1")
    private String iso_639_1;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso_639_1() {
        return this.iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }
}
