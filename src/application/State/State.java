package application.State;

import application.exceptions.UserNotFoundException;
import application.models.Admin;
import application.models.User;


/**
 * To store the current state of the application
 * Such as the current user object/info if the user is logged in, admin status etc.
 */
public class State {
    static private User user;
    static private Admin admin;
    static private boolean authenticated = false;
    static private boolean is_admin;

    public static User getUser() throws UserNotFoundException {
        if(!authenticated || user == null){
            throw new UserNotFoundException();
        }
        return user;
    }

    public static Admin getAdmin() throws UserNotFoundException {
        if(!authenticated|| !is_admin || user == null){
            throw new UserNotFoundException();
        }
        return admin;
    }

    public static void setUser(User user) {
        State.authenticated = true;
        State.user = user;
    }

    /**
     * @return true if the user of the current session has been logged in/has been authenticated
     */
    public static Boolean isAuthenticated(){
        return authenticated;
    }


    public static boolean isAdmin() {
        return is_admin;
    }

    public static void setAdmin(Admin admin) {
        State.authenticated = true;
        State.admin = admin;
        State.user = admin;
        State.is_admin = true;
    }

//    public static void setAdmin(Admin admin) {
//        State.isAdmin = true;
//        User = admin;
//    }
}
