package com.passwordmanager.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * @author Patrick Kelly
 */
public class FolderBoxController
{

    @FXML
    private TextField title;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

    boolean create = true;

    @FXML
    void saveNewFolder(ActionEvent actionEvent)
    {
        if (!title.getText().isEmpty())
            saveBtn.getScene().getWindow().hide();
    }
    @FXML
    void closeWindow(ActionEvent actionEvent)
    {
        create = false;
        cancelBtn.getScene().getWindow().hide();


    }

    //returns the value to be saved for later entry into the DB
    //Will only send
    String getTitle()
    {
        return title.getText();
    }
}
