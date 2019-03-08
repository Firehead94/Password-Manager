package com.passwordmanager.database;

import com.passwordmanager.objects.Folders;

import java.util.ArrayList;

public class FoldersDB {

    // GET
    public Folders getFolderByID(int id) {return null;}
    public Folders getFolderByAccessLevel(int access) {return null;}
    public Folders getFolderByName(String name) {return null;}

    public ArrayList<Folders> getFolders() {return null;}

    // UPDATE
    public boolean updateFolder(Folders folder) {return false;}
    public boolean updateFolderName(int id, String name){return false;}
    public boolean updateFolderAccessLevel(int id, int access) {return false;}
    public boolean updateFolderPassword(int id, String password) {return false;}

    // INSERT
    public boolean insertFolder(Folders folder) {return false;}
}
