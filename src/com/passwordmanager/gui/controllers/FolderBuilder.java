package com.passwordmanager.gui.controllers;

import com.passwordmanager.database.objects.Folder;
import com.passwordmanager.utils.Images;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sun.reflect.generics.tree.Tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Justin Scott
 */
public class FolderBuilder {

    /**
     * Builds a treeItem out of an arraylist populated by database.
     * 
     * @param folders
     * @return
     */
    public static TreeItem<Folder> buildTreeView(ArrayList<Folder> folders) {

        TreeItem<Folder> root = new TreeItem<>(new Folder());
        root.getValue().setFolder_name("Directory");
        root.getValue().setFolder_ID(0);
        root.setExpanded(true);

        return addLeaves(root, folders);
    }

    /**
     * Recursively builds folder structure by navigating through possible children.
     * Is not memory efficient what so ever but should be fine for the majority of
     * users.
     *
     * @param root
     * @param folders list of all folders
     * @return returns on leaf of tree
     */
    private static TreeItem<Folder> addLeaves(TreeItem<Folder> root, ArrayList<Folder> folders) {
        for (Folder folder : folders)
            if (folder.getFolder_parent() == root.getValue().getFolder_ID())
                root.getChildren().add(addLeaves(new TreeItem<>(folder), folders));
        return root;
    }

}
