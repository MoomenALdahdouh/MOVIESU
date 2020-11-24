package com.moomen.movieyou.model.credits;

import com.google.gson.annotations.SerializedName;
import com.moomen.movieyou.model.movies.MoviesResults;

import java.util.ArrayList;

public class OverviewCreditModelPerson {
    @SerializedName("gender")
    private int gender;
    @SerializedName("known_for_department")
    private String known_for_department;
    @SerializedName("known_for")
    private ArrayList<MoviesResults> known_for;
    @SerializedName("popularity")
    private double popularity;
    @SerializedName("name")
    private String name;
    @SerializedName("profile_path")
    private String profile_path;
    @SerializedName("id")
    private int id;
    @SerializedName("adult")
    private boolean adult;


    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getKnown_for_department() {
        return this.known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public ArrayList<MoviesResults> getKnown_for() {
        return known_for;
    }

    public void setKnown_for(ArrayList<MoviesResults> known_for) {
        this.known_for = known_for;
    }

    public double getPopularity() {
        return this.popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return this.profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getAdult() {
        return this.adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }
}
