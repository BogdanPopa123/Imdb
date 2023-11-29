package org.example;

import org.example.enums.AccountType;

import java.util.List;
import java.util.SortedSet;

public class Admin extends Staff{

    public Admin(Information information, AccountType accountType,
                 String username, Integer experience,
                 List<String> notifications, SortedSet<Object> favourites,
                 List<Request> requests, SortedSet<Object> addedObjects) {
        super(information, accountType, username, experience,
                notifications, favourites, requests, addedObjects);
    }

    public void addUser(User user){
        //TODO
    }

    public void removeUser(String username) {
        //TODO
    }
}
