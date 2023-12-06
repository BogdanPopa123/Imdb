package org.example.cliViews;

import org.example.*;

import java.util.Scanner;

public class AddRatingActor {

    public static void show() {
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select the actor you want to rate:");
        String name = scanner.nextLine();

        Object object = HomeView.fetch(name);
        if (object == null || object instanceof Production) {
            System.out.println("No actor with the name " +
                    name + " has been found");
            System.out.println();
            HomeView.show();
        }

        Actor actor = (Actor) object;
        System.out.println("Give a grade 1 - 10:");
        String stringGrade = scanner.nextLine();

        Integer grade = null;

        try{
            grade = Integer.parseInt(stringGrade);
            validateGrade(grade);
        }catch (NumberFormatException e){
            System.out.println("Rating failed. You need to type a number 1 - 10");
            HomeView.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            HomeView.show();
        }

        System.out.println("Add a comment: ");
        String comment = scanner.nextLine();

        Rating rating = new Rating(LoggedUser.currentUser.getUsername(),
                grade, comment, actor.getName());

        Regular regular = (Regular) LoggedUser.currentUser;
        regular.createRatingActor(rating);

        System.out.println();
        System.out.println("Rating for " + actor.getName() +
                " added successfully");
        System.out.println();
        HomeView.show();
    }





    private static void validateGrade(Integer grade) throws Exception{
        if (grade < 1 || 10 < grade) {
            throw new Exception("Rating failed. You need to " +
                    "enter a number in the valid range");
        }
    }
}
