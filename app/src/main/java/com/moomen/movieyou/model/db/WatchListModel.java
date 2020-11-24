package com.moomen.movieyou.model.db;

import com.google.gson.annotations.SerializedName;

public class WatchListModel {
    public static final String TABLE_NAME = "WATCHLIST";
    public static final String COL_PAGE = "PAGE";
    public static final String COL_MOVIE_ID = "MOVIEID";
    public static final String COL_MOVIE_IS_CHECKED = "MOVIEISCHECKED";
    public static final String COL_MOVIE_NAME = "COLMOVIENAME";
    public static final String COL_MEDIA_TYPE = "COLMEDIATYPE";

    //Data base
    public static final String COL_ID = "ID";
    public static final String TABLE_CREATE = " create table " + TABLE_NAME +
            "(" +
            TABLE_NAME + " varchar(30)," +
            COL_PAGE + " varchar(30)," +
            COL_MOVIE_ID + " varchar(30)," +
            COL_MOVIE_IS_CHECKED + " varchar(10)," +
            COL_MOVIE_NAME + " varchar(100)," +
            COL_MEDIA_TYPE + " varchar(10)," +
            COL_ID + " varchar(100) primary key" +
            ")";
    @SerializedName("page")
    private String page;
    @SerializedName("idMovieWatchList")
    private String idMovieWatchList;
    @SerializedName("isChecked")
    private String isChecked;
    @SerializedName("nameMovie")
    private String nameMovie;
    @SerializedName("mediaType")
    private String mediaType;
    @SerializedName("id")
    private String id;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getIdMovieWatchList() {
        return idMovieWatchList;
    }

    public void setIdMovieWatchList(String idMovieWatchList) {
        this.idMovieWatchList = idMovieWatchList;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
