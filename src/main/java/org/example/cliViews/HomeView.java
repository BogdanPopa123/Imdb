package org.example.cliViews;

import org.example.LoggedUser;
import org.example.enums.AccountType;
import org.example.exceptions.InvalidCommandException;

import java.util.Scanner;

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

                } else if (command.equals("2")) {

                } else if (command.equals("3")) {

                } else if (command.equals("4")) {

                } else if (command.equals("5")) {

                } else if (command.equals("6")) {

                } else if (command.equals("7")) {
                    logout();
                }
            }catch(InvalidCommandException e){

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
}
