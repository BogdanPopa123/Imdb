package org.example.guiViews;

import org.example.Actor;
import org.example.LoggedUser;
import org.example.Staff;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddActorGUI extends JFrame {
    public AddActorGUI() {
        JLabel nameLabel = new JLabel("Actor name ");
        JLabel bioLabel = new JLabel("Biography ");

        JTextField nameTextField = new JTextField();
        nameTextField.setColumns(15);
        JTextArea bioTextArea = new JTextArea();
        bioTextArea.setColumns(25);
        bioTextArea.setRows(3);

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
        spinnerPanel.add(spinner);

        JPanel urlPanel = new JPanel(new FlowLayout());
        urlPanel.add(urlLabel);
        urlPanel.add(urlTextField);

        JButton button = new JButton("Next");
        button.addActionListener(e -> {
            new EnterPerformances(nameTextField.getText(),
                    bioTextArea.getText(),
                    urlTextField.getText(),
                    (int) spinner.getValue());
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(button);

        setLayout(new GridLayout(5, 1));

        add(namePanel);
        add(bioPanel);
        add(urlPanel);
        add(spinnerPanel);
        add(buttonPanel);

        setSize(600, 400);
        setVisible(true);
    }


    class EnterPerformances extends JFrame{
        public EnterPerformances(String name, String bio,
                                 String imageUrl, int num) {
            setLayout(new GridLayout(num + 1, 1));

            for (int i = 0; i < num; i++) {
                JPanel panel = new JPanel(new FlowLayout());

                panel.add(new JLabel("Performance name"));
                JTextField textField1 = new JTextField();
                textField1.setColumns(15);
                panel.add(textField1);

                panel.add(new JLabel("Type"));
                JTextField textField2 = new JTextField();
                textField2.setColumns(15);
                panel.add(textField2);

                add(panel);
            }

            JButton addButton = new JButton("Add actor");
            addButton.addActionListener(e -> {
                Actor actor = new Actor(name, new ArrayList<>(),
                        bio, LoggedUser.currentUser.getUsername(),
                        imageUrl);

                // Extract text values from text fields and store them
                for (Component component : getContentPane().getComponents()) {
                    if (component instanceof JPanel) {
                        JPanel panel = (JPanel) component;
                        Component[] components = panel.getComponents();

                        String perfName = null;
                        String perfType = null;

                        if (components.length >= 2 && components[1] instanceof JTextField) {
                            // Text field for performance name
                            JTextField textField1 = (JTextField) components[1];
//                            performanceNames.add(textField1.getText());
                            perfName = textField1.getText();
                        }
                        if (components.length >= 4 && components[3] instanceof JTextField) {
                            // Text field for performance type
                            JTextField textField2 = (JTextField) components[3];
//                              performanceTypes.add(textField2.getText());
                                perfType = textField2.getText();
                        }

                        if (perfType != null && perfName != null) {
                            actor.addPerformance(perfName, perfType);
                        }
                    }
                }


                ((Staff)LoggedUser.currentUser).addActorSystem(actor);
            });

            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(addButton);

            add(buttonPanel);

            setSize(600, 400);
            setVisible(true);

        }
    }
}
