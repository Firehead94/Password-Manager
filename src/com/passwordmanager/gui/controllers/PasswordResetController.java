package com.passwordmanager.gui.controllers;

import com.passwordmanager.database.accessors.UserDB;
import com.passwordmanager.utils.HashPassword;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Patrick Kelly
 */
public class PasswordResetController
{
    @FXML
    private TextField passShow;
    @FXML
    private TextField confirmShow;
    @FXML
    private PasswordField passHidden;
    @FXML
    private PasswordField confirmHidden;
    @FXML
    private Button saveBtn;
    @FXML
    private CheckBox passCheck;
    @FXML
    private CheckBox confirmCheck;
    @FXML
    private Label error;

    private String passwordNew = "";

    public String getPasswordNew() {
        return passwordNew;
    }

    @FXML
    public void savePassBtn(ActionEvent actionEvent)
    {
        boolean wasreset = false;
        String hash = null;
        String salt = null;
        if (confirmHidden.getText().equals(passHidden.getText())) {
            error.setText("");
            String newPassword = getPass();
            if (!newPassword.isEmpty()) {
                salt = HashPassword.createSalt(128).get();
                hash = HashPassword.createHash(getPass(), salt).get();
                passwordNew = salt + ":" + hash;
            } else {
                Logger.getLogger(PasswordResetController.class.getName()).log(Level.INFO, "Password Not Changed");
            }

            saveBtn.getScene().getWindow().hide();
        }
        else {
            error.setText("Passwords do not match");
        }
    }

    @FXML
    public void toggleVisiblePassword(MouseEvent actionEvent) {
        passShow.setText(passHidden.getText());
        passHidden.setVisible(!passHidden.isVisible());
        if (actionEvent.isPrimaryButtonDown()) {
            passCheck.setSelected(true);
        }
    }

    @FXML
    public void toggleVisibleConfirm(MouseEvent actionEvent) {
        confirmShow.setText(confirmHidden.getText());
        confirmHidden.setVisible(!confirmHidden.isVisible());
        if (actionEvent.isPrimaryButtonDown()) {
            confirmCheck.setSelected(true);
        }
    }
    //returns the value to be saved for later entry into the DB
    //Will only send
    public String getPass()
    {
        return passHidden.getText();
    }

}
