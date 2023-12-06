package org.example;

import org.example.enums.AccountType;
import org.example.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.SortedSet;

public abstract class User implements Observer {

    public User(Information information, AccountType accountType,
                String username, Integer experience,
                List<String> notifications, SortedSet<Object> favourites) {
        this.information = information;
        this.accountType = accountType;
        this.username = username;
        this.experience = experience;
        this.notifications = notifications;
        this.favourites = favourites;
    }

    private Information information;
    private AccountType accountType;
    private String username;
    private Integer experience;
    private List<String> notifications;
    private SortedSet<Object> favourites;


    public void registerAsObserver(Request request) {
        request.registerObserver(this);
    }

    public void unregisterAsObserver(Request request) {
        request.removeObserver(this);
    }

    @Override
    public void update(Subject subject, String message) {
        if (subject instanceof Request) {
//            Request request = (Request) subject;
//            String notification = "New request\n" +
//                    "    Created at: " + request.getCreationTime().toString() +
//                    "\n    Type: " + request.getRequestType().toString() +
//                    "\n    From: " + request.getIssuerUsername() +
//                    "\n    Subject: " + request.getSubject() +
//                    "\n    Description: " + request.getDescription();
//            this.getNotifications().add(notification);
        this.getNotifications().add(message);
        } else if (subject instanceof Rating) {
            this.getNotifications().add(message);
        }
    }

    public void addToFavourites(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if ( (!(o instanceof Production)) && (!(o instanceof Actor))) {
            throw new ClassCastException("Cannot add to favourites non-production / non-actor");

        }
        this.favourites.add(o);
    }

    public void removeFromFavourites(String name) {
        for (Object o : favourites) {
            if (o instanceof Production) {
                Production p = (Production) o;
                if (p.getTitle().equals(name)) {
                    favourites.remove(o);
                    return;
                }
            } else if (o instanceof Actor) {
                Actor a = (Actor) o;
                if (a.getName().equals(name)) {
                    favourites.remove(o);
                    return;
                }
            }
        }
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public SortedSet<Object> getFavourites() {
        return favourites;
    }

    public void setFavourites(SortedSet<Object> favourites) {
        this.favourites = favourites;
    }

    public static class InformationBuilder{
        private Credentials credentials;
        private String name;
        private String country;
        private Integer age;
        private Gender gender;
        private LocalDate dateOfBirth;

        public InformationBuilder setCredentials(Credentials credentials) {
            this.credentials = credentials;
            return this;
        }

        public InformationBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public InformationBuilder setCountry(String country){
            this.country = country;
            return this;
        }

        public InformationBuilder setAge(int age) {
            this.age = age;
            return this;
        }

        public InformationBuilder setGender(Gender gender){
            this.gender = gender;
            return this;
        }

        public InformationBuilder setDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Information build(User user) {
            return user.new Information(credentials, name, country,
                    age, gender, dateOfBirth);
        }
    }
    public class Information{

        public Information(Credentials credentials, String name,
                           String country, Integer age,
                           Gender gender, LocalDate dateOfBirth) {
            this.credentials = credentials;
            this.name = name;
            this.country = country;
            this.age = age;
            this.gender = gender;
            this.dateOfBirth = dateOfBirth;
        }

        private Credentials credentials;
        private String name;
        private String country;
        private Integer age;
        private Gender gender;
        private LocalDate dateOfBirth;

        public Credentials getCredentials() {
            return credentials;
        }

        public void setCredentials(Credentials credentials) {
            this.credentials = credentials;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Gender getGender() {
            return gender;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public LocalDate getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        @Override
        public String toString() {
            return "Information{" +
                    "credentials=" + credentials.toString() +
                    ", name='" + name + '\'' +
                    ", country='" + country + '\'' +
                    ", age=" + age +
                    ", gender=" + gender +
                    ", dateOfBirth=" + dateOfBirth +
                    '}';
        }
    }
}
