package org.example.guiViews;

import org.example.IMDB;
import org.example.LoggedUser;
import org.example.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginViewGUI extends JFrame {

    public LoginViewGUI(){
        super("Login");

        setLayout(new GridLayout(2, 1));
        JPanel usernameFieldsPanel = new JPanel(new FlowLayout());

        JLabel usernameLabel = new JLabel("Username");
//        usernameLabel.setLocation(50, 50);
        JTextField usernameField = new JTextField();
        usernameField.setColumns(15);
//        usernameField.setLocation(200, 50);
        usernameFieldsPanel.add(usernameLabel);
        usernameFieldsPanel.add(usernameField);

        JPanel passwordFieldsPanel = new JPanel(new FlowLayout());
        JLabel passwordLabel = new JLabel("Password");
//        passwordLabel.setLocation(50, 150);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setColumns(15);
//        passwordField.setLocation(200, 150);
        passwordFieldsPanel.add(passwordLabel);
        passwordFieldsPanel.add(passwordField);

        JLabel infoLabel = new JLabel();
        infoLabel.setForeground(Color.RED);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String (passwordField.getPassword());

                User mathcedUser = null;
                for (User user : IMDB.getInstance().getUsers()) {
                    if (user.getUsername().equals(username)){
                        mathcedUser = user;
                        break;
                    }
                }

                if (mathcedUser == null) {
                    infoLabel.setText("Credentials do not match");
                } else {
                    User.Information info = mathcedUser.getInformation();
                    if (!info.getCredentials().getPassword().equals(password)) {
                        infoLabel.setText("Credentials do not match");
                    } else {
                        LoggedUser.setCurrentUser(mathcedUser);
                        dispose();
                        new HomeViewGUI();
                    }
                }
            }
        });

        setSize(500, 300);


        JPanel fieldsPanel = new JPanel(new GridLayout(2, 1));

        fieldsPanel.add(usernameFieldsPanel);
        fieldsPanel.add(passwordFieldsPanel);

        add(fieldsPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JPanel buttonAndInfo = new JPanel(new GridLayout(2, 1));


        buttonAndInfo.add(loginButton);
        buttonAndInfo.add(infoLabel);

        buttonPanel.add(buttonAndInfo);

        add(buttonPanel);


        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
}
