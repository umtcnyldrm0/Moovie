package com.pulsetech.moovie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesClass {
    @SerializedName("results")
    @Expose
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public static class Movie {
        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("overview")
        @Expose
        private String description;

        @SerializedName("poster_path")
        @Expose
        private String imageUrl;

        @SerializedName("vote_average")
        @Expose
        private String vote;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getVote(){return vote;}
    }
}
