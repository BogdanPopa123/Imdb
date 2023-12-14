package org.example.guiViews;

import org.example.Admin;
import org.example.LoggedUser;
import org.example.User;

import javax.swing.*;
import java.awt.*;

public class RemoveUserGUI extends JFrame {
    public RemoveUserGUI(){

        JPanel textFieldPanel = new JPanel(new FlowLayout());
        JTextField textField = new JTextField();
        textField.setColumns(15);
        textFieldPanel.add(new JLabel("Username of user you want to remove:"));
        textFieldPanel.add(textField);

        JPanel infoLabelPanel = new JPanel(new FlowLayout());
        JLabel infoLabel = new JLabel();
        infoLabelPanel.add(infoLabel);

        JPanel removeButtonPanel = new JPanel(new FlowLayout());
        JButton removeButton = new JButton("Remove User");
        removeButtonPanel.add(removeButton);
        removeButton.addActionListener(e -> {
            String username = textField.getText();
            User user;
            user = ((Admin) LoggedUser.currentUser).removeUser(username);
            if (user == null) {
                infoLabel.setText("No user with username " + username + "  found");
            } else {
                infoLabel.setText(username + " removed successfully");
            }
        });

        setLayout(new GridLayout(3, 1));
        add(textFieldPanel);
        add(removeButtonPanel);
        add(infoLabelPanel);

        setLocationRelativeTo(null);
        setSize(600, 400);
        setVisible(true);
    }
}
