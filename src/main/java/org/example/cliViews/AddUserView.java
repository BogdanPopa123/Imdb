package org.example.cliViews;

import org.example.Admin;
import org.example.LoggedUser;
import org.example.enums.AccountType;
import org.example.enums.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AddUserView {

    public static void show(){
        System.out.println();
        System.out.println("Add a new user to the system");

        System.out.println();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Username: ");
        String username = scanner.nextLine();

        System.out.println("Email: ");
        String email = scanner.nextLine();

        System.out.println("Password: ");
        String password = scanner.nextLine();

        System.out.println("Name: ");
        String name = scanner.nextLine();

        System.out.println("Country: ");
        String country = scanner.nextLine();

        Integer age = null;

        try {
            System.out.println("Age: ");
            String stringAge = scanner.nextLine();
            age = Integer.parseInt(stringAge);
        }catch (NumberFormatException e){
            System.out.println("Registration failed. You need to type a number for age");
            HomeView.show();
        }

        System.out.println("Gender: ");
        String stringGender = scanner.nextLine();
        String initial = stringGender.substring(0, 1);
        Gender gender = Gender.fromString(initial);
        if (gender == null) {
            System.out.println("Registration failed. Type M for male or F for female");
            HomeView.show();
        }

        LocalDate dateOfBirth = null;
        System.out.println("Date of birth (Use the format YYYY-MM-DD): ");
        String dobString = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd");

        try{
            dateOfBirth = LocalDate.parse(dobString, formatter);
        }catch(DateTimeParseException e){
            System.out.println(dobString + " not a valid date");
            System.out.println("Registration failed");
            HomeView.show();
        }

        System.out.println();
        System.out.println("Choose an user type between " +
                "Admin, Contributor, Regular");
        System.out.println("Any misspell will cause the account " +
                "to be created as a regular user");

        System.out.println("Choose user type");
        String stringType = scanner.nextLine();
        stringType = stringType.toUpperCase();
        AccountType type = AccountType.fromString(stringType);
        if (type == null) {
            type = AccountType.REGULAR;
        }

        Admin admin = (Admin) LoggedUser.currentUser;

        admin.addUser(username, email, password, name, country,
                age, gender, dateOfBirth, type);

        System.out.println("User added successfully");
        HomeView.show();
    }
}
