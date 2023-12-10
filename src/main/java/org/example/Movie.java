package org.example;

import org.example.enums.Genre;

import java.time.Duration;
import java.util.List;

public class Movie extends Production{

    public Movie(String title, List<String> directors,
                 List<String> actors, List<Genre> genres,
                 List<Rating> ratings, String description,
                 String imageUrl,
                 String runtime, Integer releaseYear) {
        super(title, directors, actors, genres, ratings, description, imageUrl);
        this.runtime = runtime;
        this.releaseYear = releaseYear;
    }

    public Movie(String title, List<String> directors,
                 List<String> actors, List<Genre> genres,
                 List<Rating> ratings, String description,
                 String author, String imageUrl, String runtime,
                 Integer releaseYear) {
        super(title, directors, actors, genres, ratings, description, author, imageUrl);
        this.runtime = runtime;
        this.releaseYear = releaseYear;
    }

    public Movie(String title, List<String> directors,
                 List<String> actors, List<Genre> genres,
                 List<Rating> ratings, String description,
                 Double averageRating, String author, String imageUrl,
                 String runtime, Integer releaseYear) {
        super(title, directors, actors, genres, ratings, description,
                averageRating, author, imageUrl);
        this.runtime = runtime;
        this.releaseYear = releaseYear;
    }

    private String runtime;
    private Integer releaseYear;

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public void displayInfo() {
        String displayMessage = "Title : " + this.getTitle() + "\n" +
                "Directors : " + this.getDirectors().toString() + "\n" +
                "Actors : " + this.getActors().toString() + "\n" +
                "Genres : " + this.getGenres().toString() + "\n" +
                "runtime : " + this.getRuntime().toString() + "\n" +
                "release year : " + this.getReleaseYear();
        System.out.println(displayMessage);
    }
}
