package org.example;

import org.example.enums.Genre;

import java.util.List;

public abstract class Production implements Comparable<Object>{



    public Production(String title, List<String> directors,
                      List<String> actors, List<Genre> genres,
                      List<Rating> ratings, String description) {
        this.title = title;
        this.directors = directors;
        this.actors = actors;
        this.genres = genres;
        this.ratings = ratings;
        this.description = description;
        this.author = null;
    }

    public Production(String title, List<String> directors,
                      List<String> actors, List<Genre> genres,
                      List<Rating> ratings, String description,
                      String author) {
        this.title = title;
        this.directors = directors;
        this.actors = actors;
        this.genres = genres;
        this.ratings = ratings;
        this.description = description;
        this.author = author;
    }

    public Production(String title, List<String> directors,
                      List<String> actors, List<Genre> genres,
                      List<Rating> ratings, String description,
                      Double averageRating, String author) {
        this.title = title;
        this.directors = directors;
        this.actors = actors;
        this.genres = genres;
        this.ratings = ratings;
        this.description = description;
        this.averageRating = averageRating;
        this.author = author;
    }

    private String title;
    private List<String> directors;
    private List<String> actors;
    private List<Genre> genres;
    private List<Rating> ratings;
    private String description;
    private Double averageRating;

    private String author;

    public abstract void displayInfo();

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if ( (!(o instanceof Production)) && (!(o instanceof Actor))) {
            throw new ClassCastException("Cannot cast to Production/Actor type");
        }
        if (o instanceof  Production) {
            Production p = (Production) o;
            return this.title.compareTo(p.getTitle());
        }
        //else inseamna ca o este instanta de actor
        Actor a = (Actor) o;
        return this.title.compareTo(a.getName());

    }

    public void addRating(Rating r) {
        ratings.add(r);
        this.setAverageRating(calculateAverageRating());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private Double calculateAverageRating() {
        double sum = 0;
        int count = 0;
        for (Rating rating : ratings) {
            sum = sum + rating.getGrade();
            count = count + 1;
        }
        if (count == 0) {
            return 0.0;
        }
        return sum/count;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
