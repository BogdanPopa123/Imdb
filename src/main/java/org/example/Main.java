package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        IMDB imdb = IMDB.getInstance();
        imdb.run();

        List<User> users = imdb.getUsers();
//        for (User user : users){
//            System.out.println(user.getUsername());
//            System.out.println(user.getExperience());
//            System.out.println(user.getAccountType());
//            System.out.println(user.getNotifications().toString());
//            System.out.println(user.getInformation().toString());
//            System.out.println(user.getFavourites().toString());
//            if (user instanceof Staff) {
//                System.out.println((((Staff) user).getAddedObjects()));
//            }
//            System.out.println();
//            System.out.println();
//        }

//        List<Actor> actors = imdb.getActors();
//        for (Actor actor : actors) {
//            System.out.println(actor.getName());
//            System.out.println(actor.getBiography());
//            System.out.println(actor.getPerformances().toString());
//            System.out.println();
//            System.out.println();
//            System.out.println();
//        }

//        List<Request> requests = imdb.getRequests();
//        for (Request request : requests) {
//            System.out.println(request.getRequestType());
//            System.out.println(request.getCreationTime());
//            System.out.println(request.getSubject());
//            System.out.println(request.getDescription());
//            System.out.println(request.getIssuerUsername());
//            System.out.println(request.getSolverUsername());
//            System.out.println();
//            System.out.println();
//            System.out.println();
//        }

//        List<Production> productions = imdb.getProductions();
//        for (Production p : productions) {
//            if (p instanceof Series) {
//                System.out.println(p.getTitle());
//                for (Map.Entry<String, List<Episode>> entry  : ((Series) p).getSeasonEpisodes().entrySet()) {
//                    System.out.println(entry.getKey());
//                    System.out.println(entry.getValue().toString());
//                }
//                System.out.println();
//                System.out.println();
//                System.out.println();
//            }
//        }

    }
}
