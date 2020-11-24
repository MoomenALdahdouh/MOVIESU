package com.moomen.movieyou.model.firebase;

import com.google.gson.annotations.SerializedName;

public class CommentsModel {
    @SerializedName("userName")
    private String userName;
    @SerializedName("date")
    private String date;
    @SerializedName("idMedia")
    private String idMedia;
    @SerializedName("typeMedia")
    private String typeMedia;
    @SerializedName("comment")
    private String comment;

    public CommentsModel() {
    }

    public CommentsModel(String userName, String date, String idMedia, String typeMedia, String comment) {
        this.userName = userName;
        this.date = date;
        this.idMedia = idMedia;
        this.typeMedia = typeMedia;
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdMedia() {
        return idMedia;
    }

    public void setIdMedia(String idMedia) {
        this.idMedia = idMedia;
    }

    public String getTypeMedia() {
        return typeMedia;
    }

    public void setTypeMedia(String typeMedia) {
        this.typeMedia = typeMedia;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
