package com.passwordmanager.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

public class PasswordBoxController
{
    @FXML
    private TextField textField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private CheckBox showCheckBox;

    /*
    *
    * Temporary dummy buttons until I can figure out a  better way to utilize them. for now they just
    * "act" like a save and close button but will look good for our presentation
    *
     */
    @FXML
    void saveNewPassword(ActionEvent actionEvent)
    {
        saveBtn.getScene().getWindow().hide();
    }
    @FXML
    void closeWindow(ActionEvent actionEvent)
    {
        cancelBtn.getScene().getWindow().hide();
    }
    @FXML
    void toggleVisiblePassword()
    {
        //if show password checkbox is selected, show password in cleartext
        if (showCheckBox.isSelected())
        {
            textField.setText(passwordField.getText());
            textField.setVisible(true);
            passwordField.setVisible(false);
            return;
        }
        //otherwise don't show the password in cleartext
        passwordField.setText(textField.getText());
        textField.setVisible(false);
        passwordField.setVisible(true);
    }
    //returns the value to be saved for later entry into the DB
    //Will only send
    String getText()
    {
        return passwordField.getText();
    }
}
