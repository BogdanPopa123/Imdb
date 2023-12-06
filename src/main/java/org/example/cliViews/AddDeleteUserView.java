package org.example.cliViews;

import org.example.exceptions.InvalidCommandException;

import java.util.Scanner;

public class AddDeleteUserView {
    public static void show(){
        Scanner scanner = new Scanner(System.in);
        String command;

        do{
            System.out.println();
            System.out.println("What would you like to do?");
            System.out.println("    1) Add");
            System.out.println("    2) Delete");
            System.out.println("    Press 0 to go back");

            command = scanner.nextLine();

            try{
                validateCommand(command, 2);
                if (command.equals("0")) {
                    HomeView.show();
                } else if (command.equals("1")) {
                    AddUserView.show();
                } else if (command.equals("2")) {
                    DeleteUserView.show();
                }
            }catch (InvalidCommandException e) {
                System.out.println("Invalid command : " + command);
                System.out.println(e.getMessage());
            }


        }while(!command.equals("0") && !command.equals("1")
                && !command.equals("2"));


    }


    private static void validateCommand(String command, Integer size)
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
