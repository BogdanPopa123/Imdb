package org.example.cliViews;

import org.example.*;
import org.example.exceptions.InvalidCommandException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SolveRequestsView {

    public static List<Request> requestList = new ArrayList<>();

    public static void show() {
        listRequests();
        showMenu();
    }

    public static void showMenu(){
        Scanner scanner = new Scanner(System.in);
        String command;
        Integer size = SolveRequestsView.requestList.size();

        if (size == 0) {
            System.out.println();
            System.out.println("You currently have no request to solve");
            HomeView.show();
        }

        int index = -1;

        do {
            System.out.println();
            listRequests();
            System.out.println("Choose which request you want to solve");
            System.out.println("Choose a number in range 1 - " + size);
            System.out.println("Choose 0 to cancel");
            command = scanner.nextLine();

            try{
                validateChooseCommand(command, size);
                index = Integer.parseInt(command);

                if (index == 0) {
                    System.out.println();
                    System.out.println();
                    HomeView.show();
                }

                index = index - 1;

                Request request = SolveRequestsView.requestList.get(index);
                System.out.println();

                SolveRequestsView.showChooseMenu(request);

            }catch(InvalidCommandException e){
                System.out.println("Invalid command: " + command);
                System.out.println(e.getMessage());
            }
        }while(index < 0 && index >= SolveRequestsView.requestList.size());

    }

    private static void showChooseMenu(Request request) {

        Scanner scanner = new Scanner(System.in);
        String command;
        Integer size = SolveRequestsView.requestList.size();
        int index = -1;

        System.out.println();
        System.out.println("You have chosen the following request: ");
        System.out.println("    Created by: " +
                request.getIssuerUsername());
        System.out.println("    Created at: " + request.getCreationTime());
        System.out.println("    Type of request: " +
                request.getRequestType().toString());
        System.out.println("    Subject: " + request.getSubject());
        System.out.println("    To: " + request.getSolverUsername());
        System.out.println("    Description: " + request.getDescription());


        do {
            System.out.println();
            System.out.println("What would you like to do with this request?");
            System.out.println("    1) Mark as solved");
            System.out.println("    2) Mark as denied");
            System.out.println("    Choose 0 to cancel");
            command = scanner.nextLine();

            try{
                validateChooseCommand(command, 2);

                Staff staff = (Staff) LoggedUser.currentUser;

                if (command.equals("0")) {
                    HomeView.show();
                } else if (command.equals("1")) {
                    staff.solveRequest(request);
                    SolveRequestsView.showMenu();
                } else if (command.equals("2")) {
                    staff.denyRequest(request);
                    SolveRequestsView.showMenu();
                }

            }catch(InvalidCommandException e){
                System.out.println("Invalid command: " + command);
                System.out.println(e.getMessage());
            }
        }while(!command.equals("0") && !command.equals("1")
        && !command.equals("2"));
    }

    public static void listRequests(){
        SolveRequestsView.requestList = new ArrayList<>();

        for (Request request : Request.RequestManager.getAdminTeamRequests()) {
            SolveRequestsView.requestList.add(request);
        }

        Staff staff = (Staff) LoggedUser.currentUser;
        for (Request request : staff.getRequests()) {
            SolveRequestsView.requestList.add(request);
        }

        if (SolveRequestsView.requestList.size() == 0) {
            System.out.println("You have no active request to be solved");
        }

        int index = 0;
        System.out.println();
        System.out.println("These are your requests");
        System.out.println();
        for (Request request : SolveRequestsView.requestList) {
            index ++;
            System.out.println("    " + index + ") Created by: " +
                    request.getIssuerUsername());
            System.out.println("    Created at: " + request.getCreationTime());
            System.out.println("    Type of request: " +
                    request.getRequestType().toString());
            System.out.println("    Subject: " + request.getSubject());
            System.out.println("    To: " + request.getSolverUsername());
            System.out.println("    Description: " + request.getDescription());
            System.out.println();
        }
    }

    private static void validateChooseCommand(String command, Integer size)
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
