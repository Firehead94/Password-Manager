package com.passwordmanager.database.accessors;

import com.passwordmanager.database.objects.Password;

import java.util.ArrayList;

public class PasswordsDB {

    // GET
    public Password getPasswordByID(int id) {return null;}
    public Password getPasswordByFolder(int id) {return null;}
    public Password getPasswordByTitle(String title){return null;}

    public ArrayList<Password> getPasswords() {return null;}

    // UPDATE
    public boolean updatePassword(Password pwd) {return false;}
    public boolean updatePassword(int id, String pwd) {return false;}
    public boolean updateParentFolder(int id, int folderId) { return false;}
    public boolean updatePasswordTitle(int id, String title) {return false;}


    // INSERT
    public boolean insertPassword(Password password) {return false;}


}
