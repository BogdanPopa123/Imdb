package org.example;

import org.example.enums.AccountType;

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
        request.notifyObservers();
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

    public void createRating() {
        //TODO
    }
}
