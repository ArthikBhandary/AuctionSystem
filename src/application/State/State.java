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

    /**
     * To get user object. If the user is not authenticated, throw an exception
     * @return User object if the current state user has admin rights and validated
     * @throws UserNotFoundException
     */
    public static User getUser() throws UserNotFoundException {
        if(!authenticated || user == null){
            throw new UserNotFoundException();
        }
        return user;
    }

    /**
     * To get admin object. If the user is not authenticated as admin throw an exception
     * @return admin object if the current state user has admin rights and validated
     * @throws UserNotFoundException
     */
    public static Admin getAdmin() throws UserNotFoundException {
        if(!authenticated|| !is_admin || user == null){
            throw new UserNotFoundException();
        }
        return admin;
    }

    /**
     * To update user in State
     * @param user the user object to be set as state user
     */
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


    /**
     * @return true if the user of the current session has been admin privileges and has been validated as admin
     */
    public static boolean isAdmin() {
        return is_admin;
    }

    /**
     * To update admin in State
     * @param admin the admin object to be set as state admin and user
     */
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
