package com.moomen.movieyou.model.firebase;

import com.google.gson.annotations.SerializedName;

public class VideosModel {
    @SerializedName("page")
    private String page;
    @SerializedName("name")
    private String name;
    @SerializedName("key")
    private String key;
    @SerializedName("type")
    private String type;
    @SerializedName("status")
    private String status;
    @SerializedName("id")
    private String id;

    public VideosModel(String page, String name, String key, String type, String status, String id) {
        this.page = page;
        this.name = name;
        this.key = key;
        this.type = type;
        this.status = status;
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
