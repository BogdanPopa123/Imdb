package org.example.cliViews;

import org.example.*;
import org.example.enums.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UpdateActor {

    public static void show() {
        System.out.println();
        System.out.println("Enter the name of the actor"
                + " you want to update: ");

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        Object object = HomeView.fetch(command);

        if (object == null || object instanceof Production) {
            System.out.println();
            System.out.println("No actor with the name " +
                    command + " has been found");
            System.out.println();
            HomeView.show();
        } else if (object instanceof Actor) {
            Actor actor = (Actor) object;
            updateActor(actor);
        }
    }



    private static void updateActor(Actor oldActor){
        System.out.println();
        System.out.println("Update the actor " + oldActor.getName());
        Scanner scanner = new Scanner(System.in);

        System.out.println("New biography: ");
        String biography = scanner.nextLine();
        System.out.println("Number of performances: ");
        String stringNumPairs = scanner.nextLine();
        Actor actor = null;
        try{
            int numPairs = Integer.parseInt(stringNumPairs);


            actor = new Actor(oldActor.getName(), new ArrayList<>(), biography,
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
            staff.updateActor(actor);
        }



        System.out.println();
        System.out.println(oldActor.getName() + " updated successfully");
        HomeView.show();
    }
}
