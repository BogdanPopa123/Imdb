package org.example;

import org.example.cliViews.HomeView;
import org.example.cliViews.LoginView;
import org.example.enums.AccountType;
import org.example.strategies.RatingExperienceStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class Regular extends User implements RequestsManager{

    public Regular(Information information, AccountType accountType,
                   String username, Integer experience,
                   List<String> notifications, SortedSet<Object> favourites) {
        super(information, accountType, username,
                experience, notifications, favourites);
    }

    @Override
    public void createRequest(Request request) {
        String to = request.getSolverUsername();
        if (to.equals("ADMIN")) {
            for (User user : IMDB.getInstance().getUsers()) {
                if (user.getAccountType().equals(AccountType.ADMIN)) {
                    request.registerObserver(user);
                }
            }
            Request.RequestManager.addRequest(request);
        } else {
            for (User user : IMDB.getInstance().getUsers()) {
                if (user.getUsername().equals(to)) {
                    if (user instanceof Staff) {
                        Staff staff = (Staff) user;
                        request.registerObserver(staff);
                        staff.addRequest(request);
                    }
                }
            }
        }

        String notification = "New request\n" +
                    "    Created at: " + request.getCreationTime().toString() +
                    "\n    Type: " + request.getRequestType().toString() +
                    "\n    From: " + request.getIssuerUsername() +
                    "\n    Subject: " + request.getSubject() +
                    "\n    Description: " + request.getDescription();

        request.notifyObservers(notification);
        IMDB.getInstance().getRequests().add(request);
    }

    @Override
    public void removeRequest(Request request) {
        for (Request r : Request.RequestManager.getAdminTeamRequests()) {
            if(request.equals(r)) {
                Request.RequestManager.getAdminTeamRequests().remove(r);
                IMDB.getInstance().getRequests().remove(request);
                return;
            }
        }

        for (User user : IMDB.getInstance().getUsers()){
            if (user instanceof Staff) {
                Staff staff = (Staff) user;
                for (Request r : staff.getRequests()) {
                    if (request.equals(r)){
                        staff.getRequests().remove(request);
                        IMDB.getInstance().getRequests().remove(request);
                        return;
                    }
                }
            }
        }
    }

    public void createRatingProduction(Rating rating) {
        Object object = HomeView.fetch(rating.getSubject());

        Production production = (Production) object;

        production.addRating(rating);

        User prodAuthor = null;
        User ratingAuthor = null;
        for (User user : IMDB.getInstance().getUsers()) {
            if (user.getUsername().equals(production.getAuthor())) {
                prodAuthor = user;
            }
            if (user.getUsername().equals(rating.getUsername())) {
                ratingAuthor = user;
            }
        }

        List<User> otherRaters = new ArrayList<>();
        for (Rating rating1 : production.getRatings()){
            String username = rating1.getUsername();
            for (User user : IMDB.getInstance().getUsers()) {
                if (user.getUsername().equals(username)
                && user != ratingAuthor) {
                    otherRaters.add(user);
                }
            }
        }

        rating.removeAllObservers();

        if (prodAuthor != null) {
            rating.registerObserver(prodAuthor);
        }
//        rating.registerObserver(ratingAuthor);

        for (User user : otherRaters) {
            rating.registerObserver(user);
        }

        //nu vreau ca cel care a creeat requestul sa primeasca
        //notificarea
        rating.removeObserver(ratingAuthor);

        String notification = "The production " + production.getTitle() +
                " has been recently rated by " + rating.getUsername() +
                " with " + rating.getGrade() + ": \"" +
                rating.getComment() + "\" ";
        rating.notifyObservers(notification);

        //userului care a adaugat recenzia ii crestem experienta
        if (!(LoggedUser.currentUser instanceof Admin)) {
            LoggedUser.currentUser.setExperienceStrategy(
                    new RatingExperienceStrategy());
            int newExperince = LoggedUser.currentUser
                    .calculateIncrementedExperience();
            LoggedUser.currentUser.setExperience(newExperince);
        }
    }

    public void createRatingActor(Rating rating) {
        Object object = HomeView.fetch(rating.getSubject());

        Actor actor = (Actor) object;

        actor.addRating(rating);

        User actorAuthor = null;
        User ratingAuthor = null;
        for (User user : IMDB.getInstance().getUsers()) {
            if (user.getUsername().equals(actor.getAuthor())) {
                actorAuthor = user;
            }
            if (user.getUsername().equals(rating.getUsername())) {
                ratingAuthor = user;
            }
        }

        List<User> otherRaters = new ArrayList<>();
        for (Rating rating1 : actor.getRatings()){
            String username = rating1.getUsername();
            for (User user : IMDB.getInstance().getUsers()) {
                if (user.getUsername().equals(username)
                        && user != ratingAuthor) {
                    otherRaters.add(user);
                }
            }
        }

        rating.removeAllObservers();

        if (actorAuthor != null) {
            rating.registerObserver(actorAuthor);
        }
//        rating.registerObserver(ratingAuthor);

        for (User user : otherRaters) {
            rating.registerObserver(user);
        }

        //nu vreau ca cel care a creeat requestul sa primeasca
        //notificarea
        rating.removeObserver(ratingAuthor);

        String notification = "The actor " + actor.getName() +
                " has been recently rated by " + rating.getUsername() +
                " with " + rating.getGrade() + ": \"" +
                rating.getComment() + "\" ";
        rating.notifyObservers(notification);

        if (!(LoggedUser.currentUser instanceof Admin)) {
            LoggedUser.currentUser.setExperienceStrategy(
                    new RatingExperienceStrategy());
            int newExperince = LoggedUser.currentUser
                    .calculateIncrementedExperience();
            LoggedUser.currentUser.setExperience(newExperince);
        }
    }
}
