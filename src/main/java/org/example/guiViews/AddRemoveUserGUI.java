package org.example.guiViews;

import javax.swing.*;
import java.awt.*;

public class AddRemoveUserGUI extends JFrame {
    public AddRemoveUserGUI() {

        JPanel addUserPanel = new JPanel(new FlowLayout());
        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(e -> {
            new AddUserGUI();
        });
        addUserPanel.add(addUserButton);

        JPanel removeUserPanel = new JPanel(new FlowLayout());
        JButton removeUserButton = new JButton("Remove User");
        removeUserButton.addActionListener(e -> {
            new RemoveUserGUI();
        });
        removeUserPanel.add(removeUserButton);

        setLayout(new GridLayout(2, 1));

        add(addUserPanel);
        add(removeUserPanel);

        setSize(600, 400);
        setVisible(true);
    }
}
