package org.example.guiViews;

import org.example.Actor;
import org.example.LoggedUser;
import org.example.Staff;
import org.example.cliViews.HomeView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AddActorGUI extends JFrame {
    public AddActorGUI() {

        Actor actor = new Actor(null, new ArrayList<Actor.Pair>(),
                null, LoggedUser.currentUser.getUsername(),
                null);

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
            new EnterPerformances(actor, (int) spinner.getValue());
        });
        spinnerPanel.add(button);

        JPanel urlPanel = new JPanel(new FlowLayout());
        urlPanel.add(urlLabel);
        urlPanel.add(urlTextField);



        JPanel saveButtonPanel = new JPanel(new FlowLayout());
        JLabel saveActorInfo = new JLabel("");
        JButton saveButton = new JButton("Save Actor");
        saveButtonPanel.add(saveButton);
        saveButtonPanel.add(saveActorInfo);
        saveButton.addActionListener(e -> {
            if (nameTextField.getText() != null && !nameTextField.getText().trim().equals("")
            && bioTextArea.getText() != null && !bioTextArea.getText().trim().equals("")
            && urlTextField.getText() != null && !urlTextField.getText().trim().equals("")
            && AddSeriesGUI.checkUrlString(urlTextField.getText().trim())) {

                actor.setName(nameTextField.getText().trim());
                actor.setBiography(bioTextArea.getText().trim());
                actor.setImageUrl(urlTextField.getText().trim());
                actor.setAverageRating(actor.calculateAverageRating());

                Object fetchResult = HomeView.fetch(actor.getName());

                if (fetchResult == null) {
                    ((Staff)LoggedUser.currentUser).addActorSystem(actor);
                    saveActorInfo.setText("Actor saved successfully");
                } else {
                    Actor fetchResultActor = (Actor) fetchResult;
                    saveActorInfo.setText("Actor " + fetchResultActor.getName() +
                            " already exists");
                }

            } else {
                saveActorInfo.setText("Complete all fields");
            }
        });

        setLayout(new GridLayout(5, 1));

        add(namePanel);
        add(bioPanel);
        add(urlPanel);
        add(spinnerPanel);
        add(saveButtonPanel);

        setSize(600, 400);
        setVisible(true);
    }


}

class EnterPerformances extends JFrame{

    List<Actor.Pair> performances = new ArrayList<>();

    List<Pairr<JTextField, JTextField>> performancesFields = new ArrayList<>();
    public EnterPerformances(Actor actor, int num) {
        actor.setPerformances(new ArrayList<>());

        setLayout(new GridLayout(num + 1, 1));

        for (int i = 0; i < num; i ++) {
            JPanel panel = new JPanel(new FlowLayout());

            panel.add(new JLabel("Performance name"));
            JTextField textField1 = new JTextField();
            textField1.setColumns(15);
            panel.add(textField1);

            panel.add(new JLabel("Type"));
            JTextField textField2 = new JTextField();
            textField2.setColumns(15);
            panel.add(textField2);

            Pairr<JTextField, JTextField> pairr = Pairr
                    .of(textField1, textField2);
            performancesFields.add(pairr);

            add(panel);



        }
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addPerformancesButton = new JButton("Add performances");
        buttonPanel.add(addPerformancesButton);

        addPerformancesButton.addActionListener(e -> {
            for (Pairr<JTextField, JTextField> pairr1 : performancesFields) {
                String name = pairr1.getKey().getText();
                String type = pairr1.getValue().getText();

                if (name != null && !name.trim().equals("")
                        && type != null && !type.trim().equals("")) {
                    actor.addPerformance(name, type);
//                            performances.add(new Actor.Pair(name ,type));
                }
            }

            dispose();
        });

        add(buttonPanel);

        setSize(600, 400);
        setVisible(true);
    }
}
