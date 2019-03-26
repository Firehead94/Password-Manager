package com.passwordmanager.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
/**
 * @author Patrick Kelly
 */
public class PasswordBoxController
{
    @FXML
    private TextField passShow;
    @FXML
    private TextField title;
    @FXML
    private PasswordField passHide;
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
    public void toggleVisiblePassword(MouseEvent actionEvent) {
        passShow.setText(passHide.getText());
        passHide.setVisible(!passHide.isVisible());
        if (actionEvent.isPrimaryButtonDown()) {
            showCheckBox.setSelected(true);
        }
    }
    //returns the value to be saved for later entry into the DB
    //Will only send
    String getPass()
    {
        return passHide.getText();
    }
    String getTitle()
    {
        return title.getText();
    }
}
