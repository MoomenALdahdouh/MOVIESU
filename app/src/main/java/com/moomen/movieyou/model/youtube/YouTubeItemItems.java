package com.moomen.movieyou.model.youtube;

import com.google.gson.annotations.SerializedName;

public class YouTubeItemItems {
    @SerializedName("snippet")
    private YouTubeItemItemsSnippet snippet;
    @SerializedName("kind")
    private String kind;
    @SerializedName("etag")
    private String etag;
    @SerializedName("id")
    private YouTubeItemItemsId id;

    public YouTubeItemItemsSnippet getSnippet() {
        return this.snippet;
    }

    public void setSnippet(YouTubeItemItemsSnippet snippet) {
        this.snippet = snippet;
    }

    public String getKind() {
        return this.kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return this.etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public YouTubeItemItemsId getId() {
        return this.id;
    }

    public void setId(YouTubeItemItemsId id) {
        this.id = id;
    }
}
