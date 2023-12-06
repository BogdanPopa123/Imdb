package org.example.cliViews;

import org.example.Admin;
import org.example.IMDB;
import org.example.LoggedUser;
import org.example.User;

import java.util.Scanner;

public class DeleteUserView {

    public static void show(){
        System.out.println();
        System.out.println("Remove an user");
        System.out.println("Type the username of the user you want to remove");

        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();

        Admin admin = (Admin) LoggedUser.currentUser;
        User removed = admin.removeUser(username);

        System.out.println();
        if (removed == null) {
            System.out.println("No user with username " +
                    username + " has been found");
        } else {
            System.out.println("User with username " +
                    username + " removed successfully");
        }


        HomeView.show();
    }
}
