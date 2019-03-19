package com.passwordmanager.gui.controllers;

import com.passwordmanager.database.accessors.FoldersDB;
import com.passwordmanager.database.objects.Folder;
import com.passwordmanager.database.objects.User;
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
    private User user = null;
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
        treeView.setRoot(FolderBuilder.buildTreeView(FoldersDB.getFolders()));//DB.ACCESS_LEVEL, user.getAccess_level())));
    }

    public void setUser(User userLoggedIn) {
        user = userLoggedIn;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        buildFolders();
    }
}