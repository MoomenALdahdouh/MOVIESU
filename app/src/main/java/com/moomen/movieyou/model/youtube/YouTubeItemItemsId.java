package com.moomen.movieyou.model.youtube;

import com.google.gson.annotations.SerializedName;

public class YouTubeItemItemsId {
    @SerializedName("kind")
    private String kind;
    @SerializedName("videoId")
    private String videoId;

    public String getKind() {
        return this.kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
