package com.passwordmanager.database.objects;


import java.sql.Timestamp;

//TODO account for new attributes in DB
/**
 * @author Justin Scott
 * @author Nick Soltysiak
 */
public class Password {

    private int password_ID;
    private int folder_ID;
    private String password;
    private String password_title;
    private Timestamp password_timestamp;
    private String password_username;
    private String password_url;
    private String password_notes;
    private int password_owner;

    public String getPassword_username() {
        return password_username;
    }

    public void setPassword_username(String password_username) {
        this.password_username = password_username;
    }

    public String getPassword_url() {
        return password_url;
    }

    public void setPassword_url(String password_url) {
        this.password_url = password_url;
    }

    public String getPassword_notes() {
        return password_notes;
    }

    public void setPassword_notes(String password_notes) {
        this.password_notes = password_notes;
    }

    public int getPassword_owner() {
        return password_owner;
    }

    public void setPassword_owner(int password_owner) {
        this.password_owner = password_owner;
    }

    @Override
    public String toString() {
        return String.format("%-20s | %2s", password_title, password);
    }

    public Password() {

    }

    //overloaded constructor for creating a password
    //folder ID can be set after creation using the setFolder_ID() method
    //timestamp can be set using the setPassword_Timestamp method()
    public Password(String password, String password_title, int folder, int id) {
        this.password = password;
        this.password_title = password_title;
        this.folder_ID = folder;
        this.password_owner = id;
    }

    /**
     * @return the password_ID
     */
    public int getPassword_ID() {
        return password_ID;
    }

    /**
     * @return the folder_ID
     */
    public int getFolder_ID() {
        return folder_ID;
    }

    /**
     * @return the password;
     */

    public String getPassword() {
        return password;
    }

    /**
     * @return the password_title;
     */

    public String getPassword_title() {
        return password_title;
    }

    /**
     * @return the password_timestamp;
     */

    public Timestamp getPassword_timestamp() {
        return password_timestamp;
    }

    /**
     * @param password_ID the password_ID to set
     */
    public void setPassword_ID(int password_ID) {
        this.password_ID = password_ID;
    }

    /**
     * @param folder_ID the folder_ID to set
     */
    public void setFolder_ID(int folder_ID) {
        this.folder_ID = folder_ID;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param password_title the password_title to set
     */
    public void setPassword_title(String password_title) {
        this.password_title = password_title;
    }

    /**
     * @param password_timestamp the password_timestamp to set
     */
    public void setPassword_timestamp(Timestamp password_timestamp) {
        this.password_timestamp = password_timestamp;
    }

}
