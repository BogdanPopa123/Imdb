package org.example.cliViews;

import org.example.exceptions.InvalidCommandException;

import java.util.Scanner;

public class InitialView {

    public static void show() {
        System.out.println("How would you like to run this application?");
        System.out.println("1) CLI");
        System.out.println("2) GUI");
        System.out.println("Choose an option: 1/2");

        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            command = scanner.nextLine();

            try{
                validateCommand(command);
                if (command.equals("1")) {
                    System.out.println();
                    System.out.println();
                    LoginView.show();
                } else if (command.equals("2")) {
                    //TODO begin GUI SWING
                }
            }catch (InvalidCommandException e) {
                System.out.println("Invalid command : " + command);
                System.out.println(e.getMessage());
            }
        }while(!command.equals("1") && !command.equals("2"));


    }

    private static void validateCommand(String command) throws InvalidCommandException {
        if ( !command.equals("1") && !command.equals("2")) {
            throw new InvalidCommandException("Please enter a valid command.\n" +
                    "Choose between 1 and 2");
        }
    }
}
