package org.example.guiViews;

import org.example.LoggedUser;
import org.example.Rating;
import org.example.Series;
import org.example.enums.Genre;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddSeriesGUI extends JFrame {
    public AddSeriesGUI(){
        Series series = new Series(null, new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<Genre>(),
                new ArrayList<Rating>(), null, LoggedUser.currentUser.getUsername(),
                null, null, null);

        JPanel titleFlow = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Title");
        JTextField titleTextField = new JTextField();
        titleTextField.setColumns(15);
        titleFlow.add(titleLabel);
        titleFlow.add(titleTextField);

        JPanel numDirectorsFlow = new JPanel(new FlowLayout());
        JLabel numDirectorsLabel = new JLabel("Number of directors");
        SpinnerNumberModel numberModelDirectors = new SpinnerNumberModel(1, 1,
                20, 1);
        JSpinner directorsSpinner = new JSpinner(numberModelDirectors);
        JButton directorsButton = new JButton("Enter Directors");
        directorsButton.addActionListener(e -> {



        });
        numDirectorsFlow.add(numDirectorsLabel);
        numDirectorsFlow.add(directorsSpinner);
        numDirectorsFlow.add(directorsButton);


        JPanel numActorsFlow = new JPanel(new FlowLayout());
        JLabel numActorsLabel = new JLabel("Number of Actors");
        SpinnerNumberModel numberModelActors = new SpinnerNumberModel(1, 1,
                20, 1);
        JSpinner actorsSpinner = new JSpinner(numberModelActors);
        JButton actorsButton = new JButton("Enter Actors");
        actorsButton.addActionListener(e -> {




        });
        numActorsFlow.add(numActorsLabel);
        numActorsFlow.add(actorsSpinner);
        numActorsFlow.add(actorsButton);

        JPanel genresFlow = new JPanel(new FlowLayout());
        JLabel genresLabel = new JLabel("Choose genres");
        JButton genresButton = new JButton("Choose genres");
        genresButton.addActionListener(e -> {




        });
        genresFlow.add(genresLabel);
        genresFlow.add(genresButton);

        JPanel bioFlow = new JPanel(new FlowLayout());
        JLabel bioLabel = new JLabel("Biography");
        JTextArea bioTextArea = new JTextArea();
        bioTextArea.setRows(3);
        bioTextArea.setColumns(15);
        bioFlow.add(bioLabel);
        bioFlow.add(bioTextArea);

        JPanel urlFlow = new JPanel(new FlowLayout());
        JLabel urlLabel = new JLabel("image Url");
        JTextField urlTextField = new JTextField();
        urlTextField.setColumns(15);
        urlFlow.add(urlLabel);
        urlFlow.add(urlTextField);

        JPanel releaseYearFlow = new JPanel(new FlowLayout());
        JLabel releaseYearLabel = new JLabel("Release year");
        SpinnerNumberModel numberModelReleaseYear = new SpinnerNumberModel(2010, 1900,
                2050, 1);
        JSpinner releaseYearSpinner = new JSpinner(numberModelReleaseYear);
        releaseYearFlow.add(releaseYearLabel);
        releaseYearFlow.add(releaseYearSpinner);

        JPanel addSeasonNumbersFlow = new JPanel(new FlowLayout());
        JLabel numSeasonsLabel = new JLabel("Number of seasons");
        SpinnerNumberModel numberModelSeasons = new SpinnerNumberModel(1, 1,
                15, 1);
        JSpinner seasonsSpinner = new JSpinner(numberModelSeasons);
        JButton seasonsButton = new JButton("Configure season episodes");
        seasonsButton.addActionListener(e -> {

        });
        addSeasonNumbersFlow.add(numSeasonsLabel);
        addSeasonNumbersFlow.add(seasonsSpinner);
        addSeasonNumbersFlow.add(seasonsButton);

        JPanel addSeriesFlow = new JPanel(new FlowLayout());
        JLabel addSeriesLabel = new JLabel();
        JButton addSeriesButton = new JButton("Add Series");
        addSeriesButton.addActionListener(e -> {

        });
        addSeriesFlow.add(addSeriesLabel);
        addSeriesFlow.add(addSeriesButton);


        setLayout(new GridLayout(9, 1));
        add(titleFlow);
        add(numDirectorsFlow);
        add(numActorsFlow);
        add(genresFlow);
        add(bioFlow);
        add(urlFlow);
        add(releaseYearFlow);
        add(addSeasonNumbersFlow);
        add(addSeriesFlow);

        setSize(600, 400);
        setVisible(true);
    }
}
