package com.passwordmanager.database;

import com.passwordmanager.objects.FolderTimestamps;
import com.passwordmanager.objects.Folders;
import com.passwordmanager.objects.LoginTimestamps;
import sun.rmi.runtime.Log;

import java.util.ArrayList;

public class TimestampDB
{
    // GET
    public ArrayList<FolderTimestamps> getFolderTimestamps (int folderId){return null;}
    public ArrayList<LoginTimestamps> getLoginTimestamps (int userId){return null;}

    public ArrayList<FolderTimestamps> getFolderTimestamps() {return null;}
    public ArrayList<LoginTimestamps> getLoginTimestamps() {return null;}

    // INSERT
    public boolean insertFolderTimestamp(FolderTimestamps fts) {return false;}
    public boolean insertLoginTimestamp(FolderTimestamps fts) {return false;}
    public boolean insertFolderTimestamp(int id) {return false;}
    public boolean insertLoginTimestamp(int id) {return false;}




}
