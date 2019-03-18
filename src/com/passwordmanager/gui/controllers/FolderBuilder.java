package com.passwordmanager.gui.controllers;

import com.passwordmanager.database.objects.Folder;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import sun.reflect.generics.tree.Tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FolderBuilder {
    private static Map<Integer, TreeItem<Folder>> mapToParents = new HashMap<>();
    private static Map<Integer, TreeItem<Folder>> mapToID = new HashMap<>();

    public static TreeItem<Folder> buildTreeView(ArrayList<Folder> folders) {

        TreeItem<Folder> root = new TreeItem<>(new Folder());
        root.getValue().setFolder_name("ROOT");
        root.getValue().setFolder_ID(0);

        for (Folder folder : folders)
            mapToID.put(folder.getFolder_ID(), new TreeItem<>(folder));

        return addLeaves(root, folders);
    }

    public static TreeItem<Folder> addLeaves(TreeItem<Folder> root, ArrayList<Folder> folders) {
        for (Folder folder : folders)
            if (folder.getFolder_parent() == root.getValue().getFolder_ID())
                root.getChildren().add(addLeaves(new TreeItem<>(folder), folders));
        return root;
    }

}
