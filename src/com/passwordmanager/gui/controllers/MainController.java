package com.passwordmanager.gui.controllers;

import com.passwordmanager.database.objects.Folder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.MenuBar;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable
{
    @FXML
    private BorderPane borderPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private TreeView<String> treeView;

    //helper method to populate the main nodes
    public ArrayList<TreeItem<String>> getFolders()
    {
        ArrayList<TreeItem<String>> folders = new ArrayList<>();
        //populate the weblogin parent node
        TreeItem<String> webLogins = new TreeItem<>("Web Logins");
        webLogins.getChildren().addAll(getWebLogins());
        //populate the printerLogins parent node
        TreeItem<String> printerLogins = new TreeItem<>("Printer Logins");
        printerLogins.getChildren().addAll(getPrinterPasswords());

        folders.add(webLogins);
        folders.add(printerLogins);

        return folders;
    }
    //create the children nodes related to web logins
    private ArrayList<TreeItem<String>> getWebLogins()
    {
        ArrayList<TreeItem<String>> webLogins = new ArrayList<>();

        TreeItem<String> google = new TreeItem<>("google");
        TreeItem<String> apple = new TreeItem<>("apple");
        TreeItem<String> facebook = new TreeItem<>("facebook");

        webLogins.add(google);
        webLogins.add(apple);
        webLogins.add(facebook);

        return webLogins;
    }
    //create the children nodes related to printer passwords
    private ArrayList<TreeItem<String>> getPrinterPasswords()
    {
        ArrayList<TreeItem<String>> printerPass = new ArrayList<>();

        TreeItem<String> konica = new TreeItem<>("Konica Minolta");
        TreeItem<String> ricoh = new TreeItem<>("Ricoh");
        TreeItem<String> hp = new TreeItem<>("Hewlett Packard");

        printerPass.add(konica);
        printerPass.add(ricoh);
        printerPass.add(hp);

        return printerPass;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ArrayList<TreeItem<String>> allFolders = getFolders();
        TreeItem<String> rootNode = new TreeItem<>("Folder Types");
        rootNode.getChildren().addAll(allFolders);
        this.treeView.setRoot(rootNode);
    }
}