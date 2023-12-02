package org.example;

import org.example.enums.RequestType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Request implements Subject{

    public Request(RequestType requestType, LocalDateTime creationTime,
                   String subject, String description,
                   String issuerUsername, String solverUsername) {
        this.requestType = requestType;
        this.creationTime = creationTime;
        this.subject = subject;
        this.description = description;
        this.issuerUsername = issuerUsername;
        this.solverUsername = solverUsername;
    }

    private RequestType requestType;
    private LocalDateTime creationTime;

    //subject este titlul filmului sau numele filmului ce trebuie modificat
    private String subject;
    private String description;
    private String issuerUsername;
    private String solverUsername;

    private List<User> observers = new ArrayList<>();

    @Override
    public void registerObserver(User observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(User observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (User obserber : observers) {
            obserber.update(this);
        }
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIssuerUsername() {
        return issuerUsername;
    }

    public void setIssuerUsername(String issuerUsername) {
        this.issuerUsername = issuerUsername;
    }

    public String getSolverUsername() {
        return solverUsername;
    }

    public void setSolverUsername(String solverUsername) {
        this.solverUsername = solverUsername;
    }

    public static class RequestManager {
        static private List<Request> adminTeamRequests = new ArrayList<>();

        public static void addRequest(Request r) {
            adminTeamRequests.add(r);
        }

        public static List<Request> getAdminTeamRequests() {
            return adminTeamRequests;
        }

        public static void setAdminTeamRequests(List<Request> adminTeamRequests) {
            RequestManager.adminTeamRequests = adminTeamRequests;
        }
    }
}
