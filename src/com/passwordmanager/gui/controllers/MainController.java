package com.passwordmanager.gui.controllers;

import com.passwordmanager.database.objects.Folder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.StackPane;

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

    public ArrayList<TreeItem<String>> getFolders()
    {
        ArrayList<TreeItem<String>> folders = new ArrayList<TreeItem<String>>();

        TreeItem<String> webLogins = new TreeItem<>("Web Logins");
        webLogins.getChildren().addAll(getWebLogins());

        TreeItem<String> printerLogins = new TreeItem<>("Printer Logins");
        printerLogins.getChildren().addAll(getPrinterPasswords());

        folders.add(webLogins);
        folders.add(printerLogins);

        return folders;
    }

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

    private ArrayList<TreeItem<String>> getPrinterPasswords()
    {
        ArrayList<TreeItem<String>> printerPass = new ArrayList<TreeItem<String>>();

        TreeItem<String> konica = new TreeItem<String>("Konica Minolta");
        TreeItem<String> ricoh = new TreeItem<String>("Ricoh");
        TreeItem<String> hp = new TreeItem<String>("Hewlett Packard");

        printerPass.add(konica);
        printerPass.add(ricoh);
        printerPass.add(hp);

        return printerPass;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ArrayList<TreeItem<String>> allFolders = getFolders();
        treeView = new TreeView<>();
        TreeItem<String> rootNode = new TreeItem<>("Folder Types");
        rootNode.getChildren().addAll(allFolders);
        treeView.setRoot(rootNode);
    }
}