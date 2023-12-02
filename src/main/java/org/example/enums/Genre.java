package org.example.enums;

public enum Genre {
    Action,
    Adventure,
    Comedy,
    Drama,
    Horror,
    SF,
    Fantasy,
    Romance,
    Mystery,
    Thriller,
    Crime,
    Biography,
    War,
    Cooking;

    public static Genre fromString(String genreString) {
        for (Genre genre : Genre.values()) {
            if (genre.name().equals(genreString)) {
                return genre;
            }
        }
        return null; // or throw an exception if needed
    }

}
