package com.moomen.movieyou.model.video;

import com.google.gson.annotations.SerializedName;
import com.moomen.movieyou.model.movies.MoviesResults;

public class VideoTrailerMovieResults {
    @SerializedName("site")
    private String site;
    @SerializedName("size")
    private int size;
    @SerializedName("iso_3166_1")
    private String iso_3166_1;
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private String id;
    @SerializedName("type")
    private String type;
    @SerializedName("iso_639_1")
    private String iso_639_1;
    @SerializedName("key")
    private String key;
    //
    @SerializedName("page")
    private String page;
    @SerializedName("status")
    private String status;
    @SerializedName("mediaType")
    private String mediaType;
    @SerializedName("idMedia")
    private String idMedia;
    @SerializedName("moviesResults")
    private MoviesResults moviesResults;


    public VideoTrailerMovieResults() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSite() {
        return this.site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getIso_3166_1() {
        return this.iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIso_639_1() {
        return this.iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getIdMedia() {
        return idMedia;
    }

    public void setIdMedia(String idMedia) {
        this.idMedia = idMedia;
    }

    public MoviesResults getMoviesResults() {
        return moviesResults;
    }

    public void setMoviesResults(MoviesResults moviesResults) {
        this.moviesResults = moviesResults;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
