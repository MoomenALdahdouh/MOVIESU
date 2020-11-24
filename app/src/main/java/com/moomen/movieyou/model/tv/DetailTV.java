package com.moomen.movieyou.model.tv;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DetailTV {
    @SerializedName("original_language")
    private String original_language;
    @SerializedName("number_of_episodes")
    private int number_of_episodes;
    @SerializedName("networks")
    private ArrayList<DetailTVNetworks> networks;
    @SerializedName("type")
    private String type;
    @SerializedName("backdrop_path")
    private String backdrop_path;
    @SerializedName("genres")
    private ArrayList<DetailTVGenres> genres;
    @SerializedName("popularity")
    private double popularity;
    @SerializedName("id")
    private int id;
    @SerializedName("number_of_seasons")
    private int number_of_seasons;
    @SerializedName("vote_count")
    private int vote_count;
    @SerializedName("first_air_date")
    private String first_air_date;
    @SerializedName("overview")
    private String overview;
    @SerializedName("seasons")
    private ArrayList<DetailTVSeasons> seasons;
    @SerializedName("languages")
    private ArrayList<String> languages;
    @SerializedName("created_by")
    private ArrayList<DetailTVCreated_by> created_by;
    @SerializedName("poster_path")
    private String poster_path;
    @SerializedName("origin_country")
    private ArrayList<String> origin_country;
    @SerializedName("production_companies")
    private ArrayList<DetailTVProduction_companies> production_companies;
    @SerializedName("original_name")
    private String original_name;
    @SerializedName("vote_average")
    private double vote_average;
    @SerializedName("name")
    private String name;
    @SerializedName("episode_run_time")
    private ArrayList<Integer> episode_run_time;
    @SerializedName("next_episode_to_air")
    private Object next_episode_to_air;
    @SerializedName("in_production")
    private boolean in_production;
    @SerializedName("last_air_date")
    private String last_air_date;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("status")
    private String status;


    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public ArrayList<DetailTVNetworks> getNetworks() {
        return networks;
    }

    public void setNetworks(ArrayList<DetailTVNetworks> networks) {
        this.networks = networks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public ArrayList<DetailTVGenres> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<DetailTVGenres> genres) {
        this.genres = genres;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public ArrayList<DetailTVSeasons> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<DetailTVSeasons> seasons) {
        this.seasons = seasons;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public ArrayList<DetailTVCreated_by> getCreated_by() {
        return created_by;
    }

    public void setCreated_by(ArrayList<DetailTVCreated_by> created_by) {
        this.created_by = created_by;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public ArrayList<String> getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(ArrayList<String> origin_country) {
        this.origin_country = origin_country;
    }

    public ArrayList<DetailTVProduction_companies> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(ArrayList<DetailTVProduction_companies> production_companies) {
        this.production_companies = production_companies;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getEpisode_run_time() {
        return episode_run_time;
    }

    public void setEpisode_run_time(ArrayList<Integer> episode_run_time) {
        this.episode_run_time = episode_run_time;
    }

    public Object getNext_episode_to_air() {
        return next_episode_to_air;
    }

    public void setNext_episode_to_air(Object next_episode_to_air) {
        this.next_episode_to_air = next_episode_to_air;
    }

    public boolean isIn_production() {
        return in_production;
    }

    public void setIn_production(boolean in_production) {
        this.in_production = in_production;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
