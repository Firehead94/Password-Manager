package com.passwordmanager.database;
import java.util.ArrayList;

import com.passwordmanager.objects.*;

public class UserDB {

    // GET
    public User getUserByID(int id) {return null;}
    public User getUserByUsername(String username) {return null;}

    public ArrayList<User> getUsers() {return null;}

    // UPDATE
    public boolean updateUser(User user) {return false;}
    public boolean updateUsername(int id, String username){return false;}
    public boolean updateUsername(String  oldUsername, String newUsername){return false;}
    public boolean updatePassword(int id, String pwd){return false;}
    public boolean updatePassword(String  username, String pwd){return false;}
    public boolean updateFirstname(int id, String fname){return false;}
    public boolean updateFirstname(String  username, String fname){return false;}
    public boolean updateLastname(int id, String lname){return false;}
    public boolean updateLastname(String  username, String lname){return false;}
    public boolean updateAccessLevel(int id, int access){return false;}
    public boolean updateAccessLevel(String  username, int access){return false;}

    // INSERT
    public boolean insertUser(User user) {return false;}

}
