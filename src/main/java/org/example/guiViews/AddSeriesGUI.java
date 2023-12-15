package org.example.guiViews;


import org.example.*;
import org.example.cliViews.HomeView;
import org.example.enums.Genre;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class AddSeriesGUI extends JFrame {
    public AddSeriesGUI(){
        Series series = new Series(null, new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<Genre>(),
                new ArrayList<Rating>(), null, LoggedUser.currentUser.getUsername(),
                null, null, new TreeMap<>());

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
            new EnterDirectors(numDirectors, series);

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
            new EnterActors(numActors, series);

        });
        numActorsFlow.add(numActorsLabel);
        numActorsFlow.add(actorsSpinner);
        numActorsFlow.add(actorsButton);

        JPanel genresFlow = new JPanel(new FlowLayout());
        JLabel genresLabel = new JLabel("Choose genres");
        JButton genresButton = new JButton("Choose genres");
        genresButton.addActionListener(e -> {

            new EnterGenres(series);

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
            new EnterSeasons(numSeasons, series);

        });
        addSeasonNumbersFlow.add(numSeasonsLabel);
        addSeasonNumbersFlow.add(seasonsSpinner);
        addSeasonNumbersFlow.add(seasonsButton);

        JPanel addSeriesFlow = new JPanel(new FlowLayout());
        JLabel addSeriesLabel = new JLabel();
        JButton addSeriesButton = new JButton("Add Series");
        addSeriesButton.addActionListener(e -> {

            if (titleTextField.getText() != null && !titleTextField.getText().trim().equals("")
            && series.getDirectors().size() > 0 && series.getActors().size() > 0
            && series.getGenres().size() > 0 && bioTextArea.getText() != null
            && !bioTextArea.getText().trim().equals("")
            && urlTextField.getText() != null && !urlTextField.getText().trim().equals("")
            && series.getSeasonEpisodes().size() > 0
            && trailerUrlTextField.getText() != null && !trailerUrlTextField.getText().trim().equals("")
            && checkUrlString(urlTextField.getText().trim())
            && checkUrlString(trailerUrlTextField.getText().trim())) {

                series.setTitle(titleTextField.getText());
                series.setDescription(bioTextArea.getText());
                series.setImageUrl(urlTextField.getText());
                series.setReleaseYear((int) releaseYearSpinner.getValue());
                series.setAverageRating(series.calculateAverageRating());

                Object fetchResult = HomeView.fetch(series.getTitle());

                if (fetchResult == null) {
                    addSeriesLabel.setText("Series added successfully");
                    ((Staff)LoggedUser.currentUser).addProductionSystem(series);
                    TrailersMap.urlMap.put(series.getTitle(),
                            trailerUrlTextField.getText().trim());
                } else {
                    Series fetchedResultSeries = (Series) fetchResult;
                    addSeriesLabel.setText("Series " + fetchedResultSeries.getTitle()
                            + " already exists");
                }

            } else {
                addSeriesLabel.setText("Complete all fields");
            }

        });
        addSeriesFlow.add(addSeriesLabel);
        addSeriesFlow.add(addSeriesButton);


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
        add(addSeriesFlow);

        setSize(600, 400);
        setVisible(true);
    }

    public static boolean checkUrlString(String input) {
        if (input != null && input.startsWith("https://")) {
            return true;
        }
        return false;
    }
}

class EnterSeasons extends JFrame{



    public EnterSeasons(int numSeasons, Series series){
        Map<String, List<Episode>> localSeasonEpisodes = new LinkedHashMap<>();
        setLayout(new GridLayout(numSeasons + 1, 1));

        Map<JButton, Pairr<JTextField, JSpinner>> buttonFieldSpinnerMap = new HashMap<>();

        for (int i = 0; i < numSeasons; i++) {
            String text = "Name of season " + (i + 1);
            JPanel seasonFlow = new JPanel(new FlowLayout());
            JLabel label = new JLabel(text);
            JTextField textField = new JTextField();
            textField.setColumns(15);
            JLabel label2 = new JLabel("Number of Episodes");
            SpinnerNumberModel numberModelEpisodes = new SpinnerNumberModel(1, 1,
                    15, 1);
            JSpinner seasonsSpinner = new JSpinner(numberModelEpisodes);
            JButton button = new JButton("Enter season episodes");

            Pairr<JTextField, JSpinner> pairr = Pairr.of(textField, seasonsSpinner);
            buttonFieldSpinnerMap.put(button, pairr);

            button.addActionListener(e -> {

                Pairr<JTextField, JSpinner> pairr1 =
                        buttonFieldSpinnerMap.get(button);
                String seasonName = pairr1.getKey().getText();

                int numberOfEpisodes = (int) pairr1.getValue().getValue();

                if (pairr1.getKey().getText() != null
                && !pairr1.getKey().getText().trim().equals("")){
                    pairr1.getKey().setEnabled(false);
                    pairr1.getValue().setEnabled(false);
                    button.setEnabled(false);
                    new ConfigureEpisodes(numberOfEpisodes, seasonName, series,
                            localSeasonEpisodes);
                }

            });

            seasonFlow.add(label);
            seasonFlow.add(textField);
            seasonFlow.add(label2);
            seasonFlow.add(seasonsSpinner);
            seasonFlow.add(button);

            add(seasonFlow);
        }

        JPanel saveButtonFlow = new JPanel(new FlowLayout());
        JButton saveSeriesButton = new JButton("Save Series Episodes");
        saveSeriesButton.addActionListener(e -> {
            series.setSeasonEpisodes(localSeasonEpisodes);
            dispose();
        });

        saveButtonFlow.add(saveSeriesButton);
        add(saveButtonFlow);

        setSize(700, 400);
        setVisible(true);
    }
}

//am numit Pairr deoarece "Pair" exista deja in Actor si este declarata public Pair
class Pairr<K, V> {
    private final K key;
    private final V value;

    public Pairr(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pairr<?, ?> pair = (Pairr<?, ?>) o;
        return key.equals(pair.key) && value.equals(pair.value);
    }

    @Override
    public int hashCode() {
        return key.hashCode() ^ value.hashCode();
    }

    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }

    public static <K, V> Pairr<K, V> of(K key, V value) {
        return new Pairr<>(key, value);
    }
}

class ConfigureEpisodes extends JFrame{
    public ConfigureEpisodes(int numberOfEpisodes, String seasonName, Series series,
                             Map<String, List<Episode>> localSeasonEpisodes){
        List<Episode> episodeList = new ArrayList<>();

        List<Pairr<JTextField, JSpinner>> episodeDetailsList = new ArrayList<>();

        setLayout(new GridLayout(numberOfEpisodes + 1, 1));

        for (int i = 0; i < numberOfEpisodes; i++) {

            JPanel episodeFlow = new JPanel(new FlowLayout());
            JLabel titleLabel = new JLabel("Name for Episode " + (i + 1));
            JTextField titleTextField = new JTextField();
            titleTextField.setColumns(15);
            JLabel minutesLabel = new JLabel("Minutes ");
            SpinnerNumberModel numberModelMinutes = new SpinnerNumberModel(45, 1,
                    200, 1);
            JSpinner minutesSpinner = new JSpinner(numberModelMinutes);

            Pairr<JTextField, JSpinner> pairr = Pairr
                    .of(titleTextField, minutesSpinner);

            episodeDetailsList.add(pairr);

            episodeFlow.add(titleLabel);
            episodeFlow.add(titleTextField);
            episodeFlow.add(minutesLabel);
            episodeFlow.add(minutesSpinner);

            add(episodeFlow);
        }

        JPanel buttonFlow = new JPanel(new FlowLayout());
        JButton saveSeason = new JButton("Save season episodes");
        saveSeason.addActionListener(e -> {

            for (Pairr<JTextField, JSpinner> pairr : episodeDetailsList) {
                if (pairr.getKey().getText() != null
                && !pairr.getKey().getText().trim().equals("")) {
                    String name = pairr.getKey().getText();
                    String runtime = "" +
                            (int) pairr.getValue().getValue() + " minutes";
                    Episode episode = new Episode(name, runtime);

                    episodeList.add(episode);
                }
            }

            if (episodeList.size() > 0) {
//                series.getSeasonEpisodes().put(seasonName, episodeList);
                localSeasonEpisodes.put(seasonName, episodeList);
            }

            dispose();

        });

        buttonFlow.add(saveSeason);
        add(buttonFlow);

        setSize(600, 400);
        setVisible(true);
    }
}

