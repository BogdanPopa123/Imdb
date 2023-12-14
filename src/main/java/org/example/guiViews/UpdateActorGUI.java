package org.example.guiViews;

import org.example.Actor;
import org.example.LoggedUser;
import org.example.Staff;
import org.example.cliViews.HomeView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UpdateActorGUI extends JFrame {
    public UpdateActorGUI() {

        Actor perfDummy = new Actor(null, null,
                null, null, null);

        JLabel nameLabel = new JLabel("Actor name ");
        JLabel bioLabel = new JLabel("Biography ");

        JTextField nameTextField = new JTextField();
        nameTextField.setColumns(15);
        JTextArea bioTextArea = new JTextArea();
        bioTextArea.setColumns(25);
        bioTextArea.setRows(3);

        JLabel numPerformancesLabel = new JLabel("Number of performances ");
        SpinnerNumberModel numberModel = new SpinnerNumberModel(1, 1,
                20, 1);
        JSpinner spinner = new JSpinner(numberModel);

        JLabel urlLabel = new JLabel("Image url (https) ");
        JTextField urlTextField = new JTextField();
        urlTextField.setColumns(15);

        JPanel namePanel = new JPanel(new FlowLayout());
        namePanel.add(nameLabel);
        namePanel.add(nameTextField);

        JPanel bioPanel = new JPanel(new FlowLayout());
        bioPanel.add(bioLabel);
        bioPanel.add(bioTextArea);

        JPanel spinnerPanel = new JPanel(new FlowLayout());
        spinnerPanel.add(numPerformancesLabel);
        spinnerPanel.add(spinner);
        JButton button = new JButton("Choose performances");
        button.addActionListener(e -> {
            new EnterPerformances(perfDummy, (int) spinner.getValue());
        });
        spinnerPanel.add(button);

        JPanel urlPanel = new JPanel(new FlowLayout());
        urlPanel.add(urlLabel);
        urlPanel.add(urlTextField);

        JPanel updateButtonPanel = new JPanel(new FlowLayout());
        JLabel updateActorInfo = new JLabel("");
        JButton updateButton = new JButton("Update Actor");
        updateButtonPanel.add(updateButton);
        updateButtonPanel.add(updateActorInfo);
        updateButton.addActionListener(e -> {
            String input = nameTextField.getText();
            Object feched = HomeView.fetch(input);
            if (!(feched instanceof Actor)) {
                updateActorInfo.setText("No actor found for: " + input);
                return;
            }

            Actor oldActor = (Actor) feched;
            String newBio = null;
            String newUrl = null;

            if (bioTextArea.getText() != null && !bioTextArea.getText().trim().equals("")) {
                newBio = bioTextArea.getText().trim();
            } else {
                newBio = oldActor.getBiography();
            }

            if (urlTextField.getText() != null && !urlTextField.getText().trim().equals("")
            && AddSeriesGUI.checkUrlString(urlTextField.getText().trim())) {
                newUrl = urlTextField.getText().trim();
            } else {
                newUrl = oldActor.getImageUrl();
            }

            List<Actor.Pair> newPerformances = null;
            if (perfDummy.getPerformances() != null
                    && perfDummy.getPerformances().size() > 0) {
                newPerformances = perfDummy.getPerformances();
            } else {
                newPerformances = oldActor.getPerformances();
            }

            String name = oldActor.getName();
            String author = oldActor.getAuthor();

            Actor newActor = new Actor(name, newPerformances,
                    newBio, author, newUrl);

            ((Staff)LoggedUser.currentUser).updateActor(newActor);
            updateActorInfo.setText("Actor " + oldActor.getName() +
                    " updated successfully");

        });

        setLayout(new GridLayout(5, 1));

        add(namePanel);
        add(bioPanel);
        add(urlPanel);
        add(spinnerPanel);
        add(updateButtonPanel);

        setSize(600, 400);
        setVisible(true);
    }
}
