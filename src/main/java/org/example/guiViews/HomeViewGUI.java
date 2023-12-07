package org.example.guiViews;

import org.example.*;
import org.example.enums.AccountType;
import org.example.enums.Genre;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class HomeViewGUI extends JFrame {

    public HomeViewGUI(){
        super("Home");

        setLayout(new GridLayout(2 ,1));

//        add(new JLabel("Home"));

        JPanel userInfoPanel = new JPanel(new GridLayout(3 ,1));
        JLabel welcomeBackLabel = new JLabel("Welcome back user " +
                LoggedUser.currentUser.getUsername());
        JLabel usernameLabel = new JLabel("Username: " +
                LoggedUser.currentUser.getUsername());

        String experienceString = null;
        if (LoggedUser.currentUser.getAccountType().equals(AccountType.ADMIN)) {
            experienceString = "-";
        } else {
            experienceString = "" + LoggedUser.currentUser.getExperience().intValue();
        }

        JLabel experrienceLabel = new JLabel("User experience: " +
                experienceString);

        usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        welcomeBackLabel.setHorizontalAlignment(SwingConstants.LEFT);
        experrienceLabel.setHorizontalAlignment(SwingConstants.LEFT);

        userInfoPanel.add(usernameLabel);
        userInfoPanel.add(welcomeBackLabel);
        userInfoPanel.add(experrienceLabel);

        JPanel regularPanel = new JPanel(new GridLayout(7, 1));
        JPanel contributorPanel = new JPanel(new GridLayout(11, 1));
        JPanel adminPanel = new JPanel(new GridLayout(11, 1));


        add(userInfoPanel);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            LoggedUser.setAnonymousUser();
            dispose();
            new LoginViewGUI();
        });

        JButton listProductionsButton = new JButton("List Productions");
        listProductionsButton.addActionListener(e -> {
            JDialog dialog = new JDialog();
            dialog.setTitle("Productions");

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);

            List<Production> productions = IMDB.getInstance().getProductions();
            for (Production production : productions) {
                textArea.append(getProductionString(production));
            }

            JScrollPane scrollPane = new JScrollPane(textArea);
            dialog.add(scrollPane);

            dialog.setSize(600, 400);
            dialog.setLocationRelativeTo(null); // Center the dialog on the screen
            dialog.setVisible(true);

        });

        JButton listActorsButton = new JButton("List Actors");
        listActorsButton.addActionListener(e -> {
            JDialog dialog = new JDialog();
            dialog.setTitle("Actors");

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);

            List<Actor> actors = IMDB.getInstance().getActors();
            for (Actor actor : actors) {
                textArea.append(getActorString(actor));
            }

            JScrollPane scrollPane = new JScrollPane(textArea);
            dialog.add(scrollPane);

            dialog.setSize(600, 400);
            dialog.setLocationRelativeTo(null); // Center the dialog on the screen
            dialog.setVisible(true);
        });

        JButton listNotificationsButton = new JButton("List Notifications");
        listNotificationsButton.addActionListener(e -> {
            JDialog dialog = new JDialog();
            dialog.setTitle("Actors");

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);

            textArea.append(getNotificationString());

            JScrollPane scrollPane = new JScrollPane(textArea);
            dialog.add(scrollPane);

            dialog.setSize(600, 400);
            dialog.setLocationRelativeTo(null); // Center the dialog on the screen
            dialog.setVisible(true);
        });

        if (LoggedUser.currentUser.getAccountType().equals(AccountType.REGULAR)) {
            regularPanel.add(listProductionsButton);
            regularPanel.add(listActorsButton);
            regularPanel.add(listNotificationsButton);
            regularPanel.add(logoutButton);
            add(regularPanel);
        } else if (LoggedUser.currentUser.getAccountType().equals(AccountType.CONTRIBUTOR)){
            contributorPanel.add(listProductionsButton);
            contributorPanel.add(listActorsButton);
            contributorPanel.add(listNotificationsButton);
            contributorPanel.add(logoutButton);
            add(contributorPanel);
        } else if (LoggedUser.currentUser.getAccountType().equals(AccountType.ADMIN)) {
            adminPanel.add(listProductionsButton);
            adminPanel.add(listActorsButton);
            adminPanel.add(listNotificationsButton);
            adminPanel.add(logoutButton);
            add(adminPanel);
        }

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private String getProductionString(Production production) {
        String type = null;
        StringBuilder sb = new StringBuilder();

        if (production instanceof Movie) {
            type = "Movie";
        } else if (production instanceof Series) {
            type = "Series";
        }

        sb.append(production.getTitle()).append(" (").append(type).append(")\n");
        sb.append("    ").append("Directors: \n");
        for (String director : production.getDirectors()) {
            sb.append("        " + director + "\n");
        }
        sb.append("    " + "Actors: \n");
        for (String actor : production.getActors()){
            sb.append("        " + actor + "\n");
        }
        sb.append("    " + "Genres: \n");
        for (Genre genre : production.getGenres()) {
            sb.append("        " + genre.toString() + "\n");
        }
        sb.append("    " + "Ratings: \n");
        for (Rating rating : production.getRatings()) {
            sb.append("        " + rating.getUsername() +
                    "\n        rated with: " + rating.getGrade() +
                    "\n        " + rating.getComment() + "\n\n");
        }
        sb.append("    Plot: " + production.getDescription() + "\n");
        sb.append("    Average rating: " + production.getAverageRating() + "\n");

        if (production instanceof Movie) {
            Movie movie = (Movie) production;
            sb.append("    Release year: " + movie.getReleaseYear() + "\n");
            sb.append("    Duration: " + movie.getRuntime() + "\n");
        } else if (production instanceof Series) {
            Series series = (Series) production;
            sb.append("    Release year: " + series.getReleaseYear() + "\n");

            Map<String, List<Episode>> seasonEpisodes = series.getSeasonEpisodes();

            seasonEpisodes.forEach((seasonName, episodes) -> {
                sb.append("    " + seasonName + "\n");
                episodes.forEach(episode -> {
                    sb.append("        " + episode.getName() + "\n");
                    sb.append("        " + episode.getRuntime() + "\n");
                    sb.append("\n");
                });
            });
        }

        sb.append("\n\n\n");
        return sb.toString();
    }

    private String getActorString(Actor actor) {
        String type = null;
        StringBuilder sb = new StringBuilder();
        sb.append(actor.getName() + "\n");
        sb.append("    Performances: \n");
        List<Actor.Pair> performances = actor.getPerformances();

        for (Actor.Pair pair : performances) {
            sb.append("        " + pair.getTitle() +
                    " (" + pair.getType() + ")\n");
        }

        sb.append("    Biography: " + actor.getBiography() + "\n");
        sb.append("    Ratings: \n");

        for (Rating rating : actor.getRatings()) {
            sb.append("        " + rating.getUsername() + "\n" +
                    "            rated with: " + rating.getGrade() + "\n" +
                    "            " + rating.getComment() + "\n\n");
        }

        sb.append("\n\n");

        return sb.toString();
    }

    private String getNotificationString() {
        String type = null;
        StringBuilder sb = new StringBuilder();

        if (LoggedUser.currentUser.getNotifications().size() == 0) {
            sb.append("You currently have no notifications\n");
            return sb.toString();
        }

        sb.append("These are the notifications for the user:" +
                LoggedUser.currentUser.getUsername() + "\n");

        for (String notification : LoggedUser.currentUser.getNotifications()) {
            sb.append("    " + notification + "\n");
        }

        return sb.toString();
    }
}
