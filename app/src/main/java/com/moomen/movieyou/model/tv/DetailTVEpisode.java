package com.moomen.movieyou.model.tv;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DetailTVEpisode {
    @SerializedName("production_code")
    private String production_code;
    @SerializedName("air_date")
    private String air_date;
    @SerializedName("overview")
    private String overview;
    @SerializedName("episode_number")
    private int episode_number;
    @SerializedName("vote_average")
    private double vote_average;
    @SerializedName("name")
    private String name;
    @SerializedName("season_number")
    private int season_number;
    @SerializedName("id")
    private int id;
    @SerializedName("still_path")
    private String still_path;
    @SerializedName("vote_count")
    private int vote_count;
    @SerializedName("crew")
    private ArrayList<DetailTVEpisodeCrew> crew;
    @SerializedName("guest_stars")
    private ArrayList<DetailTVEpisodeGuest_stars> guest_stars;

    public DetailTVEpisode() {
    }

    public DetailTVEpisode(String air_date, String overview, int episode_number, double vote_average, String name, int season_number, int id, String still_path) {
        this.air_date = air_date;
        this.overview = overview;
        this.episode_number = episode_number;
        this.vote_average = vote_average;
        this.name = name;
        this.season_number = season_number;
        this.id = id;
        this.still_path = still_path;
    }

    public String getProduction_code() {
        return this.production_code;
    }

    public void setProduction_code(String production_code) {
        this.production_code = production_code;
    }

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

    public int getEpisode_number() {
        return this.episode_number;
    }

    public void setEpisode_number(int episode_number) {
        this.episode_number = episode_number;
    }

    public double getVote_average() {
        return this.vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
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

    public String getStill_path() {
        return "https://image.tmdb.org/t/p/w780/" + this.still_path;
    }

    public void setStill_path(String still_path) {
        this.still_path = still_path;
    }

    public int getVote_count() {
        return this.vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public ArrayList<DetailTVEpisodeCrew> getCrew() {
        return crew;
    }

    public void setCrew(ArrayList<DetailTVEpisodeCrew> crew) {
        this.crew = crew;
    }

    public ArrayList<DetailTVEpisodeGuest_stars> getGuest_stars() {
        return guest_stars;
    }

    public void setGuest_stars(ArrayList<DetailTVEpisodeGuest_stars> guest_stars) {
        this.guest_stars = guest_stars;
    }
}
