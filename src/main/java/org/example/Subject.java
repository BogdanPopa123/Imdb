package org.example;

public interface Subject {
    void registerObserver(User observer);

    void removeObserver(User observer);

    void notifyObservers();
}
