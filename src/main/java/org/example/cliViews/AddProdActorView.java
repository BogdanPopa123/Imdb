package org.example.cliViews;

import org.example.*;
import org.example.enums.Genre;
import org.example.exceptions.InvalidCommandException;

import java.util.*;

public class AddProdActorView {

    public static void show(){
        showMenu();
    }

    public static void showMenu(){

        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            System.out.println();
            System.out.println("What do you wish to add to the system?");
            System.out.println("    1) Actor");
            System.out.println("    2) Movie");
            System.out.println("    3) Series");
            System.out.println("    Press 0 to go back");
            command = scanner.nextLine();

            try{
                validateDeleteCommand(command, 3);

                if (command.equals("0")) {
                    HomeView.show();
                } else if (command.equals("1")) {
                    addActor();
                } else if (command.equals("2")) {
                    addMovie();
                } else if (command.equals("3")) {
                    addSeries();
                }

            }catch(InvalidCommandException e){
                System.out.println("Invalid command: " + command);
                System.out.println(e.getMessage());
            }
        }while(!command.equals("0") && !command.equals("1")
        && !command.equals("2") && !command.equals("3"));
    }





    private static void addActor(){
        System.out.println();
        System.out.println("Name of the new actor: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println("Biography of new actor: ");
        String biography = scanner.nextLine();
        System.out.println("Number of performances: ");
        String stringNumPairs = scanner.nextLine();
        Actor actor = null;
        try{
            int numPairs = Integer.parseInt(stringNumPairs);


            actor = new Actor(name, new ArrayList<>(), biography,
                    LoggedUser.currentUser.getUsername());

            for (int i = 0; i < numPairs; i++) {
                System.out.println("Enter name of performance:");
                String perfName = scanner.nextLine();
                System.out.println("Enter type of performance:");
                String perfType = scanner.nextLine();
                actor.addPerformance(perfName, perfType);
            }
        }catch(NumberFormatException e){
            System.out.println(stringNumPairs + " is not a number");
        }

        if (LoggedUser.currentUser instanceof Staff
        && actor!= null) {
            Staff staff = (Staff) LoggedUser.currentUser;
            staff.addActorSystem(actor);
        }

        showMenu();
    }

    private static void addMovie(){
        System.out.println();
        System.out.println("Title of the new movie: ");
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();
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

            movie = new Movie(title, directors, actors, genres,
                    new ArrayList<>(), plot, LoggedUser.currentUser.getUsername(),
                    duration, releaseYear);

            if (LoggedUser.currentUser instanceof Staff
                    && movie != null) {
                Staff staff = (Staff) LoggedUser.currentUser;
                staff.addProductionSystem(movie);
            }


        }catch (NumberFormatException e){
            System.out.println("Please enter a number!");
        }

        showMenu();
    }

    private static  void addSeries(){
        System.out.println();
        System.out.println("Title of the new series: ");
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();
        System.out.println("Plot of new series: ");
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

            series = new Series(title, directors, actors, genres,
                    new ArrayList<>(), plot, LoggedUser.currentUser.getUsername(),
                    releaseYear, seasonEpisodes);

            if (LoggedUser.currentUser instanceof Staff
                    && series != null) {
                Staff staff = (Staff) LoggedUser.currentUser;
                staff.addProductionSystem(series);
            }


        }catch (NumberFormatException e){
            System.out.println("Please enter a number!");
        }

        showMenu();
    }
    private static void validateDeleteCommand(String command, Integer size)
            throws InvalidCommandException {
        int throwFlag = 1;
        for (int i = 0; i <= size; i++) {
            Integer num = i;
            if (command.equals(num.toString())) {
                throwFlag = 0;
            }
        }

        if (throwFlag == 1) {
            throw new InvalidCommandException("Please enter a valid command.\n" +
                    "Choose an integer in range 1 - " + size);
        }
    }

}
