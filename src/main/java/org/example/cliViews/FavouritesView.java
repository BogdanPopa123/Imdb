package org.example.cliViews;

import org.example.*;
import org.example.exceptions.InvalidCommandException;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;

public class FavouritesView {

    public static void show(){
        System.out.println();
        System.out.println();
        listFavourites();
        showMenu();
    }

    private static void showMenu() {
        System.out.println("What would you like to do next?\n");
        System.out.println("    1) Add to favourites");
        System.out.println("    2) Remove from favourites");
        System.out.println("    3) List favourites");
        System.out.println("    4) Return");

        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            System.out.print("Enter a command: ");
            command = scanner.nextLine();

            try{
                validateCommand(command);
                if (command.equals("1")) {
                    addFavourite();
                } else if (command.equals("2")) {
                    removeFavourite();
                } else if (command.equals("3")) {
                    System.out.println();
                    listFavourites();
                    showMenu();
                } else if (command.equals("4")) {
                    System.out.println();
                    System.out.println();
                    HomeView.show();
                }
            }catch(InvalidCommandException e){
                System.out.println("Invalid command : " + command);
                System.out.println(e.getMessage());
            }
        }while(!command.equals("1") && !command.equals("2")
                &&!command.equals("3") &&!command.equals("4"));
    }
    private static void listFavourites() {
        System.out.println("Your favourites are: ");
        SortedSet<Object> favourites = LoggedUser.
                currentUser.getFavourites();
        int index = 0;
        for (Object favourite : favourites) {
            index ++;
            if (favourite instanceof Actor) {
                Actor actor = (Actor) favourite;
                System.out.println("    " + index + ") " +
                        actor.getName() + " (actor)");
            } else if (favourite instanceof Movie) {
                Movie movie  = (Movie) favourite;
                System.out.println("    " + index + ") "
                        + movie.getTitle() + " (movie)");
            } else if (favourite instanceof Series) {
                Series series = (Series) favourite;
                System.out.println("    " + index + ") " +
                        series.getTitle() + " (series)");
            }
        }
        System.out.println();
        System.out.println();
    }

    private static void addFavourite() {
        System.out.println();
        System.out.print("Enter the name of the actor/" +
                "the title of the movie/series: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Object fetched = HomeView.fetch(input);

        if (fetched == null) {
            System.out.println();
            System.out.println("No result has been found for search: " +
                    input);
            System.out.println();
        }else if (LoggedUser.currentUser.getFavourites().contains(fetched)) {

            System.out.println();
            String name = null;
            if (fetched instanceof Production){
                name = ((Production)fetched).getTitle();
            } else if (fetched instanceof Actor) {
                name = ((Actor)fetched).getName();
            }
            System.out.println(name + " is already to your favourites list");
            System.out.println();

        } else if (fetched instanceof Production) {
            Production p = (Production) fetched;
            LoggedUser.currentUser.getFavourites().add(fetched);
            System.out.println();
            System.out.println("Successfully added " +
                    p.getTitle() + " to the favourites");
            System.out.println();
        } else if (fetched instanceof Actor) {
            Actor a = (Actor) fetched;
            LoggedUser.currentUser.getFavourites().add(fetched);
            System.out.println();
            System.out.println("Successfully added " +
                    a.getName() + " to the favourites");
            System.out.println();
        }

        FavouritesView.showMenu();

    }

    private static void removeFavourite(){


        Scanner scanner = new Scanner(System.in);
        String command;

        Integer index = -1;
        do {
            System.out.println();
            Integer size = LoggedUser.currentUser.getFavourites().size();
            System.out.println("Choose what you wish to remove from favourites");
            System.out.println("Choose a number in range 1 - " + size);
            System.out.println("Choose 0 to cancel");
            command = scanner.nextLine();

            try{
                validateDeleteCommand(command, size);
                index = Integer.parseInt(command);

                if (index == 0) {
                    System.out.println();
                    System.out.println();
                    FavouritesView.showMenu();
                }

                index = index - 1;
                Integer iterator = 0;
                String name = null;
                for (Object object : LoggedUser.currentUser.getFavourites()) {
                    if (iterator == index){
                        if (object instanceof Production) {
                            name  = ((Production)object).getTitle();
                        } else if (object instanceof Actor) {
                            name = ((Actor)object).getName();
                        }
                        LoggedUser.currentUser.getFavourites().remove(object);
                        break;
                    }
                    iterator++;
                }
                System.out.println();
                System.out.println("Successfully removed " + name +
                        " from the favourites");

            }catch(InvalidCommandException e){
                System.out.println("Invalid command: " + command);
                System.out.println(e.getMessage());
            }
        }while(index < 0 && index >= LoggedUser.currentUser.getFavourites().size());

        System.out.println();
        FavouritesView.showMenu();
    }
    private static void validateCommand(String command)
            throws InvalidCommandException{
        if (!command.equals("1") && !command.equals("2")
        && !command.equals("3") && !command.equals("4")) {
            throw new InvalidCommandException("Please enter a valid command.\n" +
                    "Choose an integer in range 1 - 4");
        }
    }

    private static void validateDeleteCommand(String command, Integer size)
        throws InvalidCommandException{
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
