package com.passwordmanager.objects;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class User {

    private int user_ID;
    private int access_level;
    private String user_username;
    private String user_password;
    private String user_first_name;
    private String user_last_name;

    /**
     * @return the user_ID
     */
    public int getUser_ID() { return user_ID;}

    /**
     * @return the access_level
     */

    public int getAccess_level() { return access_level;}

    /**
     * @return the user_username
     */

    public String getUser_username() {
        return user_username;
    }

    /**
     * @return the user_password
     */
    public String getUser_password() {
        return user_password;
    }

    /**
     * @return the user_first_name
     */
    public String getUser_first_name() {
        return user_first_name;
    }

    /**
     * @return the user_last_name
     */
    public String getUser_last_name() {
        return user_last_name;
    }


    /**
     * @param user_ID the user_ID to set
     */
    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    /**
     * @param access_level the access_level to set
     */
    public void setAccess_level(int access_level) {
        this.access_level = access_level;
    }

    /**
     * @param user_username the user_username to set
     */
    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    /**
     * @param user_password the user_password to set
     */
    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    /**
     * @param user_first_name the user_first_name to set
     */
    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    /**
     * @param user_last_name the user_last_name to set
     */
    public void setUser_last_name(String user_last_name) {
        this.user_last_name = user_last_name;
    }
}
