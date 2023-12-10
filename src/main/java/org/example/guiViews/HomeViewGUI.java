package org.example.guiViews;

import org.example.*;
import org.example.cliViews.HomeView;
import org.example.enums.AccountType;
import org.example.enums.Genre;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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



        JButton searchObjectButton = new JButton("Search Actor/Production");
        searchObjectButton.addActionListener(e -> {
            JDialog dialog = new JDialog();
            dialog.setTitle("Search");

            AtomicReference<String> urlString = new AtomicReference<>();
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);

            JLabel photoLabel = null;

            JTextField textField = new JTextField();
            JButton button = new JButton("Search");
            button.addActionListener(e2 -> {
                String input = textField.getText();
                Object fetched = HomeView.fetch(input);

                if (fetched instanceof Production) {
                    Production production = (Production) fetched;
                    textArea.setText("Result found for: " +
                            production.getTitle() + "\n\n");
                    textArea.append(getProductionString(production));
                    urlString.set(production.getImageUrl());
                } else if (fetched instanceof Actor) {
                    Actor actor = (Actor) fetched;
                    textArea.setText("Result found for: " +
                            actor.getName() + "\n\n");
                    textArea.append(getActorString(actor));
                    urlString.set(actor.getImageUrl());
                } else if (fetched == null) {
                    textArea.setText("No results found for: " + input);
                    urlString.set("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1Op5yQpnNvLXb5eozfBKYJ_h6IIIIjYvsOQ&usqp=CAU");
                }


                updatePhotoLabel(dialog, urlString.get(), textArea,
                        textField, button);
            });


            JPanel textAreaPanel = new JPanel(new BorderLayout());
            textAreaPanel.add(textArea, BorderLayout.CENTER);

            JScrollPane scrollPane = new JScrollPane(textAreaPanel);

            dialog.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            // Top panel with FlowLayout for left alignment
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topPanel.add(textField);
            textField.setColumns(15);
            topPanel.add(button);

            gbc.gridx = 0;
            gbc.gridy = 0;
            dialog.add(topPanel, gbc);

            // ScrollPane in the middle with more weight
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weighty = 1.0;  // Higher weight for more space
            gbc.fill = GridBagConstraints.BOTH;  // Fill both horizontally and vertically
            dialog.add(scrollPane, gbc);

            dialog.setSize(800, 400);  // Increased width to accommodate the photo
            dialog.setLocationRelativeTo(null); // Center the dialog on the screen
            dialog.setVisible(true);
        });

        JButton addRemoveFavourites = new JButton("Add/Remove favourites");
        addRemoveFavourites.addActionListener(e ->{
            JDialog dialog = new JDialog();
            dialog.setTitle("Favourites");

            dialog.setLayout(new GridLayout(2, 1));

            JLabel addFavLabel = new JLabel("");
            JPanel addFavouritesPanel = new JPanel(new FlowLayout());
            JTextField addFavTextfield = new JTextField();
            addFavTextfield.setColumns(15);
            JButton addFavButton = new JButton("Add to favourites");
            addFavButton.addActionListener(e2 ->{
                String input = addFavTextfield.getText();
                Object fetched = HomeView.fetch(input);
                if (fetched == null) {
                    addFavLabel.setText("No result found for: " + input);
                    return;
                }

                String name = null;
                if (fetched instanceof Production){
                    name = ((Production)fetched).getTitle();
                } else if (fetched instanceof Actor) {
                    name = ((Actor)fetched).getName();
                }

                if (LoggedUser.currentUser.getFavourites().contains(fetched)) {
                    addFavLabel.setText(name + " is already in your favourites list");
                    return;
                }

                LoggedUser.currentUser.addToFavourites(fetched);
                addFavLabel.setText(name + " successfully added to the favourites");
            });
            addFavouritesPanel.add(addFavTextfield);
            addFavouritesPanel.add(addFavButton);
            JPanel firstGrid = new JPanel(new GridLayout(2, 1));
            firstGrid.add(addFavouritesPanel);
            firstGrid.add(addFavLabel);

            JPanel removeFavouritesPanel = new JPanel(new FlowLayout());
            JLabel removeFavLabel = new JLabel("");
            JTextField removeFavTextfield = new JTextField();
            removeFavTextfield.setColumns(15);
            JButton removeFavButton = new JButton("Remove from favourites");
            removeFavButton.addActionListener(e2 -> {
                String input = removeFavTextfield.getText();
                Object fetched = HomeView.fetch(input);

                if (fetched == null) {
                    removeFavLabel.setText("No result found for: " + input);
                    return;
                }

                String name = null;
                if (fetched instanceof Production){
                    name = ((Production)fetched).getTitle();
                } else if (fetched instanceof Actor) {
                    name = ((Actor)fetched).getName();
                }

                if (!LoggedUser.currentUser.getFavourites().contains(fetched)) {
                    removeFavLabel.setText(name + " is not in your favourites list");
                    return;
                }

                LoggedUser.currentUser.getFavourites().remove(fetched);
                removeFavLabel.setText(name + " has ben successfully removed " +
                        "from your favourites");
            });
            removeFavouritesPanel.add(removeFavTextfield);
            removeFavouritesPanel.add(removeFavButton);
            JPanel secondGrid = new JPanel(new GridLayout(2, 1));
            secondGrid.add(removeFavouritesPanel);
            secondGrid.add(removeFavLabel);


            dialog.add(firstGrid);
            dialog.add(secondGrid);
            dialog.setSize(600, 400);
            dialog.setLocationRelativeTo(null); // Center the dialog on the screen
            dialog.setVisible(true);
        });

        JButton createRemoveRequest = new JButton("Create/Remove request");
        createRemoveRequest.addActionListener(e -> {
            new CreateRequestGUI();
        });

        if (LoggedUser.currentUser.getAccountType().equals(AccountType.REGULAR)) {
            regularPanel.add(listProductionsButton);
            regularPanel.add(listActorsButton);
            regularPanel.add(listNotificationsButton);
            regularPanel.add(searchObjectButton);
            regularPanel.add(addRemoveFavourites);
            regularPanel.add(createRemoveRequest);
            regularPanel.add(logoutButton);
            add(regularPanel);
        } else if (LoggedUser.currentUser.getAccountType().equals(AccountType.CONTRIBUTOR)){
            contributorPanel.add(listProductionsButton);
            contributorPanel.add(listActorsButton);
            contributorPanel.add(listNotificationsButton);
            contributorPanel.add(searchObjectButton);
            contributorPanel.add(addRemoveFavourites);
            contributorPanel.add(createRemoveRequest);
            contributorPanel.add(logoutButton);
            add(contributorPanel);
        } else if (LoggedUser.currentUser.getAccountType().equals(AccountType.ADMIN)) {
            adminPanel.add(listProductionsButton);
            adminPanel.add(listActorsButton);
            adminPanel.add(listNotificationsButton);
            adminPanel.add(searchObjectButton);
            adminPanel.add(addRemoveFavourites);
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

    private void updatePhotoLabel(JDialog dialog, String imageUrl,
                                  JTextArea textArea, JTextField textField,
                                  JButton searchButton) {
        try {
            JLabel photoLabel = new JLabel(new ImageIcon(new URL(imageUrl)));

            // Clear the dialog content before updating with new components
            dialog.getContentPane().removeAll();

            JPanel textAreaPanel = new JPanel(new BorderLayout());
            textAreaPanel.add(textArea, BorderLayout.CENTER);

            JScrollPane scrollPane = new JScrollPane(textAreaPanel);

            dialog.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            // Top panel with FlowLayout for left alignment
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topPanel.add(textField);
            textField.setColumns(15);  // Set a fixed size for the textField
            topPanel.add(searchButton);

            gbc.gridx = 0;
            gbc.gridy = 0;
            dialog.add(topPanel, gbc);

            // ScrollPane in the middle with more weight
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 1.0;  // Allow the scroll pane to expand horizontally
            gbc.weighty = 1.0;  // Higher weight for more space
            gbc.fill = GridBagConstraints.BOTH;  // Fill both horizontally and vertically
            dialog.add(scrollPane, gbc);

            // PhotoLabel on the right side
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.weightx = 0;  // Reset weight for the photo label
            gbc.fill = GridBagConstraints.VERTICAL;  // Fill vertically
            dialog.add(photoLabel, gbc);

            // Repaint the dialog to reflect the changes
            dialog.revalidate();
            dialog.repaint();

            dialog.setSize(800, 400);  // Increased width to accommodate the photo
            dialog.setLocationRelativeTo(null); // Center the dialog on the screen
            dialog.setVisible(true);
        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        }
    }
}


