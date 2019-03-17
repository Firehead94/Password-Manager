package com.passwordmanager.gui.controllers;

import com.passwordmanager.database.objects.Folder;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FolderBuilder {
    public static Map<Integer, ArrayList<Folder>> folderMap = new HashMap<>();
    public static Map<Integer, TreeItem<Folder>> folderItems = new HashMap<>();
    public static TreeItem<Folder> root = new TreeItem<>();

    public static TreeItem<Folder> buildTreeView(ArrayList<Folder> folders) {
;

        folderItems.put(0, new TreeItem<Folder>(new Folder()));
        // Split folders into map where key is parent id
        for (Folder f : folders) {
            if (!(folderMap.containsKey(f.getFolder_parent()))) {
                folderMap.put(f.getFolder_parent(), new ArrayList<>());
            }
            folderMap.get(f.getFolder_parent()).add(f);
            folderItems.put(f.getFolder_ID(), getTreeItem(f));
        }

        System.out.println("FOLDER ITEMS");
        Iterator it = folderItems.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            for (TreeItem<Folder> f : (ArrayList<TreeItem<Folder>>)pair.getValue()) {
                System.out.println(pair.getKey() + " = " + f.getValue());
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        System.out.println("FOLDER MAP");
        it = folderMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            for (TreeItem<Folder> f : (ArrayList<TreeItem<Folder>>)pair.getValue()) {
                System.out.println(pair.getKey() + " = " + f.getValue());
            }
            it.remove(); // avoids a ConcurrentModificationException
        }


        for (Map.Entry<Integer, TreeItem<Folder>> entry : folderItems.entrySet()) {
            Integer key = entry.getKey();
            TreeItem<Folder> curFolder = entry.getValue();
            if (folderMap.containsKey(key)) {
                System.out.println("Enter If");
                for (Folder f : folderMap.get(key)){
                    System.out.println("Enter | " + key + " For | " + f.getFolder_ID());
                    curFolder.getChildren().addAll(getTreeItem(f));
                }
                folderItems.put(key,curFolder);
            }
        }

        TreeItem<Folder> root = new TreeItem<>();
        for (TreeItem<Folder> f : folderItems.get(0).getChildren())
            root.getChildren().addAll(addLeaves(f));

        return root;

    }

    public static TreeItem<Folder> addLeaves(TreeItem<Folder> testObj) {
        if (testObj.isLeaf())
            return testObj;
        else {
            testObj.getChildren().addAll(folderItems.get(testObj.getValue().getFolder_ID()));
            for (TreeItem<Folder> t : testObj.getChildren()) {
                testObj = addLeaves(t);
            }
        }
        return testObj;
    }

    public static TreeItem<Folder> getTreeItem(Folder folder) {
        return new TreeItem<Folder>(folder);
    }

    public static TreeItem<Folder> addChild(TreeItem<Folder> folderItem, Folder folder) {

        folderItem.getChildren().addAll(getTreeItem(folder));
        return folderItem;
    }
}
