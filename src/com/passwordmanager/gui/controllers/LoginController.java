package com.passwordmanager.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import com.passwordmanager.loginscreen.*;

public class LoginController
{
    @FXML
    private AnchorPane login;

    @FXML
    private PasswordField passHiddenFld;

    @FXML
    private TextField passShowFld;

    @FXML
    private Button okBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button helpBtn;

    @FXML
    private CheckBox showCheckBox;

    //Button Controllers. Temporary for now

    @FXML
    private void showHelp(ActionEvent actionEvent)
    {
        DialogBox.showInformation("Welcome To Our Password Manager",
                                  "Eventually when you click here, we will give assistance on how " +
                                           "to login. For now enjoy this cool custom dialog box.");

    }

    @FXML
    private void showOK(ActionEvent actionEvent)
    {
        DialogBox.showInformation("Confirm",
                                  "Clicking this button will allow you to login once we build our " +
                                           "next scene.");
    }

    @FXML
    private void closeApp(ActionEvent actionEvent)
    {
        System.exit(0);
    }

    public void toggleVisiblePassword(ActionEvent actionEvent)
    {
        //if show password checkbox is selected, show password in cleartext
        if (showCheckBox.isSelected())
        {
            passShowFld.setText(passHiddenFld.getText());
            passShowFld.setVisible(true);
            passHiddenFld.setVisible(false);
            return;
        }

        //otherwise don't show the password in cleartext
        passHiddenFld.setText(passShowFld.getText());
        passShowFld.setVisible(false);
        passHiddenFld.setVisible(true);

    }
}
