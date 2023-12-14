package org.example.guiViews;

import org.example.*;
import org.example.cliViews.HomeView;
import org.example.enums.Genre;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AddMovieGUI extends JFrame {
    public  AddMovieGUI() {
        Movie movie = new Movie(null, new ArrayList<String>(),
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

            int numDirectos = (int) directorsSpinner.getValue();
            new EnterDirectors(numDirectos, movie);

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

            int numActors = (int) actorsSpinner.getValue();
            new EnterActors(numActors, movie);

        });
        numActorsFlow.add(numActorsLabel);
        numActorsFlow.add(actorsSpinner);
        numActorsFlow.add(actorsButton);

        JPanel genresFlow = new JPanel(new FlowLayout());
        JLabel genresLabel = new JLabel("Choose genres");
        JButton genresButton = new JButton("Choose genres");
        genresButton.addActionListener(e -> {

            new EnterGenres(movie);

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

        JPanel numMinutesFlow = new JPanel(new FlowLayout());
        JLabel numMinutesLabel = new JLabel("Number of minutes");
        SpinnerNumberModel numberModelMinutes = new SpinnerNumberModel(1, 1,
                250, 1);
        JSpinner minutesSpinner = new JSpinner(numberModelMinutes);
        numMinutesFlow.add(numMinutesLabel);
        numMinutesFlow.add(minutesSpinner);


        JPanel releaseYearFlow = new JPanel(new FlowLayout());
        JLabel releaseYearLabel = new JLabel("Release year");
        SpinnerNumberModel numberModelReleaseYear = new SpinnerNumberModel(2010, 1900,
                2050, 1);
        JSpinner releaseYearSpinner = new JSpinner(numberModelReleaseYear);
        releaseYearFlow.add(releaseYearLabel);
        releaseYearFlow.add(releaseYearSpinner);

        JPanel addMovieFlow = new JPanel(new FlowLayout());
        JLabel addMoveLabel = new JLabel();
        JButton addMovieButton = new JButton("Add Movie");
        addMovieButton.addActionListener(e -> {

            if (titleTextField.getText() != null && !titleTextField.getText().trim().equals("")
            && movie.getDirectors().size() > 0 && movie.getActors().size() > 0
            && movie.getGenres().size() > 0 && bioTextArea.getText() != null
            && !bioTextArea.getText().trim().equals("") && urlTextField.getText() != null
            && !urlTextField.getText().trim().equals("")
            && AddSeriesGUI.checkUrlString(urlTextField.getText().trim())) {

                movie.setTitle(titleTextField.getText());
                movie.setDescription(bioTextArea.getText());
                movie.setImageUrl(urlTextField.getText());
                movie.setRuntime( "" + ((int) minutesSpinner.getValue())
                 + " minutes");
                movie.setReleaseYear((int) releaseYearSpinner.getValue());
                movie.setAverageRating(movie.calculateAverageRating());

                Object fetchResult = HomeView.fetch(movie.getTitle());

                if (fetchResult == null) {
                    addMoveLabel.setText("Movie added successfully");
                    ((Staff)LoggedUser.currentUser).addProductionSystem(movie);
                } else {
                    Movie fetchResultMovie = (Movie) fetchResult;
                    addMoveLabel.setText("Movie " + fetchResultMovie.getTitle()
                            + " already exists");
                }


            } else {
                addMoveLabel.setText("Complete all fields");
            }

        });
        addMovieFlow.add(addMoveLabel);
        addMovieFlow.add(addMovieButton);


        setLayout(new GridLayout(9, 1));
        add(titleFlow);
        add(numDirectorsFlow);
        add(numActorsFlow);
        add(genresFlow);
        add(bioFlow);
        add(urlFlow);
        add(numMinutesFlow);
        add(releaseYearFlow);
        add(addMovieFlow);

        setSize(600, 400);
        setVisible(true);
    }

}

class EnterDirectors extends JFrame{
    public EnterDirectors(int numDirectors, Production production){

        setLayout(new GridLayout(numDirectors + 1, 1));
        List<JTextField> directorTextFields = new ArrayList<JTextField>();

        for(int i = 0; i < numDirectors; i++) {
            JPanel directorFlow = new JPanel(new FlowLayout());
            JLabel label = new JLabel("director name");
            JTextField directorTextField = new JTextField();
            directorTextField.setColumns(15);

            directorTextFields.add(directorTextField);

            directorFlow.add(label);
            directorFlow.add(directorTextField);
            add(directorFlow);
        }

        JPanel buttonFlow = new JPanel(new FlowLayout());
        JButton setDirectorsButton = new JButton("Set Directors");
        setDirectorsButton.addActionListener(e -> {

            List<String> directors = new ArrayList<>();

            for (JTextField textField : directorTextFields) {
                String dirName = textField.getText();
                if (dirName != null && !dirName.trim().equals("")) {
                    directors.add(dirName);
                }
            }

            production.setDirectors(directors);

            this.dispose();
        });
        buttonFlow.add(setDirectorsButton);

        add(buttonFlow);

        setSize(600, 400);
        setVisible(true);
    }


}


class EnterActors extends JFrame{
    public EnterActors(int numActors, Production production){

        setLayout(new GridLayout(numActors + 1, 1));
        List<JTextField> actorTextFields = new ArrayList<JTextField>();

        for(int i = 0; i < numActors; i++) {
            JPanel actorFlow = new JPanel(new FlowLayout());
            JLabel label = new JLabel("actor name");
            JTextField actorTextField = new JTextField();
            actorTextField.setColumns(15);

            actorTextFields.add(actorTextField);

            actorFlow.add(label);
            actorFlow.add(actorTextField);
            add(actorFlow);
        }

        JPanel buttonFlow = new JPanel(new FlowLayout());
        JButton setActorsButton = new JButton("Set Actors");
        setActorsButton.addActionListener(e -> {

            List<String> actors = new ArrayList<>();

            for (JTextField textField : actorTextFields) {
                String actorName = textField.getText();
                if (actorName != null && !actorName.trim().equals("")) {
                    actors.add(actorName);
                }
            }

            production.setActors(actors);

            this.dispose();
        });
        buttonFlow.add(setActorsButton);

        add(buttonFlow);

        setSize(600, 400);
        setVisible(true);
    }
}

class EnterGenres extends JFrame{
    public EnterGenres(Production production) {

        List<JCheckBox> genreCheckBoxes = new ArrayList<>();

        setLayout(new GridLayout(Genre.values().length + 1, 1));

        for (Genre genre : Genre.values()) {
            JCheckBox checkBox = new JCheckBox(genre.name());
            genreCheckBoxes.add(checkBox);
            add(checkBox);
        }


        JPanel buttonFlow = new JPanel(new FlowLayout());
        JButton button = new JButton("Set Genres");
        button.addActionListener(e -> {

            List<Genre> selectedGenres = new ArrayList<>();

            for (JCheckBox checkBox : genreCheckBoxes) {
                if (checkBox.isSelected()) {
                    selectedGenres.add(Genre.fromString(checkBox.getText()));
                }
            }

            production.setGenres(selectedGenres);

            this.dispose();
        });

        buttonFlow.add(button);
        add(buttonFlow);



        setSize(600, 400);
        setVisible(true);
    }
}
