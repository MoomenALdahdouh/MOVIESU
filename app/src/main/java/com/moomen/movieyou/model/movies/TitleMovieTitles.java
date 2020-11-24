package com.moomen.movieyou.model.movies;

import com.google.gson.annotations.SerializedName;

public class TitleMovieTitles {
    @SerializedName("iso_3166_1")
    private String iso_3166_1;
    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private String type;

    public String getIso_3166_1() {
        return this.iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
