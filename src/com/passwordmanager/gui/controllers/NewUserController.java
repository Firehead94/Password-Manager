package com.passwordmanager.gui.controllers;

import com.passwordmanager.database.accessors.UserDB;
import com.passwordmanager.database.objects.User;
import com.passwordmanager.gui.base.DialogBox;
import com.passwordmanager.utils.DB;
import com.passwordmanager.utils.HashPassword;
import com.passwordmanager.utils.Layouts;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author Justin Scott
 */
public class NewUserController {


    @FXML
    private PasswordField passHiddenFld;
    @FXML
    private TextField passShowFld;
    @FXML
    private TextField usernameFld;
    @FXML
    private CheckBox showCheckBox;
    @FXML
    private PasswordField passHiddenFld2;
    @FXML
    private TextField passShowFld2;
    @FXML
    private CheckBox showCheckBox2;
    @FXML
    private TextField lastname;
    @FXML
    private TextField firstname;
    @FXML
    private Button cancel;
    @FXML
    private Button create;
    @FXML
    private Label noMatch;

    @FXML
    private void cancel(ActionEvent actionEvent) {
        try {
            URL loc = getClass().getClassLoader().getResource(Layouts.LOGINSCREEN_FXML);
            FXMLLoader loader = new FXMLLoader(loc);
            Parent root1 = loader.load();
            Stage stage2 = new Stage();
            stage2.setScene(new Scene(root1));
            cancel.getScene().getWindow().hide();
            stage2.show();
        } catch (Exception e) {
            DialogBox.showError("Fatal Error",
                    "Error switching scenes. Please try again.");
            Logger.getLogger(NewUserController.class.getName()).log(Level.SEVERE, "ERROR OPENING MAIN WINDOW", e);
        }
    }

    @FXML
    private void createUser(ActionEvent actionEvent) {
        User user = null;

        if (!passHiddenFld.getText().equals(passHiddenFld2.getText())) {
            noMatch.setText("Passwords Don't Match");
        } else {
            if (!UserDB.userExists(usernameFld.getText())) {
                try {
                    String salt = HashPassword.createSalt(128).get();
                    String hash = HashPassword.createHash(passHiddenFld.getText(), salt).get();

                    user = new User(usernameFld.getText(), firstname.getText(), lastname.getText(), salt + ":" + hash);
                    boolean wasCreated = UserDB.insertUser(user);
                    if (wasCreated) {
                        user = UserDB.getUser(DB.USER_USERNAME, usernameFld.getText());
                        Logger.getLogger(LoginController.class.getName()).log(Level.INFO, user.getUser_username() + " has logged in.");
                        URL loc = getClass().getClassLoader().getResource(Layouts.MAINWINDOW_FXML);
                        FXMLLoader loader = new FXMLLoader(loc);
                        Parent root1 = loader.load();
                        Stage stage2 = new Stage();
                        stage2.setScene(new Scene(root1));
                        stage2.setMinWidth(1100);

                        MainController mainController = loader.<MainController>getController();
                        mainController.setUser(user);
                        stage2.setTitle("KeyCrypt - Welcome " + user.getUser_first_name() + " " + user.getUser_last_name());

                        create.getScene().getWindow().hide();
                        stage2.show();
                    } else {
                        throw new Exception("No User Created");
                    }

                } catch (Exception e) {
                    DialogBox.showError("Fatal Error",
                            "Failed connection to Database.");
                    Logger.getLogger(NewUserController.class.getName()).log(Level.SEVERE, "ERROR CREATING USER", e);
                }
            } else {
                DialogBox.showError("Fatal Error",
                        "Username is taken. Please try a different Username.");
                Logger.getLogger(NewUserController.class.getName()).log(Level.SEVERE, "ERROR CREATING USER");
            }
        }


    }

    @FXML
    public void passwordPassword(MouseEvent actionEvent) {
        passShowFld.setText(passHiddenFld.getText());
        passHiddenFld.setVisible(!passHiddenFld.isVisible());
        if (actionEvent.isPrimaryButtonDown()) {
            showCheckBox.setSelected(true);
        }
    }

    @FXML
    public void passwordConfirm(MouseEvent actionEvent) {
        passShowFld2.setText(passHiddenFld2.getText());
        passHiddenFld2.setVisible(!passHiddenFld2.isVisible());
        if (actionEvent.isPrimaryButtonDown()) {
            showCheckBox2.setSelected(true);
        }
    }
}
