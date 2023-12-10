package org.example;

import org.example.enums.Genre;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class Series extends Production{

    public Series(String title, List<String> directors,
                  List<String> actors, List<Genre> genres,
                  List<Rating> ratings, String description,
                  String imageUrl, Integer releaseYear,
                  Map<String, List<Episode>> seasonEpisodes) {
        super(title, directors, actors, genres, ratings, description, imageUrl);
        this.releaseYear = releaseYear;
        this.seasonEpisodes = seasonEpisodes;
    }

    public Series(String title, List<String> directors,
                  List<String> actors, List<Genre> genres,
                  List<Rating> ratings, String description,
                  String author, String imageUrl, Integer releaseYear,
                  Map<String, List<Episode>> seasonEpisodes) {
        super(title, directors, actors, genres, ratings, description, author, imageUrl);
        this.releaseYear = releaseYear;
        this.seasonEpisodes = seasonEpisodes;
    }

    public Series(String title, List<String> directors,
                  List<String> actors, List<Genre> genres,
                  List<Rating> ratings, String description,
                  Double averageRating, String author, String imageUrl,
                  Integer releaseYear, Map<String, List<Episode>> seasonEpisodes) {
        super(title, directors, actors, genres, ratings,
                description, averageRating, author, imageUrl);
        this.releaseYear = releaseYear;
        this.seasonEpisodes = seasonEpisodes;
    }

    private Integer releaseYear;
    private Map<String, List<Episode>> seasonEpisodes;

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }


    public Map<String, List<Episode>> getSeasonEpisodes() {
        return seasonEpisodes;
    }

    public void setSeasonEpisodes(Map<String, List<Episode>> seasonEpisodes) {
        this.seasonEpisodes = seasonEpisodes;
    }

    @Override
    public void displayInfo() {
        String displayMessage = "Title : " + this.getTitle() + "\n" +
                "Directors : " + this.getDirectors().toString() + "\n" +
                "Actors : " + this.getActors().toString() + "\n" +
                "Genres : " + this.getGenres().toString() + "\n" +
                "release year : " + this.getReleaseYear() + "\n" +
                "episodes : " + this.getSeasonEpisodes().toString();
        System.out.println(displayMessage);
    }
}
