package com.passwordmanager.gui.controllers;

import com.passwordmanager.database.accessors.FoldersDB;
import com.passwordmanager.database.objects.Folder;
import com.passwordmanager.database.objects.User;
import com.passwordmanager.utils.DB;
import com.passwordmanager.utils.Images;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.MenuBar;
import java.net.URL;
import java.util.*;

import javafx.scene.control.TextArea;

public class MainController implements Initializable
{
    private static User user = null;

    @FXML
    private BorderPane borderPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private TreeView<Folder> treeView;
    @FXML
    private TextArea textArea;

    public void buildFolders()
    {
        treeView.setRoot(FolderBuilder.buildTreeView(FoldersDB.getFoldersByAL(user.getAccess_level())));
        treeView.setShowRoot(false);
    }

    public void setUser(User userLoggedIn) {
        user = userLoggedIn;
        buildFolders();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        textArea.setEditable(false);
    }
}