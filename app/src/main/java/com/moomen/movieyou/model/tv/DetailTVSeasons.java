package com.moomen.movieyou.model.tv;

import com.google.gson.annotations.SerializedName;

public class DetailTVSeasons {
    @SerializedName("air_date")
    private String air_date;
    @SerializedName("overview")
    private String overview;
    @SerializedName("episode_count")
    private int episode_count;
    @SerializedName("name")
    private String name;
    @SerializedName("season_number")
    private int season_number;
    @SerializedName("id")
    private int id;
    @SerializedName("poster_path")
    private String poster_path;

    public String getAir_date() {
        return this.air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getEpisode_count() {
        return this.episode_count;
    }

    public void setEpisode_count(int episode_count) {
        this.episode_count = episode_count;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeason_number() {
        return this.season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster_path() {
        return "https://image.tmdb.org/t/p/w342/" + poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
