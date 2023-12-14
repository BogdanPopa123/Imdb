package org.example.guiViews;

import org.example.enums.AccountType;
import org.example.enums.Gender;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class AddUserGUI extends JFrame {
    public AddUserGUI() {

        JPanel textFieldsGrid = new JPanel(new GridLayout(5, 1));

        JPanel usernameFlow = new JPanel(new FlowLayout());
        JLabel usernameLabel = new JLabel("Username: ");
        JTextField usernameField = new JTextField();
        usernameField.setColumns(15);
        usernameFlow.add(usernameLabel);
        usernameFlow.add(usernameField);
        textFieldsGrid.add(usernameFlow);

        JPanel emailFlow = new JPanel(new FlowLayout());
        JLabel emailLabel = new JLabel("Email: ");
        JTextField emailField = new JTextField();
        emailField.setColumns(15);
        emailFlow.add(emailLabel);
        emailFlow.add(emailField);
        textFieldsGrid.add(emailFlow);

        JPanel passwordFlow = new JPanel(new FlowLayout());
        JLabel passwordLabel = new JLabel("Password: ");
        JPasswordField passwordField = new JPasswordField();
        passwordField.setColumns(15);
        passwordFlow.add(passwordLabel);
        passwordFlow.add(passwordField);
        textFieldsGrid.add(passwordFlow);


        JPanel nameFlow = new JPanel(new FlowLayout());
        JLabel nameLabel = new JLabel("Name: ");
        JTextField nameField = new JTextField();
        nameField.setColumns(15);
        nameFlow.add(nameLabel);
        nameFlow.add(nameField);
        textFieldsGrid.add(nameFlow);

        JPanel countryFlow = new JPanel(new FlowLayout());
        JLabel countryLabel = new JLabel("Country: ");
        JTextField countryField = new JTextField();
        countryField.setColumns(15);
        countryFlow.add(countryLabel);
        countryFlow.add(countryField);
        textFieldsGrid.add(countryFlow);

        JPanel ageFlow = new JPanel(new FlowLayout());
        JLabel ageLabel = new JLabel("Age: ");
        SpinnerNumberModel ageNumberModel = new SpinnerNumberModel(30,
                16, 120, 1);
        JSpinner ageSpinner = new JSpinner(ageNumberModel);
        ageFlow.add(ageLabel);
        ageFlow.add(ageSpinner);


        JPanel genderAccountTypeFlow = new JPanel(new FlowLayout());

        JPanel genderFlow = new JPanel(new FlowLayout());
        JLabel genderLabel = new JLabel("Gender");
        List<Gender> genders = Arrays.asList(Gender.values());
        DefaultComboBoxModel<Gender> genderComboBoxModel = new DefaultComboBoxModel<>(genders.toArray(new Gender[0]));
        JComboBox<Gender> genderComboBox = new JComboBox<>(genderComboBoxModel);
        genderFlow.add(genderLabel);
        genderFlow.add(genderComboBox);

        JPanel accountTypeFlow = new JPanel(new FlowLayout());
        JLabel accountTypeLabel = new JLabel("Account Type ");
        List<AccountType> accountTypes = Arrays.asList(AccountType.values());
        DefaultComboBoxModel<AccountType> accountTypeComboBoxModel = new DefaultComboBoxModel<>(accountTypes.toArray(new AccountType[0]));
        JComboBox<AccountType> accountTypeComboBox = new JComboBox<>(accountTypeComboBoxModel);
        accountTypeFlow.add(accountTypeLabel);
        accountTypeFlow.add(accountTypeComboBox);

        genderAccountTypeFlow.add(genderFlow);
        genderAccountTypeFlow.add(accountTypeFlow);

        JPanel dateOfBirthFlow = new JPanel(new FlowLayout());

        JPanel dayFlow = new JPanel(new FlowLayout());
        dayFlow.add(new JLabel("Day of Birth"));
        SpinnerNumberModel dayNumberModel = new SpinnerNumberModel(15,
                1, 31, 1);
        JSpinner daySpinner = new JSpinner(dayNumberModel);
        dayFlow.add(daySpinner);
        dateOfBirthFlow.add(dayFlow);

        JPanel monthFlow = new JPanel(new FlowLayout());
        monthFlow.add(new JLabel("Month of Birth"));
        SpinnerNumberModel monthNumberModel = new SpinnerNumberModel(6,
                1, 12, 1);
        JSpinner monthSpinner = new JSpinner(monthNumberModel);
        monthFlow.add(monthSpinner);
        dateOfBirthFlow.add(monthFlow);

        JPanel yearFlow = new JPanel(new FlowLayout());
        yearFlow.add(new JLabel("Year of Birth"));
        SpinnerNumberModel yearNumberModel = new SpinnerNumberModel(1990,
                1900, 2030, 1);
        JSpinner yearSpinner = new JSpinner(yearNumberModel);
        yearFlow.add(yearSpinner);
        dateOfBirthFlow.add(yearFlow);

        JPanel addButtonFlow = new JPanel(new FlowLayout());
        JLabel infoLabel = new JLabel();
        JButton addButton = new JButton("Add User");
        addButton.addActionListener(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String (passwordField.getPassword());
            String name = nameField.getText();
            String country = countryField.getText();
            Gender gender = (Gender) genderComboBox.getSelectedItem();
            AccountType type = (AccountType) accountTypeComboBox.getSelectedItem();
            int dayOfBirth = (int) daySpinner.getValue();
            int monthOfBirth = (int) monthSpinner.getValue();
            int yearOfBirth = (int) yearSpinner.getValue();


        });
        addButtonFlow.add(infoLabel);
        addButtonFlow.add(addButton);


        setLayout(new GridLayout(4, 1));
        add(textFieldsGrid);
        add(genderAccountTypeFlow);
        add(dateOfBirthFlow);
        add(addButtonFlow);

        setSize(700, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
