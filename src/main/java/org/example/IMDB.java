package org.example;

import com.fasterxml.jackson.core.JsonParser;
import org.example.cliViews.HomeView;
import org.example.cliViews.InitialView;
import org.example.enums.AccountType;
import org.example.enums.Gender;
import org.example.enums.Genre;
import org.example.enums.RequestType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class IMDB {

    private IMDB(List<User> users, List<Actor> actors,
                 List<Request> requests, List<Production> productions) {
        this.users = users;
        this.actors = actors;
        this.requests = requests;
        this.productions = productions;

    }
    private static IMDB instance = null;
    private List<User> users;
    private List<Actor> actors;
    private List<Request> requests;
    private List<Production> productions;

    private void loadUsers() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader("src\\main\\resources\\input\\accounts.json");
        Object obj = jsonParser.parse(reader);
        JSONArray array = (JSONArray) obj;
        for (int i = 0; i < array.size(); i++) {
            JSONObject user = (JSONObject) array.get(i);
            String username = (String) user.get("username");

            Integer experience;
            if (user.get("experience") != null) {
                if (user.get("experience") instanceof Integer){
                    experience = (Integer) Integer.parseInt((String)user.get("experience"));
                } else if (user.get("experience") instanceof Long) {
                    Long xp = (Long) user.get("experience");
                    int points = xp.intValue();
                    experience = points;

                } else {
                    experience = (Integer) Integer.parseInt((String)user.get("experience"));
                }

            } else {
                experience = null;
            }

            AccountType accountType = AccountType
                    .valueOf(((String) user.get("userType")).toUpperCase());

            JSONObject informationJson = (JSONObject)user.get("information");
            String name = (String) informationJson.get("name");
            String country = (String) informationJson.get("country");

            Long longAge = (Long) informationJson.get("age");
            int ageValue = longAge.intValue();
            Integer age = ageValue;

            Gender gender = Gender.valueOf(((String)informationJson
                    .get("gender")).substring(0, 1));
            String dobString = (String) informationJson.get("birthDate");

            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd");
            LocalDate dateOfBirth = LocalDate
                    .parse(dobString, formatter);

            JSONObject credentialsJson = (JSONObject) informationJson
                    .get("credentials");

            String email = (String) credentialsJson.get("email");
            String password = (String) credentialsJson.get("password");
            Credentials credentials = new Credentials(email, password);

            List<String> notifications = new ArrayList<>();
            JSONArray notificationJson =(JSONArray) user.get("notifications");

            if (notificationJson != null) {
                for (int j = 0; j < notificationJson.size(); j++) {
                    notifications.add((String) notificationJson.get(j));
                }
            }


            UserFactory factory = UserFactory.getInstance();
            User.InformationBuilder builder = new User.InformationBuilder();

            SortedSet<Object> favourites = new TreeSet<>();

            JSONArray favProds = (JSONArray) user.get("favoriteProductions");
            if (favProds != null) {
                for (int j = 0; j < favProds.size(); j++) {
                    favourites.add((String)favProds.get(j));
                }
            }

            JSONArray favActors = (JSONArray) user.get("favoriteActors");
            if (favActors != null) {
                for (int j = 0; j < favActors.size(); j++) {
                    favourites.add((String) favActors.get(j));
                }
            }

            if (accountType.equals(AccountType.REGULAR)) {
                User regular = factory.createRegular(null, accountType,
                        username, experience, notifications, favourites);
                User.Information info = builder.setCredentials(credentials)
                        .setName(name)
                        .setCountry(country)
                        .setAge(age)
                        .setGender(gender)
                        .setDateOfBirth(dateOfBirth)
                        .build(regular);
                regular.setInformation(info);
                users.add(regular);
            } else {
                SortedSet<Object> contributions = new TreeSet<>();
                JSONArray prodContributions = (JSONArray) user
                        .get("productionsContribution");
                if (prodContributions != null) {
                    for (int j = 0; j < prodContributions.size(); j++) {
                        contributions.add((String)prodContributions.get(j));
                    }
                }

                JSONArray actorContributions = (JSONArray) user
                        .get("actorsContribution");
                if (actorContributions != null) {
                    for (int j = 0; j < actorContributions.size(); j++) {
                        contributions.add((String)actorContributions.get(j));
                    }
                }
                if (accountType.equals(AccountType.CONTRIBUTOR)) {
                    User contributor = factory.createContributor(null,
                            accountType, username, experience, notifications,
                            favourites, new ArrayList<>(), contributions);
                    User.Information info = builder.setCredentials(credentials)
                            .setName(name)
                            .setCountry(country)
                            .setAge(age)
                            .setGender(gender)
                            .setDateOfBirth(dateOfBirth)
                            .build(contributor);
                    contributor.setInformation(info);
                    users.add(contributor);
                }
                if (accountType.equals(AccountType.ADMIN)) {
                    User admin = factory.createAdmin(null,
                            accountType, username, experience, notifications,
                            favourites, new ArrayList<>(), contributions);
                    User.Information info = builder.setCredentials(credentials)
                            .setName(name)
                            .setCountry(country)
                            .setAge(age)
                            .setGender(gender)
                            .setDateOfBirth(dateOfBirth)
                            .build(admin);
                    admin.setInformation(info);
                    users.add(admin);
                }

            }



//            System.out.println(username);
//            System.out.println(experience);
//            System.out.println(accountType);
//            System.out.println();
        }
    }

    private void loadActors() throws IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader("src\\main\\resources\\input\\actors.json");
        Object obj = jsonParser.parse(reader);
        JSONArray array = (JSONArray) obj;
        for (int i = 0; i < array.size(); i++) {
            JSONObject actorJson = (JSONObject) array.get(i);
            String actorName = (String) actorJson.get("name");
            String actorBiography = (String) actorJson.get("biography");
            JSONArray performancesJson = (JSONArray) actorJson
                    .get("performances");
            List<Actor.Pair> performances = new ArrayList<>();

            Actor actor = new Actor(actorName, performances, actorBiography);

            for (int j = 0; j < performancesJson.size(); j++){
                JSONObject performance = (JSONObject) performancesJson.get(j);
                String title = (String) performance.get("title");
                String type = (String) performance.get("type");
                actor.addPerformance(title, type);
            }

            actors.add(actor);
        }
    }

    private void loadRequests() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader("src\\main\\resources\\input\\requests.json");
        Object obj = jsonParser.parse(reader);
        JSONArray array = (JSONArray) obj;

        for (int i = 0; i < array.size(); i++) {
            JSONObject requestJson = (JSONObject) array.get(i);
            RequestType type = RequestType
                    .valueOf((String)requestJson.get("type"));

            String creationDateString = (String) requestJson.get("createdDate");
            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime creationDate = LocalDateTime
                    .parse(creationDateString, formatter);
            String fromUsername =(String) requestJson.get("username");
            String toUsername = (String) requestJson.get("to");
            String description = (String) requestJson.get("description");

            String actorName = null;
            String movieName = null;
            String seriesName = null;

            if ((String) requestJson.get("actorName") != null) {
                actorName = (String) requestJson.get("actorName");
            }

            if ((String) requestJson.get("movieTitle") != null) {
                movieName = (String) requestJson.get("movieTitle");
            }

            if ((String) requestJson.get("seriesTitle") != null) {
                seriesName = (String) requestJson.get("seriesTitle");
            }

            String subject = null;
            if (movieName != null) {
                subject = movieName;
            }

            if (actorName != null) {
                subject = actorName;
            }

            if (seriesName != null) {
                subject = seriesName;
            }

            Request request = new Request(type, creationDate, subject,
                    description, fromUsername, toUsername);

            //daca destinatarul este ADMIN adaugam
            //requestul in lista tuturor adminilor
            if (toUsername.equals("ADMIN")) {
                Request.RequestManager.addRequest(request);
            } else { //in cazul else baam requestul userului adecvat
                //facem acest lucru iterand toti userii pana il gasim
                //pe cel care il cautam
                for (User user : users) {
                    if (user.getUsername().equals(toUsername)
                    && (user.getAccountType().equals(AccountType.CONTRIBUTOR)
                            || user.getAccountType().equals(AccountType.ADMIN))) {
                        Staff staff = (Staff) user;
                        staff.addRequest(request);
                    }
                }
            }

            requests.add(request);
        }
    }

    private void loadProductions() throws IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader("src\\main\\resources\\input\\production.json");
        Object obj = jsonParser.parse(reader);
        JSONArray array = (JSONArray) obj;

        for (int i = 0; i < array.size(); i++) {
            JSONObject productionJson = (JSONObject) array.get(i);
            String title = (String) productionJson.get("title");
            String type = (String) productionJson.get("type");

            List<String> directors = new ArrayList<>();
            JSONArray directorsJson = (JSONArray) productionJson.get("directors");
            for (int j = 0; j < directorsJson.size(); j++) {
                directors.add((String) directorsJson.get(j));
            }

            List<String> actors = new ArrayList<>();
            JSONArray actorsJson = (JSONArray) productionJson.get("actors");
            for (int j = 0; j < actorsJson.size(); j++) {
                actors.add((String) actorsJson.get(j));
            }

            List<Genre> genres = new ArrayList<>();
            JSONArray genresJson = (JSONArray) productionJson.get("genres");
            for (int j = 0; j < genresJson.size(); j++) {
                String genreString = (String) genresJson.get(j);
                genres.add(Genre.valueOf(genreString));
            }

            List<Rating> ratings = new ArrayList<>();
            JSONArray ratingsJson = (JSONArray) productionJson.get("ratings");
            for (int j = 0; j < ratingsJson.size(); j++) {
                JSONObject ratingJson = (JSONObject) ratingsJson.get(j);
                String username = (String) ratingJson.get("username");
                Integer rating;
                Long longRating = (Long) ratingJson.get("rating");
                rating = longRating.intValue();
                String comment = (String) ratingJson.get("comment");

                //adaugam ratingul in lista de ratinguri doar daca
                //userul care a adaugat ratingul are statusul regular
                for (User user : users) {
                    if (user.getUsername().equals(username)){
                        if (user.getAccountType().equals(AccountType.REGULAR)) {
                            Rating userRating = new Rating(username,
                                    rating, comment);
                            ratings.add(userRating);
                        }
                    }
                }
            }

            String plot = (String) productionJson.get("plot");
            Integer releaseYear = null;
            Long longReleaseYear = (Long) productionJson.get("releaseYear");
            if (longReleaseYear != null) {
                releaseYear = longReleaseYear.intValue();
            }
            Double averageRating = (Double) productionJson.get("averageRating");

            if (type.equals("Movie")) {
                String duration = (String) productionJson.get("duration");
                Movie movie = new Movie(title, directors,
                        actors, genres, ratings, plot, averageRating,
                        "ADMIN", duration, releaseYear);
                productions.add(movie);
            } else if (type.equals("Series")) {
                Integer numSeasons;
                Long longNumSeasons = (Long) productionJson.get("numSeasons");
                numSeasons = longNumSeasons.intValue();
                JSONObject seasonsObj = (JSONObject) productionJson.get("seasons");

                Map<String, List<Episode>> seasonEpisodes = new TreeMap<>();

                for (int j = 1; j <= numSeasons; j++) {
                    String key = "Season " + j;
                    JSONArray currentSeason = (JSONArray) seasonsObj.get(key);
                    List<Episode> episodesOfSeason = new ArrayList<>();
                    for (int k = 0; k < currentSeason.size(); k++) {
                        JSONObject episodeJson = (JSONObject) currentSeason.get(k);
                        String episodeName = (String) episodeJson.get("episodeName");
                        String duration = (String) episodeJson.get("duration");
                        Episode episode = new Episode(episodeName, duration);
                        episodesOfSeason.add(episode);
                    }
                    seasonEpisodes.put(key, episodesOfSeason);
                }
                Series series = new Series(title, directors, actors,
                        genres, ratings, plot, averageRating,
                        "ADMIN", releaseYear, seasonEpisodes);
                productions.add(series);
            }
//            System.out.println(title);
        }
    }

    //firstly, the favourites are saved as strings, then they are
    //updated as objects corresponding to those strings
    private void updateFavourites() {
        for (User user : users) {
            SortedSet<Object> favourites = user.getFavourites();
            SortedSet<Object> newList = new TreeSet<>();
            for (Object object :  favourites) {
                Object fetchedFav = HomeView.fetch((String) object);
                newList.add(fetchedFav);
            }
            user.setFavourites(newList);
        }

    }

    public void run(){
        try{
            loadUsers();
            loadActors();
            loadProductions();
            loadRequests();
            updateFavourites();

            LoggedUser.setAnonymousUser();
            InitialView.show();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static IMDB getInstance() {
        if (instance == null) {
            instance = new IMDB(new ArrayList<User>(),
                    new ArrayList<Actor>(), new ArrayList<Request>(),
                    new ArrayList<Production>());
        }
        return instance;
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public List<Production> getProductions() {
        return productions;
    }

    public void setProductions(List<Production> productions) {
        this.productions = productions;
    }
}
