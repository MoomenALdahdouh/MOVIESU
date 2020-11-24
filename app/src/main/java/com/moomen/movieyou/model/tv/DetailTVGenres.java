package com.moomen.movieyou.model.tv;

import com.google.gson.annotations.SerializedName;

public class DetailTVGenres {
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private int id;

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
}
