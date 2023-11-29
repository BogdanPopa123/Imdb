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
    public void createRequest(Request r) {
        //TODO
    }

    @Override
    public void removeRequest(Request r) {
        //TODO
    }
}
