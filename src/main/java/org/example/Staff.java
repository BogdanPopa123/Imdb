package org.example;

import org.example.cliViews.HomeView;
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
        IMDB.getInstance().getProductions().add(p);
        if (LoggedUser.currentUser instanceof Staff) {
            Staff staff = (Staff) LoggedUser.currentUser;
            staff.addedObjects.add(p.getTitle());
        }
    }

    @Override
    public void addActorSystem(Actor a) {
        IMDB.getInstance().getActors().add(a);
        if (LoggedUser.currentUser instanceof Staff) {
            Staff staff = (Staff) LoggedUser.currentUser;
            staff.addedObjects.add(a.getName());
        }
    }

    @Override
    public void removeProductionSystem(String name) {
        //mai intai stergem toate requesturile legate de acest production
        for (User user : IMDB.getInstance().getUsers()) {
            if (user instanceof Staff) {
                Staff staff = (Staff) user;
                for(Request request : staff.getRequests()) {
                    if (request.getSubject() != null
                    && request.getSubject().equals(name)){
                        staff.getRequests().remove(request);
                    }
                }
            }
        }

        for (Request request : Request.RequestManager.getAdminTeamRequests()) {
            if (request.getSubject() != null
                    && request.getSubject().equals(name)) {
                Request.RequestManager.getAdminTeamRequests().remove(request);
            }
        }

        Object obj = HomeView.fetch(name);
        Production production = (Production) obj;

        IMDB.getInstance().getProductions().remove(production);
    }

    @Override
    public void removeActorSystem(String name) {
        //mai intai stergem toate requesturile legate de acest actor
        for (User user : IMDB.getInstance().getUsers()) {
            if (user instanceof Staff) {
                Staff staff = (Staff) user;
                for(Request request : staff.getRequests()) {
                    if (request.getSubject() != null
                    && request.getSubject().equals(name)){
                        staff.getRequests().remove(request);
                    }
                }
            }
        }

        for (Request request : Request.RequestManager.getAdminTeamRequests()) {
            if (request.getSubject() != null &&
                    request.getSubject().equals(name)) {
                Request.RequestManager.getAdminTeamRequests().remove(request);
            }
        }

        Object obj = HomeView.fetch(name);
        Actor actor = (Actor) obj;

        IMDB.getInstance().getActors().remove(actor);
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
