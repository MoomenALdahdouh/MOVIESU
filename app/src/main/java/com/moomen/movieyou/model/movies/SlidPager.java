package com.moomen.movieyou.model.movies;


public class SlidPager {
    private String imageNews;

    public SlidPager(String imageNews, String titleNews, String publishDate, int iconTime) {
        this.imageNews = imageNews;
    }

    public String getImageNews() {
        return imageNews;
    }

    public void setImageNews(String imageNews) {
        this.imageNews = imageNews;
    }

}
