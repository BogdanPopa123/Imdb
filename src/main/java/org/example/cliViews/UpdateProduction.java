package org.example.cliViews;

import org.example.*;
import org.example.enums.Genre;

import java.util.*;

public class UpdateProduction {
    public static void show(){
        System.out.println();
        System.out.println("Enter the name of the production"
                + " you want to update: ");

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        Object object = HomeView.fetch(command);

        if (object == null || object instanceof Actor) {
            System.out.println();
            System.out.println("No production with the name " +
                    command + " has been found");
            System.out.println();
            HomeView.show();
        } else if (object instanceof Movie) {
            Movie movie = (Movie) object;
            updateMovie(movie);
        } else if (object instanceof Series) {
            Series series = (Series) object;
            updateSeries(series);
        }

    }


    private static void updateMovie(Movie oldMovie){
        System.out.println();
        System.out.println("Update the movie " + oldMovie.getTitle());
        Scanner scanner = new Scanner(System.in);
        System.out.println("Plot of new movie: ");
        String plot = scanner.nextLine();
        Movie movie = null;

        try{
            System.out.println("Enter number of directors: ");
            String stringNumDirectors = scanner.nextLine();
            int numDirectors = Integer.parseInt(stringNumDirectors);
            List<String> directors = new ArrayList<>();
            for (int i = 0; i < numDirectors; i++) {
                System.out.print("Enter director name: ");
                String director = scanner.nextLine();
                directors.add(director);
            }


            System.out.println("Enter number of actors: ");
            String stringNumActors = scanner.nextLine();
            int numActors = Integer.parseInt(stringNumActors);
            List<String> actors = new ArrayList<>();
            for (int i = 0; i < numActors; i++) {
                System.out.print("Enter actor name: ");
                String actor = scanner.nextLine();
                actors.add(actor);
            }

            System.out.println("Enter number of genres: ");
            String stringNumGenres = scanner.nextLine();
            int numGenres = Integer.parseInt(stringNumGenres);
            List<Genre> genres = new ArrayList<>();
            for (int i = 0; i < numGenres; i++) {
                System.out.print("Enter genre name: ");
                String genreString = scanner.nextLine();
                Genre genre = Genre.fromString(genreString);
                if (genre != null) {
                    genres.add(genre);
                } else {
                    System.out.println("Not a valid genre: " + genreString);
                }
            }

            System.out.println("Enter number of minutes: ");
            String minutes = scanner.nextLine();
            Integer.parseInt(minutes); // sa ma asigur ca userul a introdus int
            String duration = "" + minutes + " minutes";

            System.out.println("Enter release year: ");
            String yearString = scanner.nextLine();
            Integer releaseYear = Integer.parseInt(yearString);

            movie = new Movie(oldMovie.getTitle(), directors, actors, genres,
                    new ArrayList<>(), plot, LoggedUser.currentUser.getUsername(),
                    null, duration, releaseYear);

            if (LoggedUser.currentUser instanceof Staff
                    && movie != null) {
                Staff staff = (Staff) LoggedUser.currentUser;
                staff.updateProductionSystem(movie);
            }


        }catch (NumberFormatException e){
            System.out.println("Please enter a number!");
        }

        System.out.println();
        System.out.println(oldMovie.getTitle() + " updated successfully");
        HomeView.show();
    }


    private static  void updateSeries(Series oldSeries){
        System.out.println();
        System.out.println("Update the series " + oldSeries.getTitle());
        Scanner scanner = new Scanner(System.in);
        System.out.println("New plot: ");
        String plot = scanner.nextLine();
        Series series = null;

        try{
            System.out.println("Enter number of directors: ");
            String stringNumDirectors = scanner.nextLine();
            int numDirectors = Integer.parseInt(stringNumDirectors);
            List<String> directors = new ArrayList<>();
            for (int i = 0; i < numDirectors; i++) {
                System.out.print("Enter director name: ");
                String director = scanner.nextLine();
                directors.add(director);
            }


            System.out.println("Enter number of actors: ");
            String stringNumActors = scanner.nextLine();
            int numActors = Integer.parseInt(stringNumActors);
            List<String> actors = new ArrayList<>();
            for (int i = 0; i < numActors; i++) {
                System.out.print("Enter actor name: ");
                String actor = scanner.nextLine();
                actors.add(actor);
            }

            System.out.println("Enter number of genres: ");
            String stringNumGenres = scanner.nextLine();
            int numGenres = Integer.parseInt(stringNumGenres);
            List<Genre> genres = new ArrayList<>();
            for (int i = 0; i < numGenres; i++) {
                System.out.print("Enter genre name: ");
                String genreString = scanner.nextLine();
                Genre genre = Genre.fromString(genreString);
                if (genre != null) {
                    genres.add(genre);
                } else {
                    System.out.println("Not a valid genre: " + genreString);
                }
            }


            System.out.println("Enter release year: ");
            String yearString = scanner.nextLine();
            Integer releaseYear = Integer.parseInt(yearString);

            System.out.println("Enter number of seasons: ");
            String noSeasonsString = scanner.nextLine();
            Integer noSeasons = Integer.parseInt(noSeasonsString);

            Map<String, List<Episode>> seasonEpisodes = new TreeMap<>();
            for (int i = 0; i < noSeasons; i++) {
                System.out.println();
                System.out.println("Enter name of season " + (i+1));
                String seasonName = scanner.nextLine();
                List<Episode> episodes = new ArrayList<>();
                System.out.println("Enter number of episodes for this season: ");

                String noEpisodesString = scanner.nextLine();
                Integer noEpisodes = Integer.parseInt(noEpisodesString);
                for (int j = 0; j < noEpisodes; j++) {
                    System.out.println("Enter episode name: ");
                    String episodeName = scanner.nextLine();

                    System.out.println("Enter number of minutes: ");
                    String minutes = scanner.nextLine();
                    Integer.parseInt(minutes); // sa ma asigur ca userul a introdus int
                    String duration = "" + minutes + " minutes";

                    episodes.add(new Episode(episodeName, duration));
                }
                seasonEpisodes.put(seasonName, episodes);

            }

            series = new Series(oldSeries.getTitle(), directors, actors, genres,
                    new ArrayList<>(), plot, LoggedUser.currentUser.getUsername(),
                    null, releaseYear, seasonEpisodes);

            if (LoggedUser.currentUser instanceof Staff
                    && series != null) {
                Staff staff = (Staff) LoggedUser.currentUser;
                staff.updateProductionSystem(series);
            }


        }catch (NumberFormatException e){
            System.out.println("Please enter a number!");
        }

        System.out.println();
        System.out.println(oldSeries.getTitle() + " updated successfully");
        HomeView.show();
    }
}
