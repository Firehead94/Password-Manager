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

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        FoldersDB foldersDB = new FoldersDB();
        allFolders = foldersDB.getFolders();
        String test = "";
        for (Folder f : allFolders)
        {
            test = test.concat(" | " + f.getFolder_name());
        }
        textArea.setText(test);
    }
}