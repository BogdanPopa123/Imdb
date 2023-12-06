package org.example;

import org.example.cliViews.HomeView;
import org.example.cliViews.SolveRequestsView;
import org.example.enums.AccountType;
import org.example.enums.RequestType;
import org.example.strategies.ActorAddedExperienceStrategy;
import org.example.strategies.ProductionAddedExperienceStrategy;
import org.example.strategies.RatingExperienceStrategy;
import org.example.strategies.RequestSolvedExperienceStrategy;

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

        if (LoggedUser.currentUser instanceof Contributor) {
            LoggedUser.currentUser.setExperienceStrategy(
                    new ProductionAddedExperienceStrategy());
            int newExperince = LoggedUser.currentUser
                    .calculateIncrementedExperience();
            LoggedUser.currentUser.setExperience(newExperince);
        }
    }

    @Override
    public void addActorSystem(Actor a) {
        IMDB.getInstance().getActors().add(a);
        if (LoggedUser.currentUser instanceof Staff) {
            Staff staff = (Staff) LoggedUser.currentUser;
            staff.addedObjects.add(a.getName());
        }

        if (LoggedUser.currentUser instanceof Contributor) {
            LoggedUser.currentUser.setExperienceStrategy(
                    new ActorAddedExperienceStrategy());
            int newExperince = LoggedUser.currentUser
                    .calculateIncrementedExperience();
            LoggedUser.currentUser.setExperience(newExperince);
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
        for (Production production : IMDB.getInstance().getProductions()) {
            if (production.getTitle().equals(p.getTitle())) {
                production.setActors(p.getActors());
                production.setDescription(p.getDescription());
                production.setDirectors(p.getDirectors());
                production.setGenres(p.getGenres());

                if (p instanceof Movie) {
                    Movie movie = (Movie) production;
                    Movie m = (Movie) p;
                    movie.setRuntime(m.getRuntime());
                    movie.setReleaseYear(m.getReleaseYear());
                } else if (p instanceof Series) {
                    Series series = (Series) production;
                    Series s = (Series) p;
                    series.setReleaseYear(s.getReleaseYear());
                    series.setSeasonEpisodes(s.getSeasonEpisodes());
                }
            }
        }
    }

    @Override
    public void updateActor(Actor a) {
        for (Actor actor : IMDB.getInstance().getActors()) {
            if (actor.getName().equals(a.getName())) {
                actor.setBiography(a.getBiography());
                actor.setPerformances(a.getPerformances());
            }
        }
    }

    public void solveRequest(Request request) {
        SolveRequestsView.requestList.remove(request);
        if (request.getSolverUsername().equals("ADMIN")) {
            Request.RequestManager.getAdminTeamRequests().remove(request);
        } else {
            Staff staff = (Staff) LoggedUser.currentUser;
            staff.getRequests().remove(request);
        }
        IMDB.getInstance().getRequests().remove(request);


        request.removeAllObservers();

        User author = null;
        for (User user : IMDB.getInstance().getUsers()) {
            if (user.getUsername().equals(request.getIssuerUsername())) {
                author = user;
                break;
            }
        }

        //inregistram ca observer pe creatorul requestului
        request.registerObserver(author);

        String notification = "Your request\n" +
                "    Created at: " + request.getCreationTime().toString() +
                "\n    Type: " + request.getRequestType().toString() +
                "\n    Subject: " + request.getSubject() +
                "\n    Description: " + request.getDescription() +
                "\n Has been successfully solved!";

        request.notifyObservers(notification);

        if (request.getRequestType().equals(RequestType.ACTOR_ISSUE)
            || request.getRequestType().equals(RequestType.MOVIE_ISSUE)) {

            author.setExperienceStrategy(new RequestSolvedExperienceStrategy());
            int newExperince = author.calculateIncrementedExperience();
            author.setExperience(newExperince);
        }
    }

    public void denyRequest(Request request) {
        SolveRequestsView.requestList.remove(request);
        if (request.getSolverUsername().equals("ADMIN")) {
            Request.RequestManager.getAdminTeamRequests().remove(request);
        } else {
            Staff staff = (Staff) LoggedUser.currentUser;
            staff.getRequests().remove(request);
        }
        IMDB.getInstance().getRequests().remove(request);


        request.removeAllObservers();

        User author = null;
        for (User user : IMDB.getInstance().getUsers()) {
            if (user.getUsername().equals(request.getIssuerUsername())) {
                author = user;
                break;
            }
        }

        //inregistram ca observer pe creatorul requestului
        request.registerObserver(author);

        String notification = "Your request\n" +
                "    Created at: " + request.getCreationTime().toString() +
                "\n    Type: " + request.getRequestType().toString() +
                "\n    Subject: " + request.getSubject() +
                "\n    Description: " + request.getDescription() +
                "\n Has been denied!";

        request.notifyObservers(notification);
    //}
    }
}
