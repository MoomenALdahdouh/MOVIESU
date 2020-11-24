package com.moomen.movieyou.model.youtube;

import com.google.gson.annotations.SerializedName;

public class YouTubeItemItemsSnippet {
    @SerializedName("publishTime")
    private String publishTime;
    @SerializedName("publishedAt")
    private String publishedAt;
    @SerializedName("description")
    private String description;
    @SerializedName("title")
    private String title;
    @SerializedName("thumbnails")
    private YouTubeItemItemsSnippetThumbnails thumbnails;
    @SerializedName("channelId")
    private String channelId;
    @SerializedName("channelTitle")
    private String channelTitle;
    @SerializedName("liveBroadcastContent")
    private String liveBroadcastContent;

    public String getPublishTime() {
        return this.publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublishedAt() {
        return this.publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public YouTubeItemItemsSnippetThumbnails getThumbnails() {
        return this.thumbnails;
    }

    public void setThumbnails(YouTubeItemItemsSnippetThumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelTitle() {
        return this.channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getLiveBroadcastContent() {
        return this.liveBroadcastContent;
    }

    public void setLiveBroadcastContent(String liveBroadcastContent) {
        this.liveBroadcastContent = liveBroadcastContent;
    }
}
