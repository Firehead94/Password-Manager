package com.passwordmanager.database;

import com.passwordmanager.objects.Passwords;

import java.util.ArrayList;

public class PasswordsDB {

    // GET
    public Passwords getPasswordByID(int id) {return null;}
    public Passwords getPasswordByFolder(int id) {return null;}
    public Passwords getPasswordByTitle(String title){return null;}

    public ArrayList<Passwords> getPasswords() {return null;}

    // UPDATE
    public boolean updatePassword(Passwords pwd) {return false;}
    public boolean updatePassword(int id, String pwd) {return false;}
    public boolean updateParentFolder(int id, int folderId) { return false;}
    public boolean updatePasswordTitle(int id, String title) {return false;}


    // INSERT
    public boolean insertPassword(Passwords password) {return false;}


}
