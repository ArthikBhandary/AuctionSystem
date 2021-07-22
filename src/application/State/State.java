package application.State;

import application.models.Admin;
import application.models.User;

public class State {
    static private User user;
    static private Admin admin;
    static private boolean authenticated;
    static private boolean is_admin;

    public static User getUser() throws UserNotFoundException {
        if(!authenticated || user == null){
            throw new UserNotFoundException();
        }
        return user;
    }

    public static void setUser(User user) {
        authenticated = true;
        State.user = user;
    }

    public static Boolean is_authenticated(){
        return authenticated;
    }


    public static boolean isAdmin() {
        return is_admin;
    }

    public static void setAdmin(Admin admin) {
        State.admin = admin;
        State.user = admin;
        State.is_admin = true;
    }

//    public static void setAdmin(Admin admin) {
//        State.isAdmin = true;
//        User = admin;
//    }
}
