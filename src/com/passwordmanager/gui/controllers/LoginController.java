package com.passwordmanager.gui.controllers;


import com.passwordmanager.gui.base.DialogBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.application.Application;
import javafx.stage.Stage;

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

    public void openMainWindow(ActionEvent actionEvent) throws Exception
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/mainwindow/MainWindow.fxml"));
            Parent mainPane = (Parent) loader.load();
            Stage stage2 = new Stage();
            stage2.setScene(new Scene(mainPane));
            stage2.show();
        }
        catch (Exception e)
        {
            System.out.println("Error loading main scene");
            e.printStackTrace();
        }

    }
}
