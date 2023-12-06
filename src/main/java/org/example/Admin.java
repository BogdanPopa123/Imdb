package org.example;

import org.example.enums.AccountType;
import org.example.enums.Gender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Admin extends Staff{

    public Admin(Information information, AccountType accountType,
                 String username, Integer experience,
                 List<String> notifications, SortedSet<Object> favourites,
                 List<Request> requests, SortedSet<Object> addedObjects) {
        super(information, accountType, username, experience,
                notifications, favourites, requests, addedObjects);
    }

    public void addUser(String username, String email,
                        String password, String name, String country,
                        Integer age, Gender gender,
                        LocalDate dateOfBirth, AccountType type){

        UserFactory factory = UserFactory.getInstance();
        User.InformationBuilder builder = new User.InformationBuilder();
        Credentials credentials = new Credentials(email, password);

        if (type.equals(AccountType.REGULAR)) {
            User regular = factory.createRegular(null, type,
                    username, 0, new ArrayList<>(),
                    new TreeSet<>());
            User.Information info = builder.setCredentials(credentials)
                    .setName(name)
                    .setCountry(country)
                    .setAge(age)
                    .setGender(gender)
                    .setDateOfBirth(dateOfBirth)
                    .build(regular);
            regular.setInformation(info);
            IMDB.getInstance().getUsers().add(regular);
        } else if (type.equals(AccountType.CONTRIBUTOR)) {
            User contributor = factory.createContributor(null, type,
                    username, 0, new ArrayList<>(),
                    new TreeSet<>(), new ArrayList<>(), new TreeSet<>());
            User.Information info = builder.setCredentials(credentials)
                    .setName(name)
                    .setCountry(country)
                    .setAge(age)
                    .setGender(gender)
                    .setDateOfBirth(dateOfBirth)
                    .build(contributor);
            contributor.setInformation(info);
            IMDB.getInstance().getUsers().add(contributor);
        } else if (type.equals(AccountType.ADMIN)) {
            User admin = factory.createAdmin(null, type,
                    username, 0, new ArrayList<>(),
                    new TreeSet<>(), new ArrayList<>(), new TreeSet<>());
            User.Information info = builder.setCredentials(credentials)
                    .setName(name)
                    .setCountry(country)
                    .setAge(age)
                    .setGender(gender)
                    .setDateOfBirth(dateOfBirth)
                    .build(admin);
            admin.setInformation(info);
            IMDB.getInstance().getUsers().add(admin);
        }
    }

    public User removeUser(String username) {
        User removed = null;
        for (User user : IMDB.getInstance().getUsers()) {
            if (user.getUsername().equals(username)){
                removed = user;
                IMDB.getInstance().getUsers().remove(user);
                break;
            }
        }
        return removed;
    }
}
