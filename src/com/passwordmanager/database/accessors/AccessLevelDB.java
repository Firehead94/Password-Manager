package com.passwordmanager.database.accessors;

import com.passwordmanager.database.objects.AccessLevel;

import java.util.ArrayList;

public class AccessLevelDB {

    // GET
    public AccessLevel getAccessLevelByTitle(String title) {return null;}
    public AccessLevel getAccessLevelById(int id) {return null;}

    public ArrayList<AccessLevel> getAccessLevels(){return null;}

    // UPDATE
    public boolean updateAccessLevel(AccessLevel access) {return false;}
    public boolean updateAccessLevelTitle(int id, String title) {return false;}

    // INSERT
    public boolean insertAccessLevel(AccessLevel access) {return false;}


}
