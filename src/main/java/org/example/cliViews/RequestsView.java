package org.example.cliViews;

import org.example.*;
import org.example.enums.RequestType;
import org.example.exceptions.InvalidCommandException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RequestsView {

    public static List<Request> requestList = new ArrayList<>();

    public static void show() {
        listCurrentRequests();
        showMenu();
    }

    private static void listCurrentRequests() {
        RequestsView.requestList = new ArrayList<>();
        for (Request request : Request.RequestManager.getAdminTeamRequests()) {
            if(request.getIssuerUsername()
                    .equals(LoggedUser.currentUser.getUsername())) {
                RequestsView.requestList.add(request);
            }
        }

        for (User user : IMDB.getInstance().getUsers()){
            if (user instanceof Staff) {
                Staff staff = (Staff) user;
                for (Request request : staff.getRequests()) {
                    if (request.getIssuerUsername()
                            .equals(LoggedUser.currentUser.getUsername())){
                        RequestsView.requestList.add(request);
                    }
                }
            }
        }

        if (requestList.size() == 0) {
            System.out.println();
            System.out.println("You currently have no active request");
            System.out.println();
        } else {
            System.out.println();
            System.out.println("Your current requests are:");
            int index = 0;
            for (Request request :  RequestsView.requestList) {
                index++;
                System.out.println("    " + index + ") " +
                        "Created at: " + request.getCreationTime().toString());
                System.out.println("    Description: " +
                        request.getDescription());
            }
            System.out.println();
        }

    }

    private static void showMenu(){
        System.out.println();
        System.out.println("What would you like to do next?\n");
        System.out.println("    1) Create a new request");
        System.out.println("    2) Remove a request");
        System.out.println("    3) List requests");
        System.out.println("    4) Return");

        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            System.out.print("Enter a command: ");
            command = scanner.nextLine();

            try{
                validateCommand(command);
                if (command.equals("1")) {
                    addRequest();
                } else if (command.equals("2")) {
                    removeRequest();
                } else if (command.equals("3")) {
                    System.out.println();
                    listCurrentRequests();
                    showMenu();
                } else if (command.equals("4")) {
                    System.out.println();
                    System.out.println();
                    HomeView.show();
                }
            }catch(InvalidCommandException e){
                System.out.println("Invalid command : " + command);
                System.out.println(e.getMessage());
            }
        }while(!command.equals("1") && !command.equals("2")
                &&!command.equals("3") &&!command.equals("4"));
    }

    private static void addRequest(){
        System.out.println();
        System.out.println("Enter the name of the actor/series/movie " +
                "you want to create a request about");
        System.out.println("If this is not the case press enter");

        RequestType type = null;
        LocalDateTime creationTime;
        String subject = null, description, from, to = null;


        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        if (!command.equals("")) {
            Object obj = HomeView.fetch(command);
            if (obj == null) {
                System.out.println("No actor/series/movie with " +
                        "given name has been found");
                RequestsView.showMenu();
            } else {
                if (obj instanceof Actor) {
                    Actor fetched = (Actor) obj;
                    type = RequestType.ACTOR_ISSUE;
                    to = fetched.getAuthor();
                    subject = fetched.getName();
                } else if (obj instanceof Production) {
                    Production fetched = (Production) obj;
                    type = RequestType.MOVIE_ISSUE;
                    to = fetched.getAuthor();
                    subject = fetched.getTitle();
                }
                if (to == null) {
                    to = "ADMIN";
                }
                from = LoggedUser.currentUser.getUsername();
                System.out.println("Describe your problem: ");
                description = scanner.nextLine();
                creationTime = LocalDateTime.now();

                Request request = new Request(type, creationTime,
                        subject, description, from, to);

                if (LoggedUser.currentUser instanceof Regular){
                    Regular regular = (Regular) LoggedUser.currentUser;
                    regular.createRequest(request);
                    RequestsView.requestList.add(request);
                    System.out.println("Request created successfully");
                    RequestsView.showMenu();
                } else if (LoggedUser.currentUser instanceof Contributor) {
                    Contributor contributor = (Contributor) LoggedUser.currentUser;
                    contributor.createRequest(request);
                    RequestsView.requestList.add(request);
                    System.out.println("Request created successfully");
                    RequestsView.showMenu();
                }
            }
        } else {
            //daca comanda este NULL inseamna ca userul doreste
            //fie sa faca DELETE_REQUEST sau OTHER REQUEST
            System.out.println();
            System.out.println("If you wish to delete your account " +
                    "press Enter.");
            System.out.println("If you wish to create other kind of " +
                    "request, type anything and then press Enter.");

            String secondCommand = scanner.nextLine();
            if (secondCommand.equals("")) {
                //cerere de delete account
                type = RequestType.DELETE_ACCOUNT;
            } else {
                type = RequestType.OTHERS;
            }
                System.out.print("Describe your problem: ");
                description = scanner.nextLine();
                subject = null;
                from = LoggedUser.currentUser.getUsername();
                to = "ADMIN";
                creationTime = LocalDateTime.now();
                Request request = new Request(type, creationTime,
                        subject, description, from, to);

                if (LoggedUser.currentUser instanceof Regular){
                    Regular regular = (Regular) LoggedUser.currentUser;
                    regular.createRequest(request);
                    RequestsView.requestList.add(request);
                    System.out.println("Request created successfully");
                    RequestsView.showMenu();
                } else if (LoggedUser.currentUser instanceof Contributor) {
                    Contributor contributor = (Contributor) LoggedUser.currentUser;
                    contributor.createRequest(request);
                    RequestsView.requestList.add(request);
                    System.out.println("Request created successfully");
                    RequestsView.showMenu();
                }
        }
    }

    private static void removeRequest() {
        Scanner scanner = new Scanner(System.in);
        String command;
        Integer size = RequestsView.requestList.size();

        if (RequestsView.requestList.size() == 0) {
            System.out.println("You currently have no active request");
            System.out.println();
            showMenu();
        }

        Integer index = -1;
        do {
            System.out.println();
            listCurrentRequests();
            System.out.println("Choose which request you want to remove");
            System.out.println("Choose a number in range 1 - " + size);
            System.out.println("Choose 0 to cancel");
            command = scanner.nextLine();

            try{
                validateDeleteCommand(command, size);
                index = Integer.parseInt(command);

                if (index == 0) {
                    System.out.println();
                    System.out.println();
                    RequestsView.showMenu();
                }

                index = index - 1;

                Request request = RequestsView.requestList.get(index);
                RequestsView.requestList.remove((int)index);

                if (LoggedUser.currentUser instanceof Regular){
                    Regular regular = (Regular) LoggedUser.currentUser;
                    regular.removeRequest(request);
                } else if (LoggedUser.currentUser instanceof Contributor) {
                    Contributor contributor = (Contributor) LoggedUser.currentUser;
                    contributor.removeRequest(request);
                }


                System.out.println();
                System.out.println("Successfully removed the request");

            }catch(InvalidCommandException e){
                System.out.println("Invalid command: " + command);
                System.out.println(e.getMessage());
            }
        }while(index < 0 && index >= RequestsView.requestList.size());

        System.out.println();
        RequestsView.showMenu();
    }

    private static void validateCommand(String command)
            throws InvalidCommandException{
        if (!command.equals("1") && !command.equals("2")
                && !command.equals("3") && !command.equals("4")) {
            throw new InvalidCommandException("Please enter a valid command.\n" +
                    "Choose an integer in range 1 - 4");
        }
    }

    private static void validateDeleteCommand(String command, Integer size)
            throws InvalidCommandException{
        int throwFlag = 1;
        for (int i = 0; i <= size; i++) {
            Integer num = i;
            if (command.equals(num.toString())) {
                throwFlag = 0;
            }
        }

        if (throwFlag == 1) {
            throw new InvalidCommandException("Please enter a valid command.\n" +
                    "Choose an integer in range 1 - " + size);
        }
    }

}
