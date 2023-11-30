package org.example;

public class LoggedUser {

    public static User currentUser;

//    public static User getCurrentUser() {
//        return currentUser;
//    }

    public static void setCurrentUser(User currentUser) {
        LoggedUser.currentUser = currentUser;
    }

    public static void setAnonymousUser() {
        LoggedUser.currentUser = null;
    }

    public static boolean isAnonymous() {
        if (LoggedUser.currentUser == null) {
            return true;
        }
        return false;
    }
}
