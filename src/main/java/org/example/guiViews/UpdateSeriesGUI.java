package org.example.guiViews;

import org.example.*;
import org.example.cliViews.HomeView;
import org.example.enums.Genre;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class UpdateSeriesGUI extends JFrame {

    Series directorsDummy = new Series(null, null, null,
            null, null, null, null,
            null, null, null);

    Series actorsDummy = new Series(null, null, null,
            null, null, null, null,
            null, null, null);

    Series genresDummy = new Series(null, null, null,
            null, null, null, null,
            null, null, null);

    Series seasonsDummy = new Series(null, null, null,
            null, null, null, null,
            null, null, null);


    public UpdateSeriesGUI(){
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

            int numDirectors = (int) directorsSpinner.getValue();
            new EnterDirectors(numDirectors, directorsDummy);

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

            int numSeasons = (int) seasonsSpinner.getValue();
            new EnterSeasons(numSeasons, seasonsDummy);

        });
        addSeasonNumbersFlow.add(numSeasonsLabel);
        addSeasonNumbersFlow.add(seasonsSpinner);
        addSeasonNumbersFlow.add(seasonsButton);

        JPanel updateSeriesFlow = new JPanel(new FlowLayout());
        JLabel updateSeriesLabel = new JLabel();
        JButton updateSeriesButton = new JButton("Update Series");
        updateSeriesButton.addActionListener(e -> {
            String input = titleTextField.getText();
            Object fetched = HomeView.fetch(input);

            if (!(fetched instanceof Series)) {
                updateSeriesLabel.setText("No series found for: " + input);
                return;
            }

            Series oldSeries = (Series) fetched;
            String newBio = null;
            String newUrl = null;
            String newTrailerUrl = null;

            if (bioTextArea.getText() != null && !bioTextArea.getText().trim().equals("")) {
                newBio = bioTextArea.getText().trim();
            } else {
                newBio = oldSeries.getDescription();
            }

            if (urlTextField.getText() != null && !urlTextField.getText().trim().equals("")
                    && AddSeriesGUI.checkUrlString(urlTextField.getText().trim())) {
                newUrl = urlTextField.getText().trim();
            } else {
                newUrl = oldSeries.getImageUrl();
            }

            if (trailerUrlTextField.getText() != null && !trailerUrlTextField.getText().trim().equals("")
                    && AddSeriesGUI.checkUrlString(trailerUrlTextField.getText().trim())){
                newTrailerUrl = trailerUrlTextField.getText().trim();
            } else {
                newTrailerUrl = TrailersMap.urlMap.get(oldSeries.getTitle());
            }

            List<String> newDirectors = null;
            if (directorsDummy.getDirectors() != null
                    && directorsDummy.getDirectors().size() > 0) {
                newDirectors = directorsDummy.getDirectors();
            } else {
                newDirectors = oldSeries.getDirectors();
            }

            List<String> newActors = null;
            if (actorsDummy.getActors() != null
                    && actorsDummy.getActors().size() > 0) {
                newActors = actorsDummy.getActors();
            } else {
                newActors = oldSeries.getActors();
            }

            List<Genre> newGenres = null;
            if (genresDummy.getGenres() != null
                    && genresDummy.getGenres().size() > 0) {
                newGenres = genresDummy.getGenres();
            } else {
                newGenres = oldSeries.getGenres();
            }

            int releaseYearInt = (int) releaseYearSpinner.getValue();
            Integer newReleaseYear = releaseYearInt;

            Map<String, List<Episode>> newSeasonEpisodes = null;
            if (seasonsDummy.getSeasonEpisodes() != null
                    && seasonsDummy.getSeasonEpisodes().size() > 0) {
                newSeasonEpisodes = seasonsDummy.getSeasonEpisodes();
            } else {
                newSeasonEpisodes = oldSeries.getSeasonEpisodes();
            }

            String title = oldSeries.getTitle();
            String author = oldSeries.getAuthor();

            Series newSeries = new Series(title, newDirectors, newActors,
                    newGenres, oldSeries.getRatings(), newBio, author,
                    newUrl, newReleaseYear, newSeasonEpisodes);

            ((Staff) LoggedUser.currentUser).updateProductionSystem(newSeries);
            TrailersMap.urlMap.put(oldSeries.getTitle(), newTrailerUrl);
            updateSeriesLabel.setText("Series " + title + " updated successfully");

        });
        updateSeriesFlow.add(updateSeriesLabel);
        updateSeriesFlow.add(updateSeriesButton);

        setLayout(new GridLayout(10, 1));
        add(titleFlow);
        add(numDirectorsFlow);
        add(numActorsFlow);
        add(genresFlow);
        add(bioFlow);
        add(urlFlow);
        add(trailerUrlFlow);
        add(releaseYearFlow);
        add(addSeasonNumbersFlow);
        add(updateSeriesFlow);

        setSize(600, 400);
        setVisible(true);
    }
}
