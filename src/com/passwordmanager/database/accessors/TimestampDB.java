package com.passwordmanager.database.accessors;

import com.passwordmanager.database.objects.FolderTimestamp;
import com.passwordmanager.database.objects.LoginTimestamp;

import java.util.ArrayList;

public class TimestampDB
{
    // GET
    public ArrayList<FolderTimestamp> getFolderTimestamps (int folderId){return null;}
    public ArrayList<LoginTimestamp> getLoginTimestamps (int userId){return null;}

    public ArrayList<FolderTimestamp> getFolderTimestamps() {return null;}
    public ArrayList<LoginTimestamp> getLoginTimestamps() {return null;}

    // INSERT
    public boolean insertFolderTimestamp(FolderTimestamp fts) {return false;}
    public boolean insertLoginTimestamp(FolderTimestamp fts) {return false;}
    public boolean insertFolderTimestamp(int id) {return false;}
    public boolean insertLoginTimestamp(int id) {return false;}




}
