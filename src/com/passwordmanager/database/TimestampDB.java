package com.passwordmanager.database;

import com.passwordmanager.objects.Folders;
import com.passwordmanager.objects.LoginTimestamps;
import sun.rmi.runtime.Log;

import java.util.ArrayList;

public class TimestampDB
{
    public Folders getFolderbyID(int folderID)
    {
        Folders folder = null;

        return folder;
    }

    public Folders getFolderbyAccessLevel(String accessLevel)
    {
        Folders access = null;

        return access;
    }

    public Folders getFolderbyName (String folderName)
    {
        Folders name = null;

        return name;
    }

    public ArrayList<Folders> getFolders()
    {
        return null;
    }

    public LoginTimestamps getTimestampbyID (int userID)
    {
        LoginTimestamps user = null;

        return user;
    }

    public LoginTimestamps getTimestampbyTimestamp (int timestamp)
    {
        LoginTimestamps timestamps = null;
        return timestamps;
    }

    public boolean updateFolderID (Folders folderID, int id)
    {
        return false;
    }

    public boolean updateFolderAccessLevel (Folders accessLevel, int access)
    {
        return false;
    }

    public boolean updateFolderName (Folders folderName, int name)
    {
        return false;
    }

    public boolean updateTimestampID (Folders LoginTimestamps, int timestampID)
    {
        return false;
    }

    public boolean updateTimestampTimestamp (Folders LoginTimestamps, int timestamp)
    {
        return false;
    }
}
