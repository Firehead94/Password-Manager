package com.passwordmanager.gui.controllers;

import com.passwordmanager.database.accessors.FoldersDB;
import com.passwordmanager.database.objects.Folder;
import com.passwordmanager.utils.DB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.MenuBar;
import java.net.URL;
import java.util.*;

import javafx.scene.control.TextArea;

public class MainController implements Initializable
{
    @FXML
    private BorderPane borderPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private TreeView<Folder> treeView;
    @FXML
    private TextArea textArea;

    private ArrayList<Folder> allFolders;
    private ArrayList<TreeItem<Folder>> rootFolders;
    private HashMap<Integer, ArrayList<TreeItem<Folder>>> map;

    public void buildFolders()
    {
        FoldersDB foldersDB = new FoldersDB();
       // allFolders = foldersDB.getFolders();
       // rootFolders = new ArrayList<>();
       // map = new HashMap<>();
//
       // for (Folder folder : allFolders)
       // {
       //     if (folder.getFolder_parent() ==  0)
       //     {
       //         rootFolders.add(new TreeItem<>(folder));
       //     }
       //     else
       //     {
       //         if (!(map.containsKey(folder.getFolder_parent())))
       //         {
       //             map.put(folder.getFolder_parent(), new ArrayList<TreeItem<Folder>>());
       //         }
       //         map.get(folder.getFolder_parent()).add(new TreeItem<Folder>(folder));
       //     }
       // }
//
       // for (TreeItem<Folder> fol : rootFolders)
       //     System.out.println(fol);
//
       // Iterator it = map.entrySet().iterator();
       // while (it.hasNext()) {
       //     Map.Entry pair = (Map.Entry)it.next();
       //     for (TreeItem<Folder> f : (ArrayList<TreeItem<Folder>>)pair.getValue()) {
       //         System.out.println(pair.getKey() + " = " + f.getValue());
       //     }
       //     it.remove(); // avoids a ConcurrentModificationException
       // }
//
//
       // TreeItem<Folder> root = new TreeItem<Folder>(new Folder());
       // Folder rootFolder = new Folder();
       // rootFolder.setFolder_name("Password List");
//
       // for (TreeItem<Folder> roots : rootFolders) {
       //     root.getChildren().addAll(roots);
       // }

        treeView.setRoot(FolderBuilder.buildTreeView(foldersDB.getFolders()));


    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        buildFolders();
    }
}