package org.example.guiViews;

import org.example.*;
import org.example.enums.RequestType;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class AutomatedRequestGUI extends JFrame {
    public  AutomatedRequestGUI(Object object){

        JPanel textAreaPanel = new JPanel(new FlowLayout());
        JTextArea textArea = new JTextArea();
        textArea.setColumns(15);
        textArea.setRows(3);
        textAreaPanel.add(new JLabel("Describe your problem"));
        textAreaPanel.add(textArea);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JLabel infoLabel = new JLabel();
        JButton addRequest = new JButton("Add Request");
        addRequest.addActionListener(e -> {
            String subject = null;
            RequestType requestType = null;
            String solver = null;

            if (object instanceof Production) {
                subject = ((Production)object).getTitle();
                requestType = RequestType.MOVIE_ISSUE;
                solver = ((Production)object).getAuthor();
            } else if (object instanceof Actor) {
                subject = ((Actor)object).getName();
                requestType = RequestType.ACTOR_ISSUE;
                solver = ((Actor)object).getAuthor();
            }

            if (textArea.getText() != null && !textArea.getText().trim().equals("")){
                if (solver == null || solver.equals("")) {
                    solver = "ADMIN";
                }
                Request request = new Request(requestType, LocalDateTime.now(),
                        subject, textArea.getText().trim(),
                        LoggedUser.currentUser.getUsername(), solver);

                if (LoggedUser.currentUser instanceof Regular){
                    Regular regular = (Regular) LoggedUser.currentUser;
                    regular.createRequest(request);
                    CreateRequestGUI.requestList.add(request);
                    infoLabel.setText("Request added successfully");
                } else if (LoggedUser.currentUser instanceof Contributor) {
                    Contributor contributor = (Contributor) LoggedUser.currentUser;
                    contributor.createRequest(request);
                    CreateRequestGUI.requestList.add(request);
                    infoLabel.setText("Request added successfully");
                }

            } else {
                infoLabel.setText("Complete all fields");
            }
        });
        buttonPanel.add(infoLabel);
        buttonPanel.add(addRequest);

        setLayout(new GridLayout(2, 1));
        add(textAreaPanel);
        add(buttonPanel);

        setSize(400, 300);
        setVisible(true);
    }
}
