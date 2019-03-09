package com.passwordmanager.database;

import com.passwordmanager.objects.Folder;

import java.util.ArrayList;

public class FoldersDB {

    // GET
    public Folder getFolderByID(int id) {return null;}
    public Folder getFolderByAccessLevel(int access) {return null;}
    public Folder getFolderByName(String name) {return null;}

    public ArrayList<Folder> getFolders() {return null;}

    // UPDATE
    public boolean updateFolder(Folder folder) {return false;}
    public boolean updateFolderName(int id, String name){return false;}
    public boolean updateFolderAccessLevel(int id, int access) {return false;}
    public boolean updateFolderPassword(int id, String password) {return false;}

    // INSERT
    public boolean insertFolder(Folder folder) {return false;}
}
