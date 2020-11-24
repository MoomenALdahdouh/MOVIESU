package com.moomen.movieyou.model.credits;

import com.google.gson.annotations.SerializedName;
import com.moomen.movieyou.model.movies.MoviesResults;

public class OverviewCreditModel {
    @SerializedName("media_type")
    private String media_type;
    @SerializedName("person")
    private OverviewCreditModelPerson person;
    @SerializedName("media")
    private MoviesResults media;
    @SerializedName("id")
    private String id;
    @SerializedName("credit_type")
    private String credit_type;
    @SerializedName("department")
    private String department;
    @SerializedName("job")
    private String job;

    public String getMedia_type() {
        return this.media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public OverviewCreditModelPerson getPerson() {
        return person;
    }

    public void setPerson(OverviewCreditModelPerson person) {
        this.person = person;
    }

    public MoviesResults getMedia() {
        return media;
    }

    public void setMedia(MoviesResults media) {
        this.media = media;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCredit_type() {
        return this.credit_type;
    }

    public void setCredit_type(String credit_type) {
        this.credit_type = credit_type;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return this.job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
