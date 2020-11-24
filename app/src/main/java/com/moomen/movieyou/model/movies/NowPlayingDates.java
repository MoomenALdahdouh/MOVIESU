package com.moomen.movieyou.model.movies;

import com.google.gson.annotations.SerializedName;

public class NowPlayingDates {
    @SerializedName("maximum")
    private String maximum;
    @SerializedName("minimum")
    private String minimum;

    public String getMaximum() {
        return this.maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getMinimum() {
        return this.minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }
}
