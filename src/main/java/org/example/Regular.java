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
    public void removeRequest(Request r) {
        //TODO
    }

    public void createRating() {
        //TODO
    }
}
