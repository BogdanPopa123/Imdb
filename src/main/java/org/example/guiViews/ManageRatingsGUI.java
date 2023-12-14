package org.example.guiViews;

import org.example.*;
import org.example.cliViews.HomeView;

import javax.swing.*;
import java.awt.*;

public class ManageRatingsGUI extends JFrame {
    public ManageRatingsGUI() {

        JPanel textFieldPanel = new JPanel(new FlowLayout());
        JLabel searchLabel = new JLabel("Name of Actor/Production");
        JTextField textField = new JTextField();
        textField.setColumns(15);
        textFieldPanel.add(searchLabel);
        textFieldPanel.add(textField);

        JPanel textAreaPanel = new JPanel(new FlowLayout());
        JLabel textAreaLabel = new JLabel("Comment: ");
        JTextArea textArea = new JTextArea();
        textArea.setColumns(20);
        textArea.setRows(4);
        textAreaPanel.add(textAreaLabel);
        textAreaPanel.add(textArea);

        JPanel gradePanel = new JPanel(new FlowLayout());
        JLabel gradeLabel = new JLabel("Grade: ");
        SpinnerNumberModel numberModel = new SpinnerNumberModel(5,
                1, 10 ,1);
        JSpinner gradeSpinner = new JSpinner(numberModel);
        gradePanel.add(gradeLabel);
        gradePanel.add(gradeSpinner);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JPanel buttonsGrid = new JPanel(new GridLayout(3, 1));
        JButton addRatingButton = new JButton("Add Rating");
        JButton removeRatingButton = new JButton("Remove Rating");
        JLabel infoLabel = new JLabel();
        buttonsGrid.add(addRatingButton);
        buttonsGrid.add(removeRatingButton);
        buttonsGrid.add(infoLabel);
        buttonsPanel.add(buttonsGrid);

        addRatingButton.addActionListener(e -> {
            String input = textField.getText().trim();
            Object fetched = HomeView.fetch(input);

            if (fetched == null) {
                infoLabel.setText("No result found for: " + input);
                return;
            }

            if (fetched instanceof Production) {
                Production production = (Production) fetched;
                //verific daca userul a adaugat deja un comment
                for (Rating rating : production.getRatings()) {
                    if (rating.getUsername().equals(LoggedUser.currentUser.getUsername())) {
                        infoLabel.setText("You already added a comment on this production");
                        return;
                    }
                }

                if (textArea.getText() != null && !textArea.getText().trim().equals("")) {
                    Rating rating = new Rating(LoggedUser.currentUser.getUsername(),
                            (int) gradeSpinner.getValue(), textArea.getText().trim(),
                            production.getTitle());
                    ((Regular)LoggedUser.currentUser).createRatingProduction(rating);
                    infoLabel.setText("Rating for " + rating.getSubject()
                            + " added successfully");
                } else {
                    infoLabel.setText("Please complete all fields! ");
                    return;
                }
            } else if (fetched instanceof Actor) {
                Actor actor = (Actor) fetched;
                //verific daca userul a adaugat deja un comment;
                for (Rating rating : actor.getRatings()) {
                    if (rating.getUsername().equals(LoggedUser.currentUser.getUsername())) {
                        infoLabel.setText("You already added a comment on this actor");
                        return;
                    }
                }

                if (textArea.getText() != null && !textArea.getText().trim().equals("")) {
                    Rating rating = new Rating(LoggedUser.currentUser.getUsername(),
                            (int) gradeSpinner.getValue(), textArea.getText().trim(),
                            actor.getName());
                    ((Regular)LoggedUser.currentUser).createRatingActor(rating);
                    infoLabel.setText("Rating for " + rating.getSubject()
                            + " added successfully");
                } else {
                    infoLabel.setText("Please complete all fields! ");
                    return;
                }
            }
        });

        removeRatingButton.addActionListener(e -> {
            String input = textField.getText().trim();
            Object fetched = HomeView.fetch(input);

            if (fetched == null) {
                infoLabel.setText("No result found for: " + input);
                return;
            }

            if (fetched instanceof Production) {
                Production production = (Production) fetched;
                for (Rating rating : production.getRatings()) {
                    if (rating.getUsername().equals(LoggedUser.currentUser.getUsername())){
                        production.getRatings().remove(rating);
                        infoLabel.setText("Removed comment successfully ");
                        return;
                    }
                }

                infoLabel.setText("You have not added any comment for "
                        + production.getTitle());
            } else if (fetched instanceof Actor) {
                Actor actor = (Actor) fetched;
                for (Rating rating : actor.getRatings()) {
                    if (rating.getUsername().equals(LoggedUser.currentUser.getUsername())) {
                        actor.getRatings().remove(rating);
                        infoLabel.setText("Removed comment successfully ");
                        return;
                    }
                }

                infoLabel.setText("You have not added any comment for "
                        + actor.getName());
            }
        });

        setLayout(new GridLayout(4, 1));
        add(textFieldPanel);
        add(textAreaPanel);
        add(gradePanel);
        add(buttonsPanel);

        setSize(600, 400);
        setVisible(true);
    }
}
