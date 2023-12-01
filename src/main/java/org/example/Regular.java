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
    public void createRequest(Request r) {
        //TODO
    }

    @Override
    public void removeRequest(Request request) {
        for (Request r : Request.RequestManager.getAdminTeamRequests()) {
            if(request.equals(r)) {
                Request.RequestManager.getAdminTeamRequests().remove(r);
            }
        }

        for (User user : IMDB.getInstance().getUsers()){
            if (user instanceof Staff) {
                Staff staff = (Staff) user;
                for (Request r : staff.getRequests()) {
                    if (request.equals(r)){
                        staff.getRequests().remove(request);
                    }
                }
            }
        }
    }

    public void createRating() {
        //TODO
    }
}
