package org.example.cliViews;

import org.example.Actor;
import org.example.LoggedUser;
import org.example.Production;
import org.example.Staff;

import java.util.Scanner;

public class DeleteProdActorView {

    public static void show(){
        System.out.println();
        System.out.println("Write the name of movie/series/actor " +
                "you want to remove");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Object obj = HomeView.fetch(input);
        if (obj == null) {
            System.out.println("No result found for " + input);
            AddDeleteProdActor.show();
        }
        if (obj instanceof Production){
            if (LoggedUser.currentUser instanceof Staff) {
                Staff staff = (Staff) LoggedUser.currentUser;
                Production production = (Production) obj;
                staff.removeProductionSystem(production.getTitle());
                System.out.println(input + " removed successfully");
                AddDeleteProdActor.show();
            }
        } else if (obj instanceof Actor) {
            if (LoggedUser.currentUser instanceof Staff) {
                Staff staff = (Staff) LoggedUser.currentUser;
                Actor actor = (Actor) obj;
                staff.removeActorSystem(actor.getName());
                System.out.println(input + " removed successfully");
                AddDeleteProdActor.show();
            }
        }
    }
}
