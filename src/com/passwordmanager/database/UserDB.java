package com.passwordmanager.database;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.passwordmanager.objects.*;

public class UserDB {

    public Users getUserByID(int id) {
        Users user = null;
        return user;
    }

    public Users getUserByUsername(String username) {
        Users user = null;
        return user;
    }

    public boolean updateUsername(Users user, String username){return false;}

    public boolean updatePassword(Users user, String pwd) {return false;}

    public boolean updateFirstname(Users user, String fname) {return false;}

    public boolean updateLastname(Users user, String lname) {return false;}

    public boolean updateAccessLevel(Users user, int access) {return false;}

    public boolean insertUser(Users user) {return false;}

    public ArrayList<Users> getUsers() {return null;}


}
