package org.example.cliViews;

import org.example.IMDB;
import org.example.LoggedUser;
import org.example.User;

import java.util.List;
import java.util.Scanner;

public class LoginView {

    public static void show() {
        System.out.println("Welcome! Please login");

        Boolean authDone = false;

        do{
            Scanner scanner = new Scanner(System.in);
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password:");
            String password = scanner.nextLine();

            List<User> users = IMDB.getInstance().getUsers();
            User mathcedUser = null;
            for (User user : users) {
                if (user.getUsername().equals(username)){
                    mathcedUser = user;
                    break;
                }
            }
            if (mathcedUser == null){
                System.out.println("Credentials do not match");
                continue;
            } else {
                User.Information info = mathcedUser.getInformation();
                if (!info.getCredentials().getPassword().equals(password)) {
                    System.out.println("Credentials do not match");
                    continue;
                } else {
                    //login successful
                    authDone = true;
                    LoggedUser.setCurrentUser(mathcedUser);
                }
            }

        }while (!authDone);

        System.out.println();
        System.out.println();
        HomeView.show();


    }
}
