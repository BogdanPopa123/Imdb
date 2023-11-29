package org.example;

import org.example.enums.AccountType;

import java.util.List;
import java.util.SortedSet;

public abstract class Staff extends User implements StaffInterface{


    public Staff(Information information, AccountType accountType,
                 String username, Integer experience,
                 List<String> notifications, SortedSet<Object> favourites,
                 List<Request> requests, SortedSet<Object> addedObjects) {
        super(information, accountType, username,
                experience, notifications, favourites);
        this.requests = requests;
        this.addedObjects = addedObjects;
    }

    //o lista cu requesturile pe care doar respectivul
    // StaffMember le poate rezolva
    private List<Request> requests;
    private SortedSet<Object> addedObjects;

    public void addRequest(Request r) {
        requests.add(r);
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public SortedSet<Object> getAddedObjects() {
        return addedObjects;
    }

    public void setAddedObjects(SortedSet<Object> addedObjects) {
        this.addedObjects = addedObjects;
    }

    @Override
    public void addProductionSystem(Production p) {
        //TODO
    }

    @Override
    public void addActorSystem(Actor a) {
        //TODO
    }

    @Override
    public void removeProductionSystem(String name) {
        //TODO
    }

    @Override
    public void removeActorSystem(String name) {
        //TODO
    }

    @Override
    public void updateProductionSystem(Production p) {
        //TODO
    }

    @Override
    public void updateActor(Actor a) {
        //TODO
    }

    public void solveRequest() {
        //TODO
    }
}
