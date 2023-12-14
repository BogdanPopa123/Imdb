package org.example.guiViews;

import org.example.*;
import org.example.cliViews.HomeView;
import org.example.cliViews.RequestsView;
import org.example.enums.RequestType;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateRequestGUI extends JFrame {

    public static List<Request> requestList = new ArrayList<>();
    public CreateRequestGUI(){

        updateList();
        JPanel listButtonFlow = new JPanel(new FlowLayout());
        JButton listButton = new JButton("List your current requests");
        listButton.addActionListener(e -> {
            JDialog dialog = new JDialog();
            dialog.setTitle("Manage Your Requests");

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);

            textArea.setText("");
            textArea.append(getRequestString());

            JScrollPane scrollPane = new JScrollPane(textArea);
            dialog.add(scrollPane);

            dialog.setSize(600, 400);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        JPanel spinnerPanel = new JPanel(new FlowLayout());
        updateList();
        SpinnerNumberModel numberModel = new SpinnerNumberModel(1, 1,
                CreateRequestGUI.requestList.size() > 0 ? CreateRequestGUI.requestList.size() : 1,
                1);
        JSpinner spinner = new JSpinner(numberModel);
        JButton deleteButton = new JButton("Delete request");
        deleteButton.addActionListener(e -> {
            int index = (int) spinner.getValue();
            index--;
            Request request = CreateRequestGUI.requestList.get(index);
            CreateRequestGUI.requestList.remove(request);

            if (LoggedUser.currentUser instanceof Regular){
                Regular regular = (Regular) LoggedUser.currentUser;
                regular.removeRequest(request);
            } else if (LoggedUser.currentUser instanceof Contributor) {
                Contributor contributor = (Contributor) LoggedUser.currentUser;
                contributor.removeRequest(request);
            }

            if (CreateRequestGUI.requestList.size() == 0){
//            spinner.setEditor(null);
                spinner.setEnabled(false);
                deleteButton.setEnabled(false);
            }

            if (CreateRequestGUI.requestList.size() != 0) {
                spinner.setEnabled(true);
                deleteButton.setEnabled(true);
            }

            spinner.setValue(1);
            numberModel.setMaximum(CreateRequestGUI.requestList.size() > 0 ?
                    CreateRequestGUI.requestList.size() : 1);
        });

        if (CreateRequestGUI.requestList.size() == 0){
//            spinner.setEditor(null);
            spinner.setEnabled(false);
            deleteButton.setEnabled(false);
        }

        if (CreateRequestGUI.requestList.size() != 0) {
            spinner.setEnabled(true);
            deleteButton.setEnabled(true);
        }

        spinnerPanel.add(new JLabel("Choose index"));
        spinnerPanel.add(spinner);
        spinnerPanel.add(deleteButton);


        listButtonFlow.add(listButton);

        JPanel createRequestPanel = new JPanel(new FlowLayout());
        createRequestPanel.add(new JLabel("Create request"));
        List<RequestType> requestTypes = Arrays.asList(RequestType.values());
        DefaultComboBoxModel<RequestType> comboBoxModel = new DefaultComboBoxModel<>(requestTypes.toArray(new RequestType[0]));

        JComboBox<RequestType> typeComboBox = new JComboBox<>(comboBoxModel);
        createRequestPanel.add(typeComboBox);

        JPanel createRequestPanel2 = new JPanel(new FlowLayout());
        createRequestPanel2.add(new JLabel("Type Actor/Production name"));
        JTextField textField = new JTextField();
        textField.setColumns(15);
        createRequestPanel2.add(textField);
        JTextArea textArea = new JTextArea();
        createRequestPanel2.add(new JLabel("Describe your problem: "));
        textArea.setColumns(30);
        createRequestPanel2.add(textArea);
        JButton addRequestButton = new JButton("Create Request");
        JLabel createRequestStatus = new JLabel();
        addRequestButton.addActionListener(e -> {
            String input = textField.getText();
            Object fetched = HomeView.fetch(input);
            if (fetched == null &&
                    (typeComboBox.getSelectedItem().equals(RequestType.ACTOR_ISSUE)
                     || typeComboBox.getSelectedItem().equals(RequestType.MOVIE_ISSUE))) {
                createRequestStatus.setText("No result found for: " + input +
                        "\n Request has been cancelled");
                return;
            }

            if (fetched instanceof Actor
                    && typeComboBox.getSelectedItem().equals(RequestType.ACTOR_ISSUE)) {
                Actor actor = (Actor) fetched;
                String to = actor.getAuthor();
                if (to == null) {
                    to = "ADMIN";
                }
                Request request = new Request(RequestType.ACTOR_ISSUE,
                        LocalDateTime.now(), actor.getName(), textArea.getText(),
                        LoggedUser.currentUser.getUsername(), to);
                if (LoggedUser.currentUser instanceof Regular){
                    Regular regular = (Regular) LoggedUser.currentUser;
                    regular.createRequest(request);
                    CreateRequestGUI.requestList.add(request);
                    createRequestStatus.setText("Request added successfully");
                } else if (LoggedUser.currentUser instanceof Contributor) {
                    Contributor contributor = (Contributor) LoggedUser.currentUser;
                    contributor.createRequest(request);
                    CreateRequestGUI.requestList.add(request);
                    createRequestStatus.setText("Request added successfully");
                }
            } else if (fetched instanceof Production
                && typeComboBox.getSelectedItem().equals(RequestType.MOVIE_ISSUE)) {
                Production production = (Production) fetched;
                String to = production.getAuthor();
                if (to == null) {
                    to = "ADMIN";
                }
                Request request = new Request(RequestType.MOVIE_ISSUE,
                        LocalDateTime.now(), production.getTitle(), textArea.getText(),
                        LoggedUser.currentUser.getUsername(), to);
                if (LoggedUser.currentUser instanceof Regular){
                    Regular regular = (Regular) LoggedUser.currentUser;
                    regular.createRequest(request);
                    CreateRequestGUI.requestList.add(request);
                    createRequestStatus.setText("Request added successfully");
                } else if (LoggedUser.currentUser instanceof Contributor) {
                    Contributor contributor = (Contributor) LoggedUser.currentUser;
                    contributor.createRequest(request);
                    CreateRequestGUI.requestList.add(request);
                    createRequestStatus.setText("Request added successfully");
                }
            } else if (typeComboBox.getSelectedItem().equals(RequestType.DELETE_ACCOUNT)
                || typeComboBox.getSelectedItem().equals(RequestType.OTHERS)) {
                RequestType type = (RequestType) comboBoxModel.getSelectedItem();

                Request request = new Request(type, LocalDateTime.now(), null,
                        textArea.getText(), LoggedUser.currentUser.getUsername(),
                        "ADMIN");
                if (LoggedUser.currentUser instanceof Regular){
                    Regular regular = (Regular) LoggedUser.currentUser;
                    regular.createRequest(request);
                    CreateRequestGUI.requestList.add(request);
                    createRequestStatus.setText("Request added successfully");
                } else if (LoggedUser.currentUser instanceof Contributor) {
                    Contributor contributor = (Contributor) LoggedUser.currentUser;
                    contributor.createRequest(request);
                    CreateRequestGUI.requestList.add(request);
                    createRequestStatus.setText("Request added successfully");
                }
            }

            updateList();

            if (CreateRequestGUI.requestList.size() != 0) {
                deleteButton.setEnabled(true);
                spinner.setEnabled(true);
            }

            numberModel.setMaximum(CreateRequestGUI.requestList.size());
        });
        createRequestPanel2.add(addRequestButton);

        JPanel createRequestGrid = new JPanel(new GridLayout(3, 1));
        createRequestGrid.add(createRequestPanel);
        createRequestGrid.add(createRequestPanel2);
        createRequestGrid.add(createRequestStatus);

        setLayout(new GridLayout(3,1));
        add(listButtonFlow);
        add(spinnerPanel);
        add(createRequestGrid);

        pack();
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the dialog on the screen
        setVisible(true);
    }

    private void updateList() {
        CreateRequestGUI.requestList.clear();
        CreateRequestGUI.requestList = new ArrayList<>();
        for (Request request : Request.RequestManager.getAdminTeamRequests()) {
            if(request.getIssuerUsername()
                    .equals(LoggedUser.currentUser.getUsername())) {
                CreateRequestGUI.requestList.add(request);
            }
        }

        for (User user : IMDB.getInstance().getUsers()){
            if (user instanceof Staff) {
                Staff staff = (Staff) user;
                for (Request request : staff.getRequests()) {
                    if (request.getIssuerUsername()
                            .equals(LoggedUser.currentUser.getUsername())){
                        CreateRequestGUI.requestList.add(request);
                    }
                }
            }
        }
    }

    private String getRequestString(){
        String type = null;
        StringBuilder sb = new StringBuilder();
        updateList();

        if (CreateRequestGUI.requestList.size() == 0) {
            sb.append("You currently have no active request\n");
            return sb.toString();
        }

        sb.append("These are your requests:" +
                LoggedUser.currentUser.getUsername() + "\n");

        int index = 0;
        for (Request request : CreateRequestGUI.requestList) {
            index++;
            sb.append("    " + index + ") Created at: " +
                    request.getCreationTime().toString() +
                    "\n    Type: " + request.getRequestType().toString() +
                    "\n    Description: " +
                    request.getDescription() + "\n");
        }

        return sb.toString();
    }
}
