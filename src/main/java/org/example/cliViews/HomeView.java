package org.example.cliViews;

import org.example.*;
import org.example.enums.AccountType;
import org.example.enums.Genre;
import org.example.exceptions.InvalidCommandException;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class HomeView {

    public static void show(){

        AccountType type = LoggedUser.currentUser.getAccountType();

        if (type.equals(AccountType.ADMIN)) {
            showAdmin();
        } else if (type.equals(AccountType.CONTRIBUTOR)) {
            showContributor();
        } else if (type.equals(AccountType.REGULAR)) {
            showRegular();
        }

    }

    private static void showAdmin() {
        System.out.println("Welcome back " +
                LoggedUser.currentUser.getUsername() + "!");
        System.out.println("Username: " +
                LoggedUser.currentUser.getUsername());
        System.out.println("User Experience: -");
        System.out.println("Choose an action:");
        System.out.println("    1)View production details");
        System.out.println("    2)View actor details");
        System.out.println("    3)View notifications");
        System.out.println("    4)Search for actor/movie/series");
        System.out.println("    5)Add/delete actor/movie/series  to/from favourites");
        System.out.println("    6)Add/delete user");
        System.out.println("    7)Add/delete actor/movie/series from system");
        System.out.println("    8)Update movie details");
        System.out.println("    9)Update actor details");
        System.out.println("    10)Solve a request");
        System.out.println("    11)Logout");

        System.out.println();
        System.out.print("Enter a command:");
        String command;

        do{
            Scanner scanner = new Scanner(System.in);
            command = scanner.nextLine();

            try{
                validateAdminCommand(command);
                if (command.equals("1")) {
                    listProductions();
                } else if (command.equals("2")) {

                } else if (command.equals("3")) {

                } else if (command.equals("4")) {

                } else if (command.equals("5")) {

                } else if (command.equals("6")) {

                } else if (command.equals("7")) {

                } else if (command.equals("8")) {

                } else if (command.equals("9")) {

                } else if (command.equals("10")) {

                } else if (command.equals("11")) {
                    logout();
                }
            }catch(InvalidCommandException e){
                System.out.println("Invalid command : " + command);
                System.out.println(e.getMessage());
            }

        }while(true);
    }

    private static void showContributor(){
        System.out.println("Welcome back " +
                LoggedUser.currentUser.getUsername() + "!");
        System.out.println("Username: " +
                LoggedUser.currentUser.getUsername());
        System.out.println("User Experience: " +
                LoggedUser.currentUser.getExperience());
        System.out.println("Choose an action:");
        System.out.println("    1)View production details");
        System.out.println("    2)View actor details");
        System.out.println("    3)View notifications");
        System.out.println("    4)Search for actor/movie/series");
        System.out.println("    5)Add/delete actor/movie/series  to/from favourites");
        System.out.println("    6)Add/delete actor/movie/series from system");
        System.out.println("    7)Update movie details");
        System.out.println("    8)Update actor details");
        System.out.println("    9)Create a request");
        System.out.println("    10)Solve a request");
        System.out.println("    11)Logout");

        System.out.println();
        System.out.print("Enter a command:");
        String command;

        do{
            Scanner scanner = new Scanner(System.in);
            command = scanner.nextLine();

            try{
                //pentru contributor pot folosi validatorul
                //de admin pt ca are tot comenzi de la 1 la 11
                validateAdminCommand(command);
                if (command.equals("1")) {
                    listProductions();
                } else if (command.equals("2")) {

                } else if (command.equals("3")) {

                } else if (command.equals("4")) {

                } else if (command.equals("5")) {

                } else if (command.equals("6")) {

                } else if (command.equals("7")) {

                } else if (command.equals("8")) {

                } else if (command.equals("9")) {

                } else if (command.equals("10")) {

                } else if (command.equals("11")) {
                    logout();
                }
            }catch(InvalidCommandException e){
                System.out.println("Invalid command : " + command);
                System.out.println(e.getMessage());
            }

        }while(true);


    }

    private static void showRegular() {
        System.out.println("Welcome back " +
                LoggedUser.currentUser.getUsername() + "!");
        System.out.println("Username: " +
                LoggedUser.currentUser.getUsername());
        System.out.println("User Experience: " +
                LoggedUser.currentUser.getExperience());
        System.out.println("Choose an action:");
        System.out.println("    1)View production details");
        System.out.println("    2)View actor details");
        System.out.println("    3)View notifications");
        System.out.println("    4)Search for actor/movie/series");
        System.out.println("    5)Add/delete actor/movie/series  to/from favourites");
        System.out.println("    6)Create a request");
        System.out.println("    7)Logout");

        System.out.println();
        System.out.print("Enter a command:");
        String command;

        do{
            Scanner scanner = new Scanner(System.in);
            command = scanner.nextLine();

            try{
                validateRegularCommand(command);
                if (command.equals("1")) {
                    listProductions();
                } else if (command.equals("2")) {

                } else if (command.equals("3")) {

                } else if (command.equals("4")) {

                } else if (command.equals("5")) {

                } else if (command.equals("6")) {

                } else if (command.equals("7")) {
                    logout();
                }
            }catch(InvalidCommandException e){
                System.out.println("Invalid command : " + command);
                System.out.println(e.getMessage());
            }

        }while(true);
    }

    private static void displayProductions(){

    }


    private static void validateAdminCommand(String command)
            throws InvalidCommandException {
        if ( !command.equals("1") && !command.equals("2")
                && !command.equals("3") && !command.equals("4")
                && !command.equals("5") && !command.equals("6")
                && !command.equals("7") && !command.equals("8")
                && !command.equals("9") && !command.equals("10")
                && !command.equals("11")) {
            throw new InvalidCommandException("Please enter a valid command.\n" +
                    "Choose an integer in range 1 - 11");
        }
    }

    private static void validateRegularCommand(String command)
            throws InvalidCommandException {
        if ( !command.equals("1") && !command.equals("2")
                && !command.equals("3") && !command.equals("4")
                && !command.equals("5") && !command.equals("6")
                && !command.equals("7")) {
            throw new InvalidCommandException("Please enter a valid command.\n" +
                    "Choose an integer in range 1 - 7");
        }
    }

    private static void logout(){
        System.out.println("Logging you out. Please wait...");
        LoggedUser.setAnonymousUser();
        LoginView.show();
    }

    private static void listProductions(){
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("-------------------------------------" +
                "Productions" + "-------------------------------------");

        List<Production> productions = IMDB.getInstance().getProductions();
        for(Production production : productions) {
            String type = null;
            if (production instanceof Movie) {
                type = "Movie";
            } else if (production instanceof Series) {
                type = "Series";
            }
            System.out.println(production.getTitle() +
                    " (" + type + ")");
            System.out.println("    Directors:");
            for (String director : production.getDirectors()){
                System.out.println("        " + director);
            }
            System.out.println("    Actors:");
            for (String actor : production.getActors()){
                System.out.println("        " + actor);
            }
            System.out.println("    Genres:");
            for (Genre genre : production.getGenres()){
                System.out.println("        " + genre.toString());
            }
            System.out.println("    Ratings:");
            for (Rating rating : production.getRatings()) {
                System.out.println("        " + rating.getUsername());
                System.out.println("            rated with: " +
                        rating.getGrade());
                System.out.println("            " + rating.getComment());
                System.out.println();
            }

            System.out.println("    Plot: " + production.getDescription());
            System.out.println("    Average rating: " +
                    production.getAverageRating());

            if (production instanceof Movie) {
                Movie movie = (Movie) production;
                System.out.println("    Release year: " +
                        movie.getReleaseYear());
                System.out.println("    Duration: " +
                        movie.getRuntime());
            } else if (production instanceof Series) {
                Series series = (Series) production;
                System.out.println("    Release year: " +
                        series.getReleaseYear());
                Map<String, List<Episode>> seasonEpisodes = series.getSeasonEpisodes();

                seasonEpisodes.forEach((seasonName, episodes) ->{
                    System.out.println("    " + seasonName);
                    episodes.forEach(episode -> {
                        System.out.println("        " + episode.getName());
                        System.out.println("        " + episode.getRuntime());
                        System.out.println();
                    });
                });

            }

            System.out.println();
            System.out.println();

        }
        System.out.println("-------------------------------------" +
                "Productions" + "-------------------------------------");
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("Press enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println();
        System.out.println();
        HomeView.show();
    }
}
