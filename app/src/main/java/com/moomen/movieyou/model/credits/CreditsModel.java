package com.moomen.movieyou.model.credits;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CreditsModel {
    @SerializedName("cast")
    private ArrayList<CreditsModelCast> cast;
    @SerializedName("id")
    private int id;
    @SerializedName("crew")
    private ArrayList<CreditsModelCast> crew;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<CreditsModelCast> getCast() {
        return cast;
    }

    public void setCast(ArrayList<CreditsModelCast> cast) {
        this.cast = cast;
    }

    public ArrayList<CreditsModelCast> getCrew() {
        return crew;
    }

    public void setCrew(ArrayList<CreditsModelCast> crew) {
        this.crew = crew;
    }
}
