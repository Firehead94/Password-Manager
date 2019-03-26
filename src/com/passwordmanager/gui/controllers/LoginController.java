package com.passwordmanager.gui.controllers;

import com.passwordmanager.database.accessors.FoldersDB;
import com.passwordmanager.database.accessors.UserDB;
import com.passwordmanager.database.objects.User;
import com.passwordmanager.gui.base.DialogBox;
import com.passwordmanager.utils.DB;
import com.passwordmanager.utils.Layouts;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Justin Scott
 * @author Patrick Kelly
 */
public class LoginController
{
    User user = null;
    //global GUI components used by login screen
    @FXML
    private AnchorPane login;
    @FXML
    private PasswordField passHiddenFld;
    @FXML
    private TextField passShowFld;
    @FXML
    private TextField usernameFld;
    @FXML
    private Button okBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button helpBtn;
    @FXML
    private CheckBox showCheckBox;
    @FXML
    private Button newUser;

    //Show help on login page
    @FXML
    private void showHelp(ActionEvent actionEvent)
    {
        DialogBox.showInformation("Welcome To Keycrypt",
                              "Enter the password which corresponds to your access level. " +
                                       "Ask your system administrator which password you have access to.\n\n" +
                                       "The password entered will grant you access only to specific directories " +
                                       "within the application.");
    }

    //closes application
    @FXML
    private void closeApp(ActionEvent actionEvent)
    {
        Platform.exit();
    }

    @FXML
    public void toggleVisiblePassword(MouseEvent actionEvent) {
        passShowFld.setText(passHiddenFld.getText());
        passHiddenFld.setVisible(!passHiddenFld.isVisible());
        if (actionEvent.isPrimaryButtonDown()) {
            showCheckBox.setSelected(true);
        }
    }


    @FXML
    public void openUserCreation(ActionEvent actionEvent) throws Exception {
        try {
            URL loc = getClass().getClassLoader().getResource(Layouts.NEWUSER_FXML);
            FXMLLoader loader = new FXMLLoader(loc);
            Parent root1 = loader.load();
            Stage stage2 = new Stage();
            stage2.setScene(new Scene(root1));
            newUser.getScene().getWindow().hide();
            stage2.show();
        } catch (Exception e) {
            DialogBox.showError("Fatal Error",
                    "Error switching scenes. Please try again.");
            Logger.getLogger(NewUserController.class.getName()).log(Level.SEVERE, "ERROR OPENING USER CREATION WINDOW", e);
        }
    }

    public void openMainWindow(ActionEvent actionEvent) throws Exception
    {
        boolean resultHidden = UserDB.validateUser(usernameFld.getText(), passHiddenFld.getText());
        boolean resultClear = UserDB.validateUser(usernameFld.getText(), passShowFld.getText());

        if (resultHidden || resultClear)
        {
            try
            {
                user = UserDB.getUser(DB.USER_USERNAME, usernameFld.getText());
                Logger.getLogger(LoginController.class.getName()).log(Level.INFO, user.getUser_username() + " has logged in.");
                URL loc = getClass().getClassLoader().getResource(Layouts.MAINWINDOW_FXML);
                FXMLLoader loader = new FXMLLoader(loc);
                Parent root1 = loader.load();
                Stage stage2 = new Stage();
                stage2.setScene(new Scene(root1));

                MainController mainController = loader.<MainController>getController();
                mainController.setUser(user);
                stage2.setTitle("KeyCrypt - Welcome " + user.getUser_first_name() + " " + user.getUser_last_name());

                okBtn.getScene().getWindow().hide();
                stage2.show();
            }
            catch (Exception e)
            {
                DialogBox.showError("Fatal Error",
                        "Error opening program. Please check login credentials and try again.");
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "ERROR OPENING MAIN WINDOW", e);
            }
        }
        else
        {
            DialogBox.showError("Invalid Username/Password",
                                 "Please enter a valid username/password and try again");
        }

    }

}//end LoginController
