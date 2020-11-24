package com.moomen.movieyou.model.movies;

import com.google.gson.annotations.SerializedName;

public class DetailMovieProduction_countries {
    @SerializedName("iso_3166_1")
    private String iso_3166_1;
    @SerializedName("name")
    private String name;

    public String getIso_3166_1() {
        return this.iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
