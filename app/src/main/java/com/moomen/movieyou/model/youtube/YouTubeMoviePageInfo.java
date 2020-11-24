package com.moomen.movieyou.model.youtube;

public class YouTubeMoviePageInfo {
    private int totalResults;
    private int resultsPerPage;

    public int getTotalResults() {
        return this.totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getResultsPerPage() {
        return this.resultsPerPage;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }
}
