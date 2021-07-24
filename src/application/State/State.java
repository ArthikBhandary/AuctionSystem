package application.State;

import application.exceptions.UserNotFoundException;
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

    public static Admin getAdmin() throws UserNotFoundException {
        if(!authenticated|| !is_admin || user == null){
            System.out.println(authenticated);
            System.out.println(is_admin);
            System.out.println(user == null);
            System.out.println(admin == null);
            throw new UserNotFoundException();
        }
        return admin;
    }

    public static void setUser(User user) {
        State.authenticated = true;
        State.user = user;
    }

    public static Boolean is_authenticated(){
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
