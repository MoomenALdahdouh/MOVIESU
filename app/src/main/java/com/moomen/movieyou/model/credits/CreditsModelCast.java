package com.moomen.movieyou.model.credits;

import com.google.gson.annotations.SerializedName;

public class CreditsModelCast {
    @SerializedName("cast_id")
    private int cast_id;
    @SerializedName("character")
    private String character;
    @SerializedName("gender")
    private int gender;
    @SerializedName("credit_id")
    private String credit_id;
    @SerializedName("name")
    private String name;
    @SerializedName("profile_path")
    private String profile_path;
    @SerializedName("id")
    private int id;
    @SerializedName("order")
    private int order;
    @SerializedName("job")
    private String job;

    public int getCast_id() {
        return this.cast_id;
    }

    public void setCast_id(int cast_id) {
        this.cast_id = cast_id;
    }

    public String getCharacter() {
        return this.character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getCredit_id() {
        return this.credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return "https://image.tmdb.org/t/p/w154/" + this.profile_path;
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

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
