package com.moomen.movieyou.model.movies;

import com.google.gson.annotations.SerializedName;

public class DetailMovieProduction_companies {
    @SerializedName("logo_path")
    private String logo_path;
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private int id;
    @SerializedName("origin_country")
    private String origin_country;

    public String getLogo_path() {
        return this.logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
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

    public String getOrigin_country() {
        return this.origin_country;
    }

    public void setOrigin_country(String origin_country) {
        this.origin_country = origin_country;
    }
}
