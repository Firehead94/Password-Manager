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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;

public class MainController implements Initializable
{
    @FXML
    private BorderPane borderPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private TreeView<String> treeView;
    @FXML
    private TextArea textArea;

    private ArrayList<Folder> allFolders;
    private ArrayList<TreeItem<Folder>> rootFolders;
    private HashMap<Integer, ArrayList<TreeItem<Folder>>> map;

    public void buildFolders()
    {
        FoldersDB foldersDB = new FoldersDB();
        allFolders = foldersDB.getFolders();
        rootFolders = new ArrayList<>();
        map = new HashMap<>();

        for (Folder folder : allFolders)
        {
            if (folder.getFolder_parent() ==  0)
            {
                rootFolders.add(new TreeItem<>(folder));
            }
            else
            {
                if (!(map.containsKey(folder.getFolder_parent())))
                {
                    map.put(folder.getFolder_parent(), new ArrayList<TreeItem<Folder>>());
                }
                map.get(folder.getFolder_parent()).add(new TreeItem<Folder>(folder));
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}