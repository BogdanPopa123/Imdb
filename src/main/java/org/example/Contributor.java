package org.example;

import org.example.enums.AccountType;

import java.util.List;
import java.util.SortedSet;

public class Contributor extends Staff implements RequestsManager{

    public Contributor(Information information, AccountType accountType,
                       String username, Integer experience,
                       List<String> notifications, SortedSet<Object> favourites,
                       List<Request> requests, SortedSet<Object> addedObjects) {
        super(information, accountType, username, experience,
                notifications, favourites, requests, addedObjects);
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
}
