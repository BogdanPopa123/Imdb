package org.example.strategies;

import org.example.Admin;
import org.example.ExperienceStrategy;
import org.example.IMDB;
import org.example.User;

public class ActorAddedExperienceStrategy implements ExperienceStrategy {
    public int calculateExperience(String username) {
        for (User user : IMDB.getInstance().getUsers()) {
            if (user.getUsername().equals(username)) {
                if (! (user instanceof Admin)) {
                    Integer experience = user.getExperience();
                    experience = experience + 1;
                    return experience;
                }
            }
        }
        return -1;
    }
}
