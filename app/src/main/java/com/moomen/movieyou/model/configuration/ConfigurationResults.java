package com.moomen.movieyou.model.configuration;

import com.google.gson.annotations.SerializedName;

public class ConfigurationResults {
    @SerializedName("name")
    private String name;
    @SerializedName("iso_639_1")
    private String iso_639_1;
    @SerializedName("english_name")
    private String english_name;

    public ConfigurationResults(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso_639_1() {
        return this.iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getEnglish_name() {
        return this.english_name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }
}
