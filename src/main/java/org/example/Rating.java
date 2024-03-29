package org.example;

import java.util.ArrayList;
import java.util.List;

public class Rating implements Subject{

    public Rating(String username, Integer grade, String comment,
                  String subject) {
        this.username = username;
        this.grade = grade;
        this.comment = comment;
        this.subject = subject;
    }

    private String username;
    private Integer grade;
    private String comment;

    //subject este titlul productiei / numele actorului caruia ii
    //adaugam o recenzie
    private String subject;

    private List<User> observers = new ArrayList<>();
    @Override
    public void registerObserver(User observer) {
        if (!observers.contains(observer)){
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(User observer) {
        if (observers.contains(observer)){
            observers.remove(observer);
        }
    }

    @Override
    public void removeAllObservers() {
        observers.clear();
    }

    @Override
    public void notifyObservers(String message) {
        for (User observer : observers) {
            observer.update(this, message);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        if (1 <= grade && grade <= 10) {
            this.grade = grade;
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "username='" + username + '\'' +
                ", grade=" + grade +
                ", comment='" + comment + '\'' +
                "subject='" + subject + '\'' +
                '}';
    }
}
