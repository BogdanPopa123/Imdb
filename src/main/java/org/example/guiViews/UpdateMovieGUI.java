package org.example.guiViews;

import org.example.LoggedUser;
import org.example.Movie;
import org.example.Staff;
import org.example.TrailersMap;
import org.example.cliViews.HomeView;
import org.example.enums.Genre;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UpdateMovieGUI extends JFrame {
    public UpdateMovieGUI() {

        Movie directorsDummy = new Movie(null, null, null, null,
                null, null, null, null,
                null, null, null);

        Movie actorsDummy = new Movie(null, null, null, null,
                null, null, null, null,
                null, null, null);

        Movie genresDummy = new Movie(null, null, null, null,
                null, null, null, null,
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
            new EnterDirectors(numDirectos, directorsDummy);

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
            new EnterActors(numActors, actorsDummy);

        });
        numActorsFlow.add(numActorsLabel);
        numActorsFlow.add(actorsSpinner);
        numActorsFlow.add(actorsButton);

        JPanel genresFlow = new JPanel(new FlowLayout());
        JLabel genresLabel = new JLabel("Choose genres");
        JButton genresButton = new JButton("Choose genres");
        genresButton.addActionListener(e -> {

            new EnterGenres(genresDummy);

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

        JPanel trailerUrlFlow = new JPanel(new FlowLayout());
        JLabel trailerUrlLabel = new JLabel("trailer Url");
        JTextField trailerUrlTextField = new JTextField();
        trailerUrlTextField.setColumns(15);
        trailerUrlFlow.add(trailerUrlLabel);
        trailerUrlFlow.add(trailerUrlTextField);

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

        JPanel updateMovieFlow = new JPanel(new FlowLayout());
        JLabel updateMoveLabel = new JLabel();
        JButton updateMovieButton = new JButton("Update Movie");
        updateMovieButton.addActionListener(e -> {

            String input = titleTextField.getText();
            Object fetched = HomeView.fetch(input);

            if(!(fetched instanceof Movie)) {
                updateMoveLabel.setText("No movie found for: " + input);
                return;
            }

            Movie oldMovie = (Movie) fetched;
            String newBio = null;
            String newUrl = null;
            String newTrailerUrl = null;

            if (bioTextArea.getText() != null && !bioTextArea.getText().trim().equals("")) {
                newBio = bioTextArea.getText().trim();
            } else {
                newBio = oldMovie.getDescription();
            }

            if (urlTextField.getText() != null && !urlTextField.getText().trim().equals("")
                    && AddSeriesGUI.checkUrlString(urlTextField.getText().trim())) {
                newUrl = urlTextField.getText().trim();
            } else {
                newUrl = oldMovie.getImageUrl();
            }

            if (trailerUrlTextField.getText() != null && !trailerUrlTextField.getText().trim().equals("")
                && AddSeriesGUI.checkUrlString(trailerUrlTextField.getText().trim())){
                newTrailerUrl = trailerUrlTextField.getText().trim();
            } else {
                newTrailerUrl = TrailersMap.urlMap.get(oldMovie.getTitle());
            }

            List<String> newDirectors = null;
            if (directorsDummy.getDirectors() != null
            && directorsDummy.getDirectors().size() > 0) {
                newDirectors = directorsDummy.getDirectors();
            } else {
                newDirectors = oldMovie.getDirectors();
            }

            List<String> newActors = null;
            if (actorsDummy.getActors() != null
            && actorsDummy.getActors().size() > 0) {
                newActors = actorsDummy.getActors();
            } else {
                newActors = oldMovie.getActors();
            }

            List<Genre> newGenres = null;
            if (genresDummy.getGenres() != null
            && genresDummy.getGenres().size() > 0) {
                newGenres = genresDummy.getGenres();
            } else {
                newGenres = oldMovie.getGenres();
            }

            String title = oldMovie.getTitle();
            String author = oldMovie.getAuthor();

            int releaseYearInt = (int) releaseYearSpinner.getValue();
            Integer newReleaseYear = releaseYearInt;

            int runtimeInt = (int) minutesSpinner.getValue();
            String newRuntime = "" + runtimeInt + " minutes";

            Movie newMovie = new Movie(title, newDirectors, newActors,
                    newGenres, oldMovie.getRatings(), newBio, author,
                    newUrl, newRuntime, newReleaseYear);

            ((Staff) LoggedUser.currentUser).updateProductionSystem(newMovie);
            TrailersMap.urlMap.put(oldMovie.getTitle(), newTrailerUrl);
            updateMoveLabel.setText("Movie " + title + " updated successfully");

        });
        updateMovieFlow.add(updateMoveLabel);
        updateMovieFlow.add(updateMovieButton);


        setLayout(new GridLayout(10, 1));
        add(titleFlow);
        add(numDirectorsFlow);
        add(numActorsFlow);
        add(genresFlow);
        add(bioFlow);
        add(urlFlow);
        add(trailerUrlFlow);
        add(numMinutesFlow);
        add(releaseYearFlow);
        add(updateMovieFlow);

        setSize(600, 400);
        setVisible(true);

    }
}
