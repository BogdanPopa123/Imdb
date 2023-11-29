package org.example;

import org.example.enums.AccountType;

import java.util.List;
import java.util.SortedSet;

public class UserFactory {

    //clasa UserFactory a fost implementata ca si un singleton
    private static final UserFactory instance = new UserFactory();

    private UserFactory(){

    }

    public static UserFactory getInstance(){
        return instance;
    }

    public Regular createRegular(User.Information information,
                                     AccountType accountType,
                                     String username,
                                     Integer experience,
                                     List<String> notifications,
                                     SortedSet<Object> favourites){
        return new Regular(information, accountType, username,
                experience, notifications, favourites);
    }

    public Contributor createContributor(User.Information information,
                                         AccountType accountType,
                                         String username,
                                         Integer experience,
                                         List<String> notifications,
                                         SortedSet<Object> favourites,
                                         List<Request> requests,
                                         SortedSet<Object> addedObjects){
        return new Contributor(information, accountType, username, experience,
                notifications, favourites, requests, addedObjects);
    }

    public Admin createAdmin(User.Information information,
                             AccountType accountType,
                             String username,
                             Integer experience,
                             List<String> notifications,
                             SortedSet<Object> favourites,
                             List<Request> requests,
                             SortedSet<Object> addedObjects){
        return new Admin(information, accountType, username, experience,
                notifications, favourites, requests, addedObjects);
    }
}
