package com.example.saurabhs.moviesapp;

import java.io.Serializable;

/**
 * Created by SaurabhS on 7/26/2016.
 */
public class MovieDetailsVO implements Serializable{


    private String moviePosterpath;

    private String movieReleaseDate;

    private String movieTitle;

    private String movieOverview;

    private int movieId;

    private double movieRating;

    public String getMoviePosterpath() {
        return moviePosterpath;
    }

    public void setMoviePosterpath(String moviePosterpath) {
        this.moviePosterpath = moviePosterpath;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public double getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(double movieRating) {
        this.movieRating = movieRating;
    }

    public MovieDetailsVO(String moviePosterpath, String movieReleaseDate, String movieTitle, String movieOverview, int movieId, Double movieRating) {
        this.moviePosterpath = moviePosterpath;
        this.movieReleaseDate = movieReleaseDate;
        this.movieTitle = movieTitle;
        this.movieOverview = movieOverview;
        this.movieId = movieId;
        this.movieRating = movieRating;
    }

    @Override
    public String toString() {
        return "MovieDetailsVO{" +
                "moviePosterpath='" + moviePosterpath + '\'' +
                ", movieReleaseDate='" + movieReleaseDate + '\'' +
                ", movieTitle='" + movieTitle + '\'' +
                ", movieOverview='" + movieOverview + '\'' +
                ", movieId=" + movieId +
                ", movieRating=" + movieRating +
                '}';
    }
}
