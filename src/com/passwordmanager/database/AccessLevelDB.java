package com.passwordmanager.database;

import com.passwordmanager.objects.AccessLevels;

import java.util.ArrayList;

public class AccessLevelDB {

    // GET
    public AccessLevels getAccessLevelByTitle(String title) {return null;}
    public AccessLevels getAccessLevelById(int id) {return null;}

    public ArrayList<AccessLevels> getAccessLevels(){return null;}

    // UPDATE
    public boolean updateAccessLevel(AccessLevels access) {return false;}
    public boolean updateAccessLevelTitle(int id, String title) {return false;}

    // INSERT
    public boolean insertAccessLevel(AccessLevels access) {return false;}


}
