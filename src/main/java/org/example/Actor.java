package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Actor implements Comparable{

    public Actor(String name, List<Pair> performances, String biography,
                 String imageUrl) {
        this.name = name;
        this.performances = performances;
        this.biography = biography;
        this.author = null;
        this.ratings = new ArrayList<>();
        this.averageRating = 0.0;
        this.imageUrl = imageUrl;
    }

    public Actor(String name, List<Pair> performances,
                 String biography, String author, String imageUrl) {
        this.name = name;
        this.performances = performances;
        this.biography = biography;
        this.author = author;
        this.ratings = new ArrayList<>();
        this.averageRating = 0.0;
        this.imageUrl = imageUrl;
    }

    private String name;
    private List<Pair> performances;
    private String biography;
    private String author;

    private List<Rating> ratings;

    private Double averageRating;

    private String imageUrl;

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }

        if ( (!(o instanceof Production)) && (!(o instanceof Actor))) {
            throw new ClassCastException("Cannot cast to Actor/Production type");
        }
        if (o instanceof Actor) {
            Actor a = (Actor) o;
            return this.name.compareTo(a.getName());
        }
        //else o este instanceof Production
        Production p = (Production) o;
        return this.name.compareTo(p.getTitle());
    }

    public void addPerformance(String title, String type) {
        if (performances == null) {
            performances = new ArrayList<>();
        }
        Pair newPerformance = new Pair(title, type);
        performances.add(newPerformance);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pair> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Pair> performances) {
        this.performances = performances;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Double calculateAverageRating() {
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

    public void addRating(Rating r) {
        ratings.add(r);
        this.setAverageRating(calculateAverageRating());
    }

    public class Pair{

        public Pair(String title, String type) {
            this.title = title;
            this.type = type;
        }

        private String title;
        private String type;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "title='" + title + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
}
