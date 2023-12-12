package org.example.guiViews;

import org.example.Actor;
import org.example.LoggedUser;
import org.example.Production;
import org.example.Staff;
import org.example.cliViews.HomeView;

import javax.swing.*;
import java.awt.*;

public class DeleteItemGUI extends JFrame {

    public DeleteItemGUI() {


        JLabel label = new JLabel("Enter the name of the item you want to delete");

        JTextField textField = new JTextField();
        textField.setColumns(15);

        JButton deleteButton = new JButton("Delete");

        JLabel infoLabel = new JLabel();

        deleteButton.addActionListener(e -> {
            String input = textField.getText();

            Object fetched = HomeView.fetch(input);

            if (fetched == null) {
                infoLabel.setText("No result found for: " + input);
                return;
            }

            if (fetched instanceof Actor) {
                Actor actor = (Actor) fetched;
                String name = actor.getName();
                ((Staff)LoggedUser.currentUser).removeActorSystem(name);
                infoLabel.setText(name + " has been removed successfully from the system");
            } else if (fetched instanceof Production) {
                Production production = (Production) fetched;
                String title = production.getTitle();
                ((Staff)LoggedUser.currentUser).removeProductionSystem(title);
                infoLabel.setText(title + " has been removed successfully from the system");

            }
        });

        setLayout(new GridLayout(4, 1));

        JPanel labelFlow = new JPanel(new FlowLayout());
        labelFlow.add(label);

        JPanel textFieldFlow = new JPanel(new FlowLayout());
        textFieldFlow.add(textField);

        JPanel deleteButtonFlow = new JPanel(new FlowLayout());
        deleteButtonFlow.add(deleteButton);

        JPanel infoLabelFlow = new JPanel(new FlowLayout());
        infoLabelFlow.add(infoLabel);

        add(labelFlow);
        add(textFieldFlow);
        add(deleteButtonFlow);
        add(infoLabelFlow);

        setLocationRelativeTo(null);
        setSize(600, 400);
        setVisible(true);
    }
}
