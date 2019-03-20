package com.passwordmanager.gui.controllers;

import com.passwordmanager.database.accessors.FoldersDB;
import com.passwordmanager.database.objects.Folder;
import com.passwordmanager.database.objects.User;
import com.passwordmanager.gui.base.DialogBox;
import com.passwordmanager.utils.DB;
import com.passwordmanager.utils.Images;
import com.passwordmanager.utils.Layouts;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.MenuBar;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.application.Platform.exit;

public class MainController implements Initializable
{
    private static User user = null;

    @FXML
    private BorderPane borderPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem newPasswordMenu;
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

    @FXML
    public void logout() {
        try {
        URL loc = getClass().getClassLoader().getResource(Layouts.LOGINSCREEN_FXML);
        FXMLLoader loader = new FXMLLoader(loc);
        Parent root1 = loader.load();
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(root1));
        user = null;
        menuBar.getScene().getWindow().hide();
        stage2.show();
        } catch (Exception e) {
            DialogBox.showError("Fatal Error",
                "Error switching scenes. Please try again.");
            Logger.getLogger(NewUserController.class.getName()).log(Level.SEVERE, "ERROR OPENING USER CREATION WINDOW", e);
        }
    }

    @FXML
    private void closeApp(ActionEvent actionEvent)
    {
        Platform.exit();
    }

    public void createNewPassword(ActionEvent actionEvent) throws Exception
    {
        try
        {
            URL loc = getClass().getClassLoader().getResource(Layouts.PASSWORDBOX_FXML);
            FXMLLoader loader = new FXMLLoader(loc);
            Parent root2 = loader.load();
            Stage stage3 = new Stage();
            stage3.setTitle("New Password");
            stage3.setScene(new Scene(root2));
            stage3.initModality(Modality.APPLICATION_MODAL);
            stage3.showAndWait();
            PasswordBoxController pbController = loader.getController();
            //testing, just making sure the value is getting passed correctly
            //we can take the value stored in newPassword and save it into the DB
            String newPassword = pbController.getText();
            System.out.println(newPassword);
        }
        catch (Exception e)
        {
            DialogBox.showError("Error", "There was a problem opening the desired window");
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, "ERROR OPENING PASSWORD BOX", e);
        }

    }
}